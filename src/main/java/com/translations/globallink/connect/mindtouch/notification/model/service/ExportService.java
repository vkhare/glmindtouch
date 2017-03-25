package com.translations.globallink.connect.mindtouch.notification.model.service;

import org.springframework.core.io.FileSystemResource;

import com.translations.globallink.connect.mindtouch.notification.rest.dto.ExportNotificationRequest;

public interface ExportService {

	public void downloadFile(ExportNotificationRequest request);

	public FileSystemResource getSourceFile(String jobId);

}
