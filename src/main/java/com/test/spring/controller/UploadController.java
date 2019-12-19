package com.test.spring.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadController {
	
	private static String UPLOADED_FOLDER = "C://temp//";
	
	@RequestMapping(path = "/upload", method = RequestMethod.POST,consumes = { "multipart/form-data" })
	public ResponseEntity<String> uploadFile(@RequestPart("file") MultipartFile uploadfile) {

		if (uploadfile.isEmpty()) {
			return new ResponseEntity<String>("Please Select a file!", HttpStatus.OK);
		}
		try {
			saveUploadedFiles(Arrays.asList(uploadfile));
		} catch (IOException e) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<String>("Successfully uploaded - " +
				uploadfile.getOriginalFilename(), HttpStatus.OK);

	}

	private void saveUploadedFiles(List<MultipartFile> files) throws IOException {

		for (MultipartFile file : files) {

			if (file.isEmpty()) {
				continue; 
			}

			byte[] bytes = file.getBytes();
			File fileObj = new File(UPLOADED_FOLDER + file.getOriginalFilename());
			Path path = fileObj.toPath();
			Files.write(path, bytes);
		}
	}
}
