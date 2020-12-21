package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/upload")
public class UploadController {

	@GetMapping
	public String doGet() {
		return "upload";
	}


	@PostMapping
	public String doPost(
			@RequestParam MultipartFile upfile
			) {
//		画像が存在するかの確認
		if(upfile.isEmpty()) {
			return "upload";
		}

//		選択されたファイルの情報
		String fileName = upfile.getOriginalFilename();

		return "uploadDone";
	}

}
