package com.github.mitrakumarsujan.datastorageservice.service.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.mitrakumarsujan.datastorageservice.configuration.ApplicationConfigurationProperties;

/**
 * @author Sujan Kumar Mitra
 * @since 2020-10-29
 */
@Component
public class FileManagerHelper {

	@Autowired
	private ApplicationConfigurationProperties properties;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FileManagerHelper.class);

	public String getUploadBaseDirectory() {
		return properties.getProperty("upload-directory");
	}

	public Path getPath(String formId, String fileName) {
		String baseDir = getUploadBaseDirectory();
		return Paths.get(baseDir, formId, fileName);
	}

	public File getFile(String formId, String fileName) {
		Path path = getPath(formId, fileName);
		return path.toFile();
	}

	public boolean createFile(String formId, String fileName) {
		Path path = getPath(formId, fileName);
		File file = path.toFile();
		try {
			file.getParentFile().mkdirs();
			boolean created = file.createNewFile();
			if (created) {
				LOGGER.info("file {} created successfully..", file.getName());
				file.setReadable(true);
				file.setWritable(true);
			}
			return created;
		} catch (IOException e) {
			LOGGER.error("failed to create file '{}'. Reason:: {}", file.getName(), e.getMessage());
			return false;
		}
	}

	public boolean existsFile(String formId, String fileName) {
		Path path = getPath(formId, fileName);
		File file = path.toFile();
		return file.exists();
	}
}
