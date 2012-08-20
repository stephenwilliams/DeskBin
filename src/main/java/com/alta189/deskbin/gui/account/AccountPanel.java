package com.alta189.deskbin.gui.account;

import com.alta189.deskbin.gui.OptionsPanel;

public abstract class AccountPanel extends OptionsPanel {
	private static final long serialVersionUID = -971100327612152700L;

	public AccountPanel() {
		super(null);
		setVisible(false);
	}

	public abstract boolean onLoseFocus();

	public abstract void onGainFocus();
}
