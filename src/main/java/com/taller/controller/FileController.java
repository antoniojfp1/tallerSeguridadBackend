package com.taller.controller;

import com.taller.service.FileService;
import com.taller.util.CustomException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;
    
    @PostMapping(value = "/encrypt")
	public ResponseEntity<Resource> encryptFile(@RequestParam("file") MultipartFile file, 
												@RequestParam("password") String password) throws CustomException {
		MultipartFile encryptedFile = fileService.encryptFile(file, password);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getOriginalFilename() + "\"")
				.body(encryptedFile.getResource());
	}


	@PostMapping(value = "/decrypt")
	public ResponseEntity<Resource> decryptFile(@RequestParam("file") MultipartFile file, 
												@RequestParam("password") String password) throws CustomException {
		MultipartFile decryptedFile = fileService.decryptFile(file, password);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getOriginalFilename() + "\"")
				.body(decryptedFile.getResource());
	}

}