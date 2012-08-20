package com.alta189.deskbin.gui.account;

import javax.swing.JTextField;

import com.alta189.deskbin.gui.JLinkLabel;
import com.alta189.deskbin.util.KeyStore;

public class BitLyAccountPanel extends AccountPanel {
	private static final long serialVersionUID = -587406838855216399L;
	private JTextField username;
	private JTextField apiKey;
	private JLinkLabel label;

	@Override
	protected void buildControls() {
		username = new JTextField();
		apiKey = new JTextField();
		label = new JLinkLabel("Click here to get API Key", "http://bitly.com/a/your_api_key/");

		createFieldGroup("Bit.ly / J.mp");
		addField(label);
		createEmptySpace();
		addField("Username", username);
		addField("API Key", apiKey);

		String user = KeyStore.get("bitly-user");
		String apikey = KeyStore.get("bitly-apikey");

		if (user != null && !user.isEmpty()) {
			username.setText(user);
		}

		if (apikey != null && !apikey.isEmpty()) {
			apiKey.setText(apikey);
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
		KeyStore.store("bitly-user", username.getText());
		KeyStore.store("bitly-apikey", apiKey.getText());
	}
}
