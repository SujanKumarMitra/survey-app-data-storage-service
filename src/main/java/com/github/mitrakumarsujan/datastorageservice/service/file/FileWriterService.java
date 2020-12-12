package com.github.mitrakumarsujan.datastorageservice.service.file;

import java.io.File;

/**
 * @author Sujan Kumar Mitra
 * @since 2020-10-31
 */
public interface FileWriterService {
	
	public void appendData(CharSequence data, File file);
	
}