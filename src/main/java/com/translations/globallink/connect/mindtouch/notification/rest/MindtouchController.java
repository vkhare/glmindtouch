package com.translations.globallink.connect.mindtouch.notification.rest;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.translations.globallink.connect.mindtouch.notification.model.service.ExportService;
import com.translations.globallink.connect.mindtouch.notification.model.service.ImportService;
import com.translations.globallink.connect.mindtouch.notification.rest.dto.ExportNotificationRequest;
import com.translations.globallink.connect.mindtouch.notification.rest.dto.ImportNotificationRequest;
import com.translations.globallink.connect.mindtouch.notification.rest.response.RestResponse;
import com.translations.globallink.connect.mindtouch.notification.rest.response.RestResponseError;

@RestController
@RequestMapping("/mindtouch")
public class MindtouchController {

	private static Logger logger = Logger.getLogger(MindtouchController.class);

	@Autowired
	ExportService exportService;

	@Autowired
	ImportService importService;

	//localhost:9090/globallink/mindtouch/export
	@RequestMapping(method = RequestMethod.POST, value = "/export")
	public RestResponse getExportNotification(@RequestBody(required = true) ExportNotificationRequest request) {
		logger.debug("Export Request: " + request);
		exportService.downloadFile(request);
		return new RestResponse("200", "OK");
	}

	//localhost:9090/globallink/mindtouch/download/source?jobId=bd5bcfb7-1375-43b1-9374-0888851be518
	@RequestMapping(method = RequestMethod.GET, value = "/download/source")
	public FileSystemResource getSourceFile(@RequestParam("jobId") String jobId, HttpServletResponse response) {
		logger.debug("Source Download Request: JobId - " + jobId);
		FileSystemResource resource = exportService.getSourceFile(jobId);
		if (resource != null) {
			response.setContentType("application/zip");
			response.addHeader("Content-Disposition", "attachment; filename=\"" + resource.getFilename() + "\"");
		}
		return resource;
	}

	//localhost:9090/globallink/mindtouch/download/target?jobId=bd5bcfb7-1375-43b1-9374-0888851be518&locale=fr-FR
	@RequestMapping(method = RequestMethod.GET, value = "/download/target")
	public FileSystemResource getTranslatedFile(@RequestParam("jobId") String jobId, @RequestParam("locale") String locale, HttpServletResponse response) {
		logger.debug("Download Request: JobId - " + jobId + ", Locale - " + locale);
		FileSystemResource resource = importService.getTranslatedFile(jobId, locale);
		if (resource != null) {
			response.setContentType("application/zip");
			response.addHeader("Content-Disposition", "attachment; filename=\"" + resource.getFilename() + "\"");
		}
		return resource;
	}

	//localhost:9090/globallink/mindtouch/upload/target?jobId=bd5bcfb7-1375-43b1-9374-0888851be518&locale=fr-FR
	//When calling this URL, must set file/multipart as request type
	//Create a multivalue map with "file" as key and the zip file as value
	@RequestMapping(method = RequestMethod.POST, value = "/upload/target")
	public RestResponse uploadTranslatedFile(@RequestParam(value = "file", required = true) MultipartFile file, @RequestParam("jobId") String jobId, @RequestParam("locale") String locale) {
		logger.debug("Upload Request: JobId - " + jobId + ", Locale - " + locale);
		try {
			importService.uploadTranslatedFile(file, jobId, locale);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new RestResponseError("400", e.getMessage());
		}
		
		return new RestResponse("200", "OK");
	}

	//localhost:9090/globallink/mindtouch/import?jobId=bd5bcfb7-1375-43b1-9374-0888851be518&locale=fr-FR
	@RequestMapping(method = RequestMethod.POST, value = "/import")
	public RestResponse getImportNotification(@RequestParam("jobId") String jobId, @RequestParam("locale") String locale, @RequestBody(required = true) ImportNotificationRequest request) {
		logger.debug("Import Request: " + request);
		if (StringUtils.isNotBlank(request.getStatus()) && "COMPLETED".equals(request.getStatus())) {
			importService.importCompleted(jobId, locale);
		}
		return new RestResponse("200", "OK");
	}

}
