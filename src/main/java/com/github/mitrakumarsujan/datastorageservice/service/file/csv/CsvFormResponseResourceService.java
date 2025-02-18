package com.github.mitrakumarsujan.datastorageservice.service.file.csv;

import com.github.mitrakumarsujan.datastorageservice.service.FormResponseResourceService;
import com.github.mitrakumarsujan.datastorageservice.service.file.FormResponseFileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * @author skmitra
 * @since 2020-11-08
 */
@Service("csv-form-response-resource")
@ConditionalOnProperty(prefix = "app", name = "storage-strategy", havingValue = "file")
public class CsvFormResponseResourceService implements FormResponseResourceService {

	@Autowired
	private FormResponseFileManager fileManager;

	@Override
	public Resource getFileResource(String formId) {
		File file = fileManager.getFile(formId);
		return new FileSystemResource(file);
	}


}
