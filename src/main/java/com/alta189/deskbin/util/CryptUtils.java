package com.alta189.deskbin.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.crypto.SecretKey;

import org.apache.commons.io.IOUtils;

public class CryptUtils {
	private static SecretKey key;
	public static File keyFile = new File(PlatformUtils.getWorkingDirectory(), "data" + File.separator + "key" );
	private static DesEncrypter encrypter;

	private static boolean loadKey() {
		FileInputStream fis = null;
		ObjectInputStream in = null;
		try {
			fis = new FileInputStream(keyFile);
			in = new ObjectInputStream(fis);
			Object obj = in.readObject();
			if (obj != null && obj instanceof SecretKey) {
				key = (SecretKey) obj;
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(fis);
			IOUtils.closeQuietly(in);
		}
		return false;
	}

	private static SecretKey getNewSecretKey() {
		return DesEncrypter.getNewKey();
	}

	private static boolean writeKey(SecretKey key) {
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try {
			if (keyFile.getParentFile() != null) {
				keyFile.getParentFile().mkdirs();
			}
			fos = new FileOutputStream(keyFile);
			out = new ObjectOutputStream(fos);
			out.writeObject(key);
			out.flush();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(fos);
			IOUtils.closeQuietly(out);
		}
		return false;
	}

	public static SecretKey getSecretKey() {
		if (key != null) {
			return key;
		}
		SecretKey compare = null;
		if (!keyFile.exists()) {
			compare = getNewSecretKey();
			if (!writeKey(compare)) {
				return null;
			}
		}
		if (loadKey()) {
			if (compare != null && !compare.equals(key)) {
				key = null;
			}
			return key;
		}
		return null;
	}

	private static DesEncrypter getEncrypter() {
		if (encrypter != null) { return encrypter; }

		encrypter = new DesEncrypter(getSecretKey());
		return encrypter;
	}

	public static String encrypt(String raw) {
		return getEncrypter().encrypt(raw);
	}

	public static String decrypt(String raw) {
		return getEncrypter().decrypt(raw);
	}

}