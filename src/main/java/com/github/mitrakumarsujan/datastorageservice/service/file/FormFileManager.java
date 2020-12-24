package com.github.mitrakumarsujan.datastorageservice.service.file;

import java.io.File;

/**
 * @author Sujan Kumar Mitra
 * @since 2020-10-29
 */
public interface FormFileManager {
    File getFile(String formId);

    boolean createFile(String formId);

    boolean existsFile(String formId);
}