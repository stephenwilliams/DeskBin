package com.alta189.deskbin.services.paste;

import com.alta189.deskbin.services.ServiceSnapshot;
import com.alta189.deskbin.util.KeyStore;
import org.eclipse.egit.github.core.Gist;
import org.eclipse.egit.github.core.GistFile;
import org.eclipse.egit.github.core.client.GitHubClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GistService extends AbstractPasteService {
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
