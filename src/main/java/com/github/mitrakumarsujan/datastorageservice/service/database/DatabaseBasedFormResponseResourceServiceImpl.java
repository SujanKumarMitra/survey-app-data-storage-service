package com.github.mitrakumarsujan.datastorageservice.service.database;

import com.github.mitrakumarsujan.datastorageservice.service.FormResponseResourceService;
import com.github.mitrakumarsujan.datastorageservice.service.FormResponseStorageService;
import com.github.mitrakumarsujan.datastorageservice.service.file.FileWriterService;
import com.github.mitrakumarsujan.datastorageservice.service.file.FormResponseFileManager;
import com.github.mitrakumarsujan.formmodel.model.formresponse.FormResponseCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.StringJoiner;

import static java.lang.System.lineSeparator;
import static java.util.stream.Collectors.joining;

@Service
@ConditionalOnProperty(prefix = "app", name = "storage-strategy", havingValue = "database")
public class DatabaseBasedFormResponseResourceServiceImpl implements FormResponseResourceService {

    @Autowired
    private FormResponseStorageService responseStorageService;

    @Autowired
    private FormResponseFileManager fileManager;

    @Autowired
    private FileWriterService writer;

    @Override
    public Resource getFileResource(String formId) {
        FormResponseCollection responses = responseStorageService.getResponses(formId);
        fileManager.createFile(formId);
        File file = fileManager.getFile(formId);

        String csvHeaders = getCsvHeaders(responses.getQuestions());
        String csvRows = getCsvRows(responses.getResponses());
        String data = new StringJoiner(lineSeparator())
                .add(csvHeaders)
                .add(csvRows)
                .toString();
        writer.writeData(data,file);

        return new FileSystemResource(file);
    }

    private String getCsvHeaders(List<String> questions) {
        return questions
                .stream()
                .sequential()
                .collect(joining(","));
    }

    private String getCsvRow(List<String> response) {
        return response
                .stream()
                .collect(joining(","));
    }

    private String getCsvRows(List<List<String>> responses) {
        return responses
                .stream()
                .map(this::getCsvRow)
                .collect(joining(lineSeparator()));
    }
}
