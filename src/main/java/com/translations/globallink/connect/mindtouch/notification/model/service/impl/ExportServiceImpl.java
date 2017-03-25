package com.translations.globallink.connect.mindtouch.notification.model.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import com.translations.globallink.connect.mindtouch.notification.GloballinkConstants;
import com.translations.globallink.connect.mindtouch.notification.model.service.ExportService;
import com.translations.globallink.connect.mindtouch.notification.rest.dto.ExportNotificationRequest;

@Service("exportService")
public class ExportServiceImpl implements ExportService, GloballinkConstants {

	private static Logger logger = Logger.getLogger(ExportServiceImpl.class);

	@Value("${repo.directory.path}")
	String repoDirectory;

	@Override
	public void downloadFile(ExportNotificationRequest request) {

		logger.debug("Repo Directory: " + repoDirectory);

		try {
			String folderName = request.getId();
			Path path = Paths.get(repoDirectory + File.separator + folderName);
			if (Files.exists(path) && Files.isDirectory(path)) {
				FileUtils.deleteQuietly(new File(path.toAbsolutePath().toString()));
			}
			path = Files.createDirectory(path);
			downloadFromURL(request.getId(), request.getUri(), path.toAbsolutePath().toString());

		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	private void downloadFromURL(String id, String fileURL, String saveDir) throws IOException {

		logger.info("File download start.");
		logger.debug("Directory: " + saveDir);

		URL url = new URL(fileURL);
		HttpsURLConnection httpConn = (HttpsURLConnection) url.openConnection();
		int responseCode = httpConn.getResponseCode();

		// always check HTTP response code first
		if (responseCode == HttpURLConnection.HTTP_OK) {

			String fileName = "";
			String disposition = httpConn.getHeaderField("Content-Disposition");

			if (disposition != null) {
				// extracts file name from header field
				int index = disposition.indexOf("filename=");
				if (index > 0) {
					fileName = disposition.substring(index + 10, disposition.length() - 1);
				}
			} else {
				fileName = FilenameUtils.getName(url.getPath());
			}

			// opens input stream from the HTTP connection
			InputStream inputStream = httpConn.getInputStream();
			String saveFilePath = saveDir + File.separator + fileName;

			// opens an output stream to save into file
			FileOutputStream outputStream = new FileOutputStream(saveFilePath);

			int bytesRead = -1;
			byte[] buffer = new byte[4096];
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}

			outputStream.close();
			inputStream.close();

			logger.info("File download complete.");

		}

		httpConn.disconnect();
	}

	@Override
	public FileSystemResource getSourceFile(String jobId) {

		try {
			File file = new File(repoDirectory + File.separator + jobId);
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

}
