package com.github.mitrakumarsujan.datastorageservice.service.file.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mitrakumarsujan.datastorageservice.service.FormStorageService;
import com.github.mitrakumarsujan.datastorageservice.service.file.FileSystemFormResponseStorageStrategy;
import com.github.mitrakumarsujan.datastorageservice.service.file.FormFileManager;
import com.github.mitrakumarsujan.formmodel.exception.FormNotFoundException;
import com.github.mitrakumarsujan.formmodel.model.form.Form;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

/**
 * @author Sujan Kumar Mitra
 * @since 2020-10-27
 */
@Service("json-form-storage-service")
public class JsonFileSystemFormStorageStrategy implements FormStorageService {

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	@Qualifier("json-file-manager")
	private FormFileManager fileManager;
	
	@Autowired
	@Qualifier("csv-response-storage")
	private FileSystemFormResponseStorageStrategy responseStorageStrategy;

	private static final Logger LOGGER = LoggerFactory.getLogger(JsonFileSystemFormStorageStrategy.class);

	@Override
	public Form save(Form form) {
		String id = form.getId();
		fileManager.createFile(id);
		File file = fileManager.getFile(id);
		file.setWritable(true);

		try {
			mapper.writeValue(file, form);
			LOGGER.info("Form with id '{}' serialized to '{}'", id, file.getAbsolutePath());
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
		
		responseStorageStrategy.initFormResponseStorage(form);
		return form;
	}

	@Override
	public Form find(String formId) throws FormNotFoundException {
		File file = fileManager.getFile(formId);
		if(!file.exists()) {
			throw new FormNotFoundException(formId);
		}
		file.setReadable(true);

		Form form = null;
		try {
			form = mapper.readValue(file, Form.class);
			LOGGER.info("Form with id '{}' deserialized from '{}'", formId, file.getAbsolutePath());
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return form;
	}

}