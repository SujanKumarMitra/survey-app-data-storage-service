package com.github.mitrakumarsujan.datastorageservice.service.file.csv;

import static java.util.stream.Collectors.groupingBy;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.github.mitrakumarsujan.datastorageservice.service.file.FileSystemFormResponseStorageStrategy;
import com.github.mitrakumarsujan.datastorageservice.service.file.FileWriterService;
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
	private FormResponseCsvRowMapper rowMapper;

	@Autowired
	@Qualifier("csv-response-manager")
	private FormResponseFileManager fileManager;

	@Autowired
	private FileWriterService fileWriter;

	private static final Logger LOGGER = LoggerFactory.getLogger(CsvFileSystemFormResponseStorageStrategy.class);
	
	private static final String NEW_LINE = System.lineSeparator();

	@Override
	public FormResponse save(FormResponse response) {
		CharSequence data = prepareWritableData(response);

		File file = fileManager.getFile(response.getFormId());
		fileWriter.appendData(data, file);
		
		LOGGER.info("response written to file '{}'", file.getName());
		
		return response;
	}

	@Override
	public List<FormResponse> saveAll(List<FormResponse> responses) {
		Map<String, List<FormResponse>> responseMap = responses	
													  	.stream()
													  	.collect(groupingBy(FormResponse::getFormId));

		responseMap.forEach(this::writeResponseList);
		return responses;
	}

	@Override
	public List<FormResponse> getAll(String formId) {
		// TODO
		return null;
	}

	private void writeResponseList(String formId, List<FormResponse> responseList) {
		File file = fileManager.getFile(formId);
		String data = responseList	.stream()
									.map(this::prepareWritableData)
									.collect(Collectors.joining(NEW_LINE));
		fileWriter.appendData(data, file);
	}


	private CharSequence prepareWritableData(FormResponse response) {
		LocalDateTime timestamp = response.getTimestamp();
		String[] responses = rowMapper.apply(response.getResponses());

		StringJoiner joiner = new StringJoiner(",");
		joiner.add(timestamp.toString());

		for (String res : responses) {
			joiner.add(res);
		}
		return joiner.toString();
	}

	@Override
	public void initFormResponseStorage(Form form) {
		FormTemplate template = form.getTemplate();
		String[] headers = headerMapper.apply(template);

		StringJoiner joiner = new StringJoiner(",");
		joiner.add("Timestamp");
		for (String header : headers) {
			joiner.add(header);
		}

		String formId = form.getId();
		fileManager.createFile(formId);
		File file = fileManager.getFile(formId);

		fileWriter.appendData(joiner.toString(), file);
		LOGGER.info("response storage initated for formId '{}'", formId);
	}

}
