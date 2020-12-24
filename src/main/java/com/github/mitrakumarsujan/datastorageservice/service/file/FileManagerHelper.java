package com.github.mitrakumarsujan.datastorageservice.service.file;

import com.github.mitrakumarsujan.datastorageservice.configuration.UploadsConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Sujan Kumar Mitra
 * @since 2020-10-29
 */
@Component
@RefreshScope
public class FileManagerHelper {

	@Autowired
	private UploadsConfiguration uploadsConfig;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FileManagerHelper.class);

	public String getUploadBaseDirectory() {
		return uploadsConfig.getBaseDir();
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
				LOGGER.info("file '{}' created successfully", file.getAbsolutePath());
				file.setReadable(true);
				file.setWritable(true);
			}
			return created;
		} catch (IOException e) {
			LOGGER.error("failed to create file '{}'. Reason:: {}", file.getAbsolutePath(), e.getMessage());
			return false;
		}
	}

	public boolean existsFile(String formId, String fileName) {
		Path path = getPath(formId, fileName);
		File file = path.toFile();
		return file.exists();
	}
}
