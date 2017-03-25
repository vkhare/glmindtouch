package com.translations.globallink.connect.mindtouch.notification.model.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.translations.globallink.connect.mindtouch.notification.GloballinkConstants;
import com.translations.globallink.connect.mindtouch.notification.model.service.ImportService;

@Service("importService")
public class ImportServiceImpl implements ImportService, GloballinkConstants {

	private static Logger logger = Logger.getLogger(ImportServiceImpl.class);

	@Value("${repo.directory.path}")
	String repoDirectory;

	@Override
	public FileSystemResource getTranslatedFile(String jobId, String locale) {

		try {
			File file = new File(repoDirectory + File.separator + jobId + File.separator + locale);
			if (file != null && file.exists() && file.isDirectory()) {
				File[] files = file.listFiles();
				if (files != null) {
					for (File zipFile : files) {
						if (zipFile != null && zipFile.isFile()) {
							if ("zip".equalsIgnoreCase(FilenameUtils.getExtension(zipFile.getAbsolutePath()))) {
								return new FileSystemResource(zipFile);
							}
						}
					}
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return null;
	}

	@Override
	public void importCompleted(String jobId, String locale) {

		try {
			File file = new File(repoDirectory + File.separator + jobId + File.separator + locale);
			if (file != null && file.exists() && file.isDirectory()) {
				File[] files = file.listFiles();
				if (files != null) {
					for (File zipFile : files) {
						if (zipFile != null && zipFile.isFile()) {
							FileUtils.deleteQuietly(zipFile);
						}
					}
				}
				FileUtils.deleteQuietly(file);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void uploadTranslatedFile(MultipartFile multipartFile, String jobId, String locale) throws Exception {
		if (multipartFile == null || multipartFile.isEmpty()) {
			throw new Exception("File is null or empty.");
		}

		String fileName = multipartFile.getOriginalFilename();
		File file = new File(repoDirectory + File.separator + jobId + File.separator + locale);
		if (file == null || !file.exists() || !file.isDirectory()) {
			Files.createDirectory(Paths.get(file.getAbsolutePath()));
		}
		file = new File(repoDirectory + File.separator + jobId + File.separator + locale + File.separator + fileName);
		FileOutputStream out = new FileOutputStream(file);
		IOUtils.write(multipartFile.getBytes(), out);
		out.close();
	}

}
