package com.alta189.deskbin.gui.account;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.alta189.deskbin.util.KeyStore;
import com.alta189.deskbin.util.UIUtil;
import org.eclipse.egit.github.core.Authorization;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.OAuthService;

public class GithubAccountPanel extends AccountPanel implements ActionListener {
	private static final long serialVersionUID = -9154340819859873741L;
	private JTextField username;
	private JPasswordField password;
	private JLabel label = null;
	private String user = null;
	private String pass = null;
	private boolean changed = true;

	@Override
	protected void buildControls() {
		username = new JTextField();
		password = new JPasswordField();

		username.addActionListener(this);
		password.addActionListener(this);

		createFieldGroup("Github");
		addField("Username", username);

		String user = KeyStore.get("github-user");
		if (user != null && !user.isEmpty()) {
			username.setText(user);
		}

		String oauthToken = KeyStore.get("github-oauth-token");
		if (oauthToken != null && !oauthToken.isEmpty()) {
			label = new JLabel("Already authenticated");
			addField(label);
		}

		addField("Password", password);
	}

	@Override
	public boolean onLoseFocus() {
		String pass = new String(password.getPassword());
		if (username.getText() != null && !username.getText().isEmpty() && !pass.isEmpty() && changed) {
			try {
				GitHubClient client = new GitHubClient().setCredentials(username.getText(), pass);
				OAuthService service = new OAuthService(client);

				Authorization authorization = new Authorization();
				authorization.setNote("DeskBin");
				authorization.setNoteUrl("http://github.com/alta189/DeskBin");
				authorization = service.createAuthorization(authorization);

				if (authorization.getToken() == null) {
					throw new NullPointerException();
				}

				this.user = username.getText();
				this.pass = authorization.getToken();
			} catch (Exception e) {
				e.printStackTrace();
				UIUtil.showError(this, "Github Auth", "There was an error when verifying your Github account.");
				return false;
			}
		} else if (label != null) {
			UIUtil.showError(this, "Github Auth", "You need to fill in both the Github username and password.");
			return false;
		}
		return true;
	}

	@Override
	public void onGainFocus() {

	}

	@Override
	public void save() {
		if (user != null && pass != null && !user.isEmpty() && !pass.isEmpty()) {
			KeyStore.store("github-user", user);
			KeyStore.store("github-oauth-token", user);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		changed = true;
	}
}
