package com.github.mitrakumarsujan.datastorageservice.service.file.csv;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.StringJoiner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.github.mitrakumarsujan.datastorageservice.service.file.FileSystemFormResponseStorageStrategy;
import com.github.mitrakumarsujan.datastorageservice.service.file.FormResponseFileManager;
import com.github.mitrakumarsujan.formmodel.model.form.Form;
import com.github.mitrakumarsujan.formmodel.model.form.FormTemplate;
import com.github.mitrakumarsujan.formmodel.model.formresponse.FormResponse;

/**
 * @author Sujan Kumar Mitra
 * @since 2020-10-29
 */
@Service("csv-response-storage")
public class CsvFileSystemFormResponseStorageStrategy implements FileSystemFormResponseStorageStrategy {

	@Autowired
	private FormTemplateCsvHeaderMapper headerMapper;
	
	@Autowired
	@Qualifier("csv-form-storage")
	private FormResponseFileManager fileManager;

	private static final Logger LOGGER = LoggerFactory.getLogger(CsvFileSystemFormResponseStorageStrategy.class);

	@Override
	public FormResponse save(FormResponse response) {
		// TODO
		return response;
	}

	@Override
	public Iterable<FormResponse> saveAll(Iterable<FormResponse> response) {
		// TODO
		return response;
	}

	@Override
	public List<FormResponse> getAll(String formId) {
		// TODO
		return null;
	}

	@Override
	public void initFormStorage(Form form) {
		FormTemplate template = form.getTemplate();
		String[] headers = headerMapper.apply(template);

		StringJoiner joiner = new StringJoiner(", ");
		joiner.add("TimeStamp");
		for (String header : headers) {
			joiner.add(header);
		}

		String formId = form.getId();
		fileManager.createFile(formId);
		File file = fileManager.getFile(formId);

		appendData(joiner.toString(), file);

	}

	private void appendData(CharSequence cs, File file) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file,true))) {
			writer.append(cs);
			writer.newLine();
			writer.flush();
		} catch (IOException e) {
			LOGGER.error("file writing failed in file. Reason:: {}", file.getName(), e.getMessage());
		}
	}

}
