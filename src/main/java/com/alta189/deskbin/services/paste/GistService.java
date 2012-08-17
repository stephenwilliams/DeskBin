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
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.eclipse.egit.github.core.Gist;
import org.eclipse.egit.github.core.GistFile;
import org.eclipse.egit.github.core.client.GitHubClient;

import com.alta189.deskbin.services.ServiceSnapshot;
import com.alta189.deskbin.util.KeyStore;

public class GistService extends FilePasteService {
	private static final String NAME = "gist";
	private final GitHubClient client;

	public GistService() {
		String oAuthToken = KeyStore.get("github-oauth-token");
		client = new GitHubClient(oAuthToken);
	}

	public GistService(ServiceSnapshot snapshot) {
		this();
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public ServiceSnapshot generateSnapshot() {
		return new ServiceSnapshot(getClass());
	}

	@Override
	public String paste(String content, boolean isPrivate) throws PasteException {
		org.eclipse.egit.github.core.service.GistService service = new org.eclipse.egit.github.core.service.GistService(client);
		Gist gist = new Gist();
		gist.setPublic(!isPrivate);

		Map<String, GistFile> files = new HashMap<String, GistFile>();
		files.put("untitled", new GistFile().setFilename("untitled").setContent(content));

		gist.setFiles(files);
		try {
			service.createGist(gist);
		} catch (IOException e) {
			throw new PasteException(e);
		}
		return gist.getUrl();
	}

	@Override
	public String paste(File file, boolean isPrivate) throws PasteException {
		return paste(Collections.singletonList(file), isPrivate);
	}

	@Override
	public String paste(List<File> files, boolean isPrivate) throws PasteException {
		org.eclipse.egit.github.core.service.GistService service = new org.eclipse.egit.github.core.service.GistService(client);
		Gist gist = new Gist();
		gist.setPublic(!isPrivate);

		Map<String, GistFile> gistFiles = new HashMap<String, GistFile>();
		for (File file : files) {
			GistFile gistFile = new GistFile();
			gistFile.setFilename(file.getName());
			try {
				gistFile.setContent(FileUtils.readFileToString(file));
				gistFiles.put(gistFile.getFilename(), gistFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (gistFiles.size() > 0) {
			gist.setFiles(gistFiles);
			try {
				service.createGist(gist);
			} catch (IOException e) {
				throw new PasteException(e);
			}
		}
		return gist.getUrl();
	}
}
