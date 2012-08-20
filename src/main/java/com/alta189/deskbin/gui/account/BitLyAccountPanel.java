package com.alta189.deskbin.gui.account;

import javax.swing.JTextField;

import com.alta189.deskbin.util.KeyStore;

public class BitLyAccountPanel extends AccountPanel {
	private static final long serialVersionUID = -587406838855216399L;
	private JTextField username;
	private JTextField apiKey;

	@Override
	protected void buildControls() {
		username = new JTextField();
		apiKey = new JTextField();

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
