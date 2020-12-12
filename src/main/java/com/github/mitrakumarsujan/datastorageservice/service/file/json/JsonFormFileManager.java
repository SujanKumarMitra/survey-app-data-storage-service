package com.github.mitrakumarsujan.datastorageservice.service.file.json;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.github.mitrakumarsujan.datastorageservice.service.file.FileManagerHelper;
import com.github.mitrakumarsujan.datastorageservice.service.file.FormFileManager;

/**
 * @author Sujan Kumar Mitra
 * @since 2020-10-29
 */
@Component("json-file-manager")
public class JsonFormFileManager implements FormFileManager {

	@Autowired
	private FileManagerHelper delegatee;

	@Override
	public File getFile(String formId) {
		return delegatee.getFile(formId, getFileName());
	}

	@Override
	public boolean createFile(String formId) {
		return delegatee.createFile(formId, getFileName());
	}

	@Override
	public boolean existsFile(String formId) {
		return delegatee.existsFile(formId, getFileName());
	}

	private String getFileName() {
		return "form-schema.json";
	}

}
