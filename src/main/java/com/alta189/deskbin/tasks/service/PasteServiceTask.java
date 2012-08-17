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
package com.alta189.deskbin.tasks.service;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import com.alta189.deskbin.services.paste.PasteException;
import com.alta189.deskbin.services.paste.PasteService;
import com.alta189.deskbin.tasks.Action;
import com.alta189.deskbin.tasks.TaskSnapshot;
import com.alta189.deskbin.util.CastUtil;
import com.alta189.deskbin.util.Keyboard;
import com.alta189.deskbin.util.OptionsMap;

public class PasteServiceTask extends ServiceTask<PasteService> {
	public PasteServiceTask(PasteService service) {
		this(service, new OptionsMap());
	}

	public PasteServiceTask(PasteService service, OptionsMap options) {
		super(service);
		setOptions(options);
	}

	public PasteServiceTask(TaskSnapshot snapshot) {
		super(snapshot);
		setOptions(snapshot.get("options", new OptionsMap()));
	}

	@Override
	public void run() {
		String clipboard = getClipboardContents();
		if (clipboard != null && !clipboard.isEmpty()) {
			try {
				String url = getService().paste(clipboard, getOptions().get("private", true));
				if (url != null && !url.isEmpty()) {
					switch (Action.getValue(getOptions().get(String.class, "action"))) {
						case WRITE:
							Keyboard.getInstance().type(url);
							notify("The clipboard contents have uploaded and the url typed.");
							break;
						case CLIPBOARD:
							Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
							StringSelection stringSelection = new StringSelection(url);
							c.setContents(stringSelection, null);
							notify("The clipboard contents have uploaded and the clipboard set to the url.");
							break;
					}
				}
			} catch (PasteException e) {
				e.printStackTrace();
			}
		}
	}

	public String getClipboardContents() {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		if (clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor)) {
			try {
				return CastUtil.safeCast(clipboard.getData(DataFlavor.stringFlavor));
			} catch (UnsupportedFlavorException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
