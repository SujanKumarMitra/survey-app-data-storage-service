package com.github.mitrakumarsujan.datastorageservice.service.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Sujan Kumar Mitra
 * @since 2020-10-31
 */
@Service
public class FileWriterServiceImpl implements FileWriterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileWriterServiceImpl.class);

    @Override
    public void appendData(CharSequence data, File file) {
        writeToFile(data, file, true);
    }

    @Override
    public void writeData(CharSequence data, File file) {
        writeToFile(data, file, false);
    }

    private void writeToFile(CharSequence data, File file, boolean append) {
        file.setWritable(true);
        try (PrintWriter writer = new PrintWriter(new FileWriter(file, append))) {
            writer.print(data);
            writer.println();
            writer.flush();
            LOGGER.info("writing complete in file '{}'", file.getAbsolutePath());
        } catch (IOException e) {
            LOGGER.error("Writing failed in '{}'. Reason:: {}", file.getAbsolutePath(), e.getMessage());
        }
    }


}