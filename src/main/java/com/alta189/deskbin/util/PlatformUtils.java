package com.alta189.deskbin.util;

import java.io.File;

public class PlatformUtils {

	private static final File workingDirectory = getWorkingDirectory("deskhub");

	public static File getWorkingDirectory() {
		return workingDirectory;
	}

	public static File getWorkingDirectory(String applicationName) {
		String userHome = System.getProperty("user.home", ".");
		File workingDirectory;

		switch (getOperatingSystem()) {
			case LINUX:
			case SOLARIS:
				workingDirectory = new File(userHome, '.' + applicationName + '/');
				break;
			case WINDOWS:
				String applicationData = System.getenv("APPDATA");
				if (applicationData != null) {
					workingDirectory = new File(applicationData, applicationName + '/');
				} else {
					workingDirectory = new File(userHome, '.' + applicationName + '/');
				}
				break;
			case MAC_OS:
				workingDirectory = new File(userHome, "Library/Application Support/" + applicationName);
				break;
			default:
				workingDirectory = new File(userHome, applicationName + '/');
		}
		if ((!workingDirectory.exists()) && (!workingDirectory.mkdirs()))
			throw new RuntimeException("The working directory could not be created: " + workingDirectory);
		return workingDirectory;
	}

	public static OS getOperatingSystem() {
		String osName = System.getProperty("os.name").toLowerCase();
		if (osName.contains("win"))
			return OS.WINDOWS;
		if (osName.contains("mac"))
			return OS.MAC_OS;
		if (osName.contains("solaris"))
			return OS.SOLARIS;
		if (osName.contains("sunos"))
			return OS.SOLARIS;
		if (osName.contains("linux"))
			return OS.LINUX;
		if (osName.contains("unix"))
			return OS.LINUX;
		return OS.UNKNOWN;
	}

	public enum OS {
		LINUX,
		SOLARIS,
		WINDOWS,
		MAC_OS,
		UNKNOWN;
	}
	
}
