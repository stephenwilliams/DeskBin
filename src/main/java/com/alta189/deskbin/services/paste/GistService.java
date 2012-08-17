/*
 * This file is part of DeskBin.
 *
 * Copyright (c) ${project.inceptionYear}-2012, alta189 <http://github.com/alta189/DeskBin/>
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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.egit.github.core.Gist;
import org.eclipse.egit.github.core.GistFile;
import org.eclipse.egit.github.core.client.GitHubClient;

import com.alta189.deskbin.services.ServiceSnapshot;
import com.alta189.deskbin.util.KeyStore;

public class GistService extends PasteService {
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
}
