package com.alta189.deskbin.service.url;

import com.alta189.deskbin.service.ServiceSnapshot;
import com.alta189.deskbin.util.CastUtil;
import com.alta189.deskbin.util.KeyStore;
import com.rosaloves.bitlyj.Bitly;
import com.rosaloves.bitlyj.Jmp;

public class JmpService extends ShorteningService {
	private static final String NAME = "j.mp";
	private Bitly.Provider jmp;
	private final String user;

	public JmpService(String user) {
		String apiKey = KeyStore.get("jmp-" + user);
		jmp = Jmp.as(user, apiKey);
		this.user = user;
	}

	public JmpService(ServiceSnapshot snapshot) {
		 this(CastUtil.safeCast(snapshot.get("user"), String.class));
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public ServiceSnapshot generateSnapshot() {
		return new ServiceSnapshot(getClass()).add("user", user);
	}

	@Override
	public String shorten(String url) throws ShortenerException {
		try {
			return jmp.call(Bitly.shorten(url)).getShortUrl();
		} catch (Exception e) {
			throw new ShortenerException(e);
		}
	}

	@Override
	public String expand(String url) throws ShortenerException {
		try {
			return jmp.call(Bitly.shorten(url)).getShortUrl();
		} catch (Exception e) {
			throw new ShortenerException(e);
		}
	}
}
