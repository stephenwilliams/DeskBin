/*
 * This file is part of DeskBin.
 *
 * Copyright (c) 2012, alta189 <http://github.com/alta189/DeskBin/>
 * DeskBin is licensed under the GNU Lesser General Public License.
 *
 * DeskBin is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * DeskBin is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.alta189.deskbin.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public abstract class OptionsPanel extends JPanel {

	protected GridBagConstraints fieldConstraints;
	protected GridBagConstraints labelConstraints;
	private JPanel groupPanel;

	public OptionsPanel() {
		fieldConstraints = new GridBagConstraints();
		fieldConstraints.fill = GridBagConstraints.HORIZONTAL;
		fieldConstraints.weightx = 1.0;
		fieldConstraints.gridwidth = GridBagConstraints.REMAINDER;
		fieldConstraints.insets = new Insets(2, 5, 2, 5);

		labelConstraints = (GridBagConstraints) fieldConstraints.clone();
		labelConstraints.weightx = 0.0;
		labelConstraints.gridwidth = 1;
		labelConstraints.insets = new Insets(1, 5, 1, 10);

		buildUI();
	}


	private void buildUI() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setOpaque(false);
		setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

		buildControls();

		// Vertical glue not working
		add(new Box.Filler(new Dimension(0, 0), new Dimension(0, 10000), new Dimension(0, 10000)));
	}

	protected abstract void buildControls();

	public abstract void save();

	/**
	 * Create a field group and set the current active field group to the
	 * created one.
	 *
	 * @param title title of group
	 * @return field group
	 */
	protected JPanel createFieldGroup(String title) {
		JPanel parent = this;
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setBorder(BorderFactory.createTitledBorder(title));
		panel.setLayout(new GridBagLayout());
		parent.add(panel);
		groupPanel = panel;
		return panel;
	}

	/**
	 * Add a labeled field.
	 *
	 * @param label label
	 * @param component component to add
	 * @return the component
	 */
	protected <T extends Component> T addField(String label, T component) {
		JPanel parent = groupPanel;
		GridBagLayout layout = (GridBagLayout) parent.getLayout();
		JLabel labelObj = new JLabel(label);
		layout.setConstraints(labelObj, labelConstraints);
		layout.setConstraints(component, fieldConstraints);
		labelObj.setLabelFor(component);
		parent.add(labelObj);
		parent.add(component);

		return component;
	}

	/**
	 * Add a non-labeled field (like a checkbox).
	 *
	 * @param component component to add
	 * @return the component
	 */
	protected <T extends Component> T addField(T component) {
		JPanel parent = groupPanel;
		if (component instanceof JComponent) {
			((JComponent) component).setOpaque(false);
		}

		if (component instanceof JCheckBox) {
			((JCheckBox) component).setBorder(BorderFactory.createEmptyBorder(2, 4, 2, 4));
		}

		GridBagLayout layout = (GridBagLayout) parent.getLayout();
		layout.setConstraints(component, fieldConstraints);
		parent.add(component);
		return component;
	}
}
