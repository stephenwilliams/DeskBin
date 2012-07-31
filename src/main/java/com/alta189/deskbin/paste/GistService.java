package com.alta189.deskbin.paste;

import org.eclipse.egit.github.core.Gist;
import org.eclipse.egit.github.core.GistFile;
import org.eclipse.egit.github.core.client.GitHubClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GistService extends AbstractPasteService {
	private static final String NAME = "gist";
	private final GitHubClient client;

	public GistService(String oauthTokeb) {
		client = new GitHubClient(oauthTokeb);
	}

	@Override
	public String getName() {
		return NAME;
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
