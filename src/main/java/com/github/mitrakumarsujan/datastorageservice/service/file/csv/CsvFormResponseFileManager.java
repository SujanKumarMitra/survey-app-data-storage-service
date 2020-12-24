package com.github.mitrakumarsujan.datastorageservice.service.file.csv;

import java.io.File;

import com.github.mitrakumarsujan.datastorageservice.service.file.FileManagerHelper;
import com.github.mitrakumarsujan.datastorageservice.service.file.FormResponseFileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Sujan Kumar Mitra
 * @since 2020-10-29
 */
@Service("csv-response-manager")
public class CsvFormResponseFileManager implements FormResponseFileManager {

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
		return "form-response.csv";
	}

}
