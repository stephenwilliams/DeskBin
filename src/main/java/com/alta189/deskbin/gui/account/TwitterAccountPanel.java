package com.alta189.deskbin.gui.account;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import com.alta189.deskbin.gui.JLinkLabel;
import com.alta189.deskbin.util.KeyStore;
import com.alta189.deskbin.util.KeyUtils;

public class TwitterAccountPanel extends AccountPanel {
	
	private JTextField tokenentry;
	private JLabel status;
	private JLinkLabel authurl;
	private JButton refresh;
	private JButton logout;
	
	private Twitter twitter;
	private RequestToken reqtoken;
	
	@Override
	protected void buildControls() {
		twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(KeyUtils.getKey("twitter-consumerkey"), KeyUtils.getKey("twitter-consumersec"));
		/* reqtoken is created whenever a token is needed */
		reqtoken = null;
		createFieldGroup("Twitter-Compatible Image Services");
		status = new JLabel();
		addField("Status",status);
		authurl = new JLinkLabel();
		addField(authurl);
		createEmptySpace();
		tokenentry = new JTextField();
		addField("PIN",tokenentry);
		refresh = new JButton();
		addField(refresh);
		logout = new JButton("Remove Authorization");
		addField(logout);
		refresh.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				refreshInterface();
			}
			
		});
		logout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				clearAccessToken();
			}
			
		});
		refreshInterface();
	}
	
	private void clearAccessToken() {
		KeyStore.remove("twitter-oauth");
		refreshInterface();
	}
	
	private void refreshInterface() {
		AccessToken token = KeyStore.get("twitter-oauth");
		if (!tokenentry.getText().isEmpty()) {
			try {
				token = twitter.getOAuthAccessToken(reqtoken,tokenentry.getText());
				KeyStore.store("twitter-oauth", token);
				tokenentry.setText("");
			} catch (TwitterException e) {
				token = null;
			}
		}
		if (token != null) {
			twitter.setOAuthAccessToken(token);
			User user = null;
			try {
				user = twitter.verifyCredentials();
			} catch (TwitterException e) {
				if (e.getStatusCode() == 401)
					token = null;
			}
			if (user != null) {
				status.setText("Authorized as @" + user.getScreenName());
				authurl.setText("Your credentials are up to date.");
				authurl.setURL(null);
				tokenentry.setEnabled(false);
				refresh.setText("Refresh Status");
				logout.setEnabled(true);
			}
		}
		if (token == null) {
			status.setText("Not Authorized");
			authurl.setText("Authorize with Twitter");
			// request token needs to be recreated
		    try {
		    	twitter.setOAuthAccessToken(null);
				reqtoken = twitter.getOAuthRequestToken();
			} catch (TwitterException e) {
				throw new RuntimeException(e);
			}
			authurl.setURL(reqtoken.getAuthorizationURL());
			tokenentry.setEnabled(true);
			refresh.setText("Refresh and Authorize");
			logout.setEnabled(false);
		}
	}

	@Override
	public boolean onLoseFocus() {
		return true;
	}

	@Override
	public void onGainFocus() {
	}

	@Override
	public void save() {
	}
}
