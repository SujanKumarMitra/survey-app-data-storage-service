package com.github.mitrakumarsujan.datastorageservice.service.file.csv;

import com.github.mitrakumarsujan.datastorageservice.service.FormResponseRowMapper;
import com.github.mitrakumarsujan.datastorageservice.service.FormResponseStorageService;
import com.github.mitrakumarsujan.datastorageservice.service.FormTemplateHeaderMapper;
import com.github.mitrakumarsujan.datastorageservice.service.file.FileWriterService;
import com.github.mitrakumarsujan.datastorageservice.service.file.FormResponseFileManager;
import com.github.mitrakumarsujan.formmodel.exception.FormNotFoundException;
import com.github.mitrakumarsujan.formmodel.model.form.Form;
import com.github.mitrakumarsujan.formmodel.model.form.FormTemplate;
import com.github.mitrakumarsujan.formmodel.model.formresponse.FormResponse;
import com.github.mitrakumarsujan.formmodel.model.formresponse.FormResponseCollection;
import com.github.mitrakumarsujan.formmodel.model.formresponse.FormResponseCollectionImpl;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toCollection;

/**
 * @author Sujan Kumar Mitra
 * @since 2020-10-29
 */
@Service("csv-response-storage")
public class CsvFileSystemFormResponseStorageStrategy implements FormResponseStorageService {

	@Autowired
	private FormTemplateHeaderMapper headerMapper;

	@Autowired
	private FormResponseRowMapper rowMapper;

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

		LOGGER.info("response written to file '{}'", file.getAbsolutePath());

		return response;
	}

	private CharSequence prepareWritableData(FormResponse response) {
		LocalDateTime timestamp = response.getTimestamp();
		List<String> responses = rowMapper.apply(response.getResponses());
		responses.add(0, timestamp.toString());

		return getWritableData(responses);
	}

	@Override
	public void initFormResponseStorage(Form form) {
		FormTemplate template = form.getTemplate();
		List<String> headers = headerMapper.apply(template);

		headers.add(0, "Timestamp");

		CharSequence data = getWritableData(headers);

		String formId = form.getId();
		fileManager.createFile(formId);
		File file = fileManager.getFile(formId);

		fileWriter.appendData(data, file);
		LOGGER.info("response storage initiated for formId '{}'", formId);
	}

	private CharSequence getWritableData(List<String> data) {
		return data	.stream()
					.collect(joining(","));
	}

	@Override
	public FormResponseCollection getResponses(String formId) throws FormNotFoundException {
		File file = fileManager.getFile(formId);

		CSVFormat format = CSVFormat.RFC4180
									.withFirstRecordAsHeader()
									.withNullString("")
									.withTrim();
		try (CSVParser parser = format.parse(new FileReader(file))) {
			FormResponseCollectionImpl responseCollection = new FormResponseCollectionImpl();
			responseCollection.setFormId(formId);

			List<String> headers = parser.getHeaderNames();
			responseCollection.setQuestions(headers);

			List<List<String>> responses = parser	.getRecords()
													.stream()
													.sequential()
													.map(record -> parseRecord(record, headers))
													.collect(toCollection(LinkedList::new));

			responseCollection.setResponses(responses);
			return responseCollection;
		} catch (IOException e) {
			throw new FormNotFoundException(formId);
		}
	}

	private List<String> parseRecord(CSVRecord record, List<String> headers) {
		List<String> response = new LinkedList<>();
		for (String header : headers) {
			String cell = record.get(header);
			response.add(cell);
		}
		return response;
	}

}
