package com.github.mitrakumarsujan.datastorageservice.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mitrakumarsujan.datastorageservice.configuration.ApplicationConfigurationProperties;
import com.github.mitrakumarsujan.formmodel.model.form.Form;

/**
 * @author Sujan Kumar Mitra
 * @since 2020-10-27
 */
@Component
public class FileSystemFormStorageStrategy implements FormStorageService {

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private ApplicationConfigurationProperties properties;

	private static Logger LOGGER = LoggerFactory.getLogger(FileSystemFormStorageStrategy.class);

	@Override
	public Form save(Form form) {
		String uid = form.getId();
		File file = getFile(uid);
		
		file.setWritable(true);

		try {
			mapper.writeValue(file, form);
			LOGGER.info("Form with uid {} serialized to {}", uid, file.getAbsolutePath());
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
		
		return form;
	}

	@Override
	public Form find(String formUID) {
		File file = getFile(formUID);
		file.setReadable(true);

		Form form = null;
		try (Scanner scanner = new Scanner(file)) {
			form = mapper.readValue(file, Form.class);
			LOGGER.info("Form with uid {} deserialized from {}", formUID, file.getAbsolutePath());
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return form;
	}

	private File getFile(String formUID) {
		Path path = Paths.get(getBaseDirectory(), formUID, "form-schema.json");
		File file = path.toFile();
		if (!file.exists()) {
			file.getParentFile().mkdirs();
			try {
				file.createNewFile();
				LOGGER.info("form-schema.json file created for form uid {}", formUID);
			} catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
		return file;
	}

	private String getBaseDirectory() {
		return properties.getUploadBaseDirectory();
	}

}
