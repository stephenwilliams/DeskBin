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
package com.alta189.deskbin.services.paste;

import java.io.File;
import java.util.List;

public abstract class FilePasteService extends PasteService {

	public String paste(File file) throws PasteException {
		return paste(file, true);
	}

	public abstract String paste(File file, boolean isPrivate) throws PasteException;

	public String paste(List<File> files) throws PasteException {
		return paste(files, true);
	}

	public abstract String paste(List<File> files, boolean isPrivate) throws PasteException;
}
