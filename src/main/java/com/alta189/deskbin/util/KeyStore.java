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
package com.alta189.deskbin.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

public class KeyStore {
	private static final KeyStore instance;
	private final ConcurrentHashMap<String, String> map = new ConcurrentHashMap<String, String>();
	private final File saveFile = new File(PlatformUtils.getWorkingDirectory(), "data" + File.separator + "store.ser");
	private final Object key = new Object();

	static {
		instance = new KeyStore();

		// Ensure CryptUtils key is loaded
		CryptUtils.getSecretKey();

		// Load
		load();

		// Add shutdown hook to save the store
		Runtime.getRuntime().addShutdownHook(new Thread(new SafeSaveRunnable()));
	}

	public static void store(String key, Object o) {
		if (key == null || key.isEmpty()) {
			throw new IllegalAccessError();
		}
		instance.storeImpl(key, o);
	}

	public static <T> T get(String key) {
		if (key == null || key.isEmpty()) {
			throw new IllegalAccessError();
		}
		return instance.getImpl(key);
	}

	public static boolean contains(String key) {
		return !(key == null || key.isEmpty()) && instance.map.containsKey(key);
	}

	public static void remove(String key) {
		if (key != null && !key.isEmpty()) {
			instance.map.remove(key);
		}
	}

	public static void load() {
		ConcurrentHashMap<String, String> loadedMap = instance.loadImpl();
		instance.map.clear();
		instance.map.putAll(loadedMap);
	}

	public static void save() {
		instance.saveImpl();
	}

	private void storeImpl(String key, Object o) {
		if (o == null) {
			o = new NullObject();
		}
		String data = toString(CastUtil.safeCast(o, Serializable.class));
		String encryptedData = CryptUtils.encrypt(data);
		map.put(key, encryptedData);
	}

	private <T> T getImpl(String key) {
		String data = map.get(key);
		if (data == null || data.isEmpty()) {
			return null;
		}
		String decryptedData = CryptUtils.decrypt(data);
		Object o = fromString(decryptedData);
		if (o instanceof NullObject) {
			return null;
		}
		return CastUtil.safeCast(o);
	}

	private ConcurrentHashMap<String, String> loadImpl() {
		synchronized (key) {
			String data = null;

			if (saveFile.exists()) {
				try {
					data = FileUtils.readFileToString(saveFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (data == null || data.isEmpty()) {
				return new ConcurrentHashMap<String, String>();
			} else {
				return CastUtil.safeCast(fromString(data));
			}
		}
	}

	private void saveImpl() {
		synchronized (key) {
			if (!map.isEmpty()) {
				String data = toString(map);
				try {
					FileUtils.write(saveFile, data);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				saveFile.delete();
			}
		}
	}

	private Object fromString(String s) {
		ObjectInputStream ois = null;
		try {
			byte[] data = Base64Coder.decode(s);
			ois = new ObjectInputStream(new ByteArrayInputStream(data));
			return SerializationUtils.deserialize(ois);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(ois);
		}
		return null;
	}

	private String toString(Serializable o) {
		ByteArrayOutputStream baos = null;
		ObjectOutputStream oos = null;
		try {
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			SerializationUtils.serialize(o, oos);
			return new String(Base64Coder.encode(baos.toByteArray()));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(oos);
			IOUtils.closeQuietly(baos);
		}
		return null;
	}

	static class NullObject implements Serializable {
		private static final long serialVersionUID = 6437740969004810019L;
	}

	static class SafeSaveRunnable implements Runnable {
		@Override
		public void run() {
			save();
		}
	}
}
