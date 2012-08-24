package com.alta189.deskbin.gui;

import java.util.Map;
import java.util.TreeMap;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.alta189.commons.yaml.YAMLProcessor;
import com.alta189.deskbin.gui.account.AccountPanel;
import com.alta189.deskbin.gui.account.BitLyAccountPanel;
import com.alta189.deskbin.gui.account.GithubAccountPanel;
import com.alta189.deskbin.gui.account.TwitterAccountPanel;

public class AccountsOptionPanel extends OptionsPanel implements ListSelectionListener {
	private static final long serialVersionUID = -2424416208776144054L;
	private static final Map<String, AccountPanel> accounts = getAccountList();
	private JList<String> listBox;
	private String preValue = null;
	private int preIndex = -1;

	public AccountsOptionPanel(YAMLProcessor settings) {
		super(settings);
	}

	private static Map<String, AccountPanel> getAccountList() {
		Map<String, AccountPanel> accounts = new TreeMap<String, AccountPanel>();
		accounts.put("Twitter", new TwitterAccountPanel());
		accounts.put("Github", new GithubAccountPanel());
		accounts.put("bit.ly/j.mp", new BitLyAccountPanel());
		return accounts;
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void buildControls() {
		createFieldGroup("Accounts");
		listBox = new JList<String>(accounts.keySet().toArray(new String[0]));
		listBox.addListSelectionListener(this);
		listBox.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
		addField(listBox);
		createEmptySpace();
		createFieldGroup("Account Settings");
		for (AccountPanel panel : accounts.values()) {
			addField(panel);
		}
	}

	@Override
	public void save() {
		for (AccountPanel panel : accounts.values()) {
			panel.save();
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (!e.getValueIsAdjusting()) {
			return;
		}
		boolean canSwitch = true;
		if (preValue != null) {
			AccountPanel panel = accounts.get(preValue);
			canSwitch = panel.onLoseFocus();

			if (!canSwitch) {
				listBox.setSelectedIndex(preIndex);
			} else {
				panel.setVisible(false);
			}
		}

		if (canSwitch) {
			AccountPanel panel = accounts.get(listBox.getSelectedValue());
			panel.setVisible(true);
			panel.onGainFocus();
			preValue = listBox.getSelectedValue();
			preIndex = listBox.getSelectedIndex();
		}
	}
}
