package com.example.config;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class DispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {




		return new Class[] {ApplicationConfig.class};
	}

	@Override
	protected String[] getServletMappings() {

		return new String[] {"/"};
	}

//	文字化け表示フィルター
	@Override
	protected Filter[] getServletFilters() {
		var filter = new CharacterEncodingFilter();
		filter.setEncoding("UTF-8");
		filter.setForceEncoding(true);
		return new Filter[] {filter};

	}


//	アップロード用の設定
	@Override
	protected void customizeRegistration(Dynamic registration) {
//一時ファイルのロケーション
		String location = "C:/temp";
//		最大アップロードサイズ これは5M 一枚のサイズ
		long maxFilesize = 5242880;
//		フォーム全体でのサイズ　合計サイズ　画像だけでなく、文字つも含む
		long maxRequestSize = 5242880;
//		ファイルサイズスレッシュフォールド
		int fileSizeThreshold = 0;

		var config = new MultipartConfigElement(location,maxFilesize,maxRequestSize,fileSizeThreshold);

//		設定を登録する
		registration.setMultipartConfig(config);



	}









}
