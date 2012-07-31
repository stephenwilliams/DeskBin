package com.alta189.deskbin.gui;

import com.alta189.deskbin.util.UIUtil;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class OptionsDialog extends JDialog {

	private JTable configsTable;
	private JTabbedPane tabs;
	private List<OptionsPanel> optionsPanels = new ArrayList<OptionsPanel>();

	public OptionsDialog() {
		setTitle("Launcher Options");
		setResizable(false);
		buildUserInterface();
		pack();
		setSize(400, 500);
	}

	private <T extends OptionsPanel> T wrap(T panel) {
		optionsPanels.add(panel);
		return panel;
	}

	public void buildUserInterface() {
		final OptionsDialog self = this;

		JPanel container = new JPanel();
		container.setBorder(BorderFactory.createEmptyBorder(8, 8, 5, 8));
		container.setLayout(new BorderLayout(3, 3));

		tabs = new JTabbedPane();

		container.add(tabs, BorderLayout.CENTER);

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		JButton okButton = new JButton("OK");
		JButton cancelButton = new JButton("Cancel");
		UIUtil.equalWidth(okButton, cancelButton);
		buttonsPanel.add(okButton);
		buttonsPanel.add(cancelButton);
		container.add(buttonsPanel, BorderLayout.SOUTH);

		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				self.save();
				self.dispose();
			}
		});

		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				self.dispose();
			}
		});

		self.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		add(container, BorderLayout.CENTER);
	}
	
	public void save() {
		for (OptionsPanel panel : optionsPanels) {
			panel.save();
		}
	}

}
