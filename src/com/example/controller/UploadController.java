package com.example.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/upload")
public class UploadController {

	@GetMapping
	public String doGet(
			HttpServletRequest request,
			Model model
			) {

		//imagesフォルダの全ファイルを取得
//複数回呼び出されるので外部メソッド化する
//		File folder = new File(request.getServletContext().getRealPath("/images"));
//		File[] fileList = folder.listFiles();


//		var fileList = getFileList(request);

		model.addAttribute("fileList",getFileList(request));


		return "upload";
	}


	@PostMapping
	public String doPost(
			HttpServletRequest request,
			@RequestParam MultipartFile upfile,
			Model model
			) throws IllegalStateException, IOException {
//		画像が存在するかの確認
		if(upfile.isEmpty()) {

			model.addAttribute("fileList",getFileList(request));

			return "upload";
		}

//		選択されたファイルの情報
		String fileName = upfile.getOriginalFilename();
		String contentType = upfile.getContentType();
		long fileSize = upfile.getSize();

//		画像以外の場合アップさせない
		if(!contentType.startsWith("image/")){

			model.addAttribute("fileList",getFileList(request));

			return "upload";
		}

		//imagesフォルダにファイルを保存
		File dest = new File(request.getServletContext().getRealPath("/images") + "/" + fileName);

		System.out.println(dest);
//		アップロードさせる
		upfile.transferTo(dest);


		model.addAttribute("fileName", fileName);
		model.addAttribute("contentType", contentType);
		model.addAttribute("fileSize", fileSize);


		return "uploadDone";
	}


	private File[] getFileList(HttpServletRequest request) {
		File folder = new File(request.getServletContext().getRealPath("/images"));
		File[] fileList = folder.listFiles();

		return fileList;

	}



}
