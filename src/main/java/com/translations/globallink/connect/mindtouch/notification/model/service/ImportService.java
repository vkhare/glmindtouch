package com.translations.globallink.connect.mindtouch.notification.model.service;

import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.MultipartFile;

public interface ImportService {

	public FileSystemResource getTranslatedFile(String jobId, String locale);
	
	public void uploadTranslatedFile(MultipartFile file, String jobId, String locale) throws Exception;
	
	public void importCompleted(String jobId, String locale);

}
