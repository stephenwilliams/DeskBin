package com.alta189.deskbin.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.alta189.deskbin.util.UIUtil;

public class JLinkLabel extends JLabel implements MouseListener {
	private static final long serialVersionUID = -8160487889817317619L;
	private String url;
	private JContextMenu contextMenu;

	public JLinkLabel(String text, String url) {
		super(text);
		this.url = url;
		contextMenu = new JContextMenu(this);
		setForeground(Color.BLUE);
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		addMouseListener(this);
	}

	public void mouseClicked(MouseEvent e) {
		if (e.isPopupTrigger()) {
			try {
				UIUtil.openURL(url, this);
			} catch (Exception ex) {
				System.err.println("Unable to open browser to " + url);
				ex.printStackTrace();
			}
		}
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		showPopup(e);
	}

	public void mouseReleased(MouseEvent e) {
		showPopup(e);
	}

	private void showPopup(MouseEvent e) {
		if (e.isPopupTrigger()) {
			contextMenu.show(e.getComponent(), e.getX(), e.getY());
		}
	}

	public class JContextMenu extends JPopupMenu implements ActionListener {
		private static final long serialVersionUID = -131370594209853446L;
		private final JLinkLabel label;
		private final JMenuItem copyText;
		private final JMenuItem copyLink;
		private final JMenuItem open;

		public JContextMenu(JLinkLabel label) {
			this.label = label;
			open = add(new JMenuItem("Open"));
			copyLink = add(new JMenuItem("Copy Link"));
			copyText = add(new JMenuItem("Copy Text"));

			open.addActionListener(this);
			copyText.addActionListener(this);
			copyLink.addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals(open.getActionCommand())) {
				UIUtil.openURL(label.url, label);
			} else if (e.getActionCommand().equals(copyText.getActionCommand())) {
				setClipboadContents(label.getText());
			} else if (e.getActionCommand().equals(copyLink.getActionCommand())) {
				setClipboadContents(label.url);
			}
		}

		public void setClipboadContents(String contents) {
			Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
			StringSelection stringSelection = new StringSelection(contents);
			c.setContents(stringSelection, null);
		}
	}

}
