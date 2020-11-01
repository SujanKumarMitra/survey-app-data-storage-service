package com.github.mitrakumarsujan.datastorageservice.service.file;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mitrakumarsujan.datastorageservice.service.FormStorageService;
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
	@Qualifier("json-file-manager")
	private FormFileManager fileManager;
	
	@Autowired
	@Qualifier("csv-response-storage")
	private FileSystemFormResponseStorageStrategy responseStorageStrategy;

	private static Logger LOGGER = LoggerFactory.getLogger(FileSystemFormStorageStrategy.class);

	@Override
	public Form save(Form form) {
		String id = form.getId();
		fileManager.createFile(id);
		File file = fileManager.getFile(id);
		file.setWritable(true);

		try {
			mapper.writeValue(file, form);
			LOGGER.info("Form with uid '{}' serialized to '{}'", id, file.getAbsolutePath());
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
		
		responseStorageStrategy.initFormResponseStorage(form);
		return form;
	}

	@Override
	public Form find(String formId) {
		File file = fileManager.getFile(formId);
		file.setReadable(true);

		Form form = null;
		try (Scanner scanner = new Scanner(file)) {
			form = mapper.readValue(file, Form.class);
			LOGGER.info("Form with uid '{}' deserialized from '{}'", formId, file.getAbsolutePath());
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return form;
	}

}