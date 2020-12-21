package com.example.config;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@ComponentScan(basePackages = "com.example")
@EnableWebMvc
@EnableTransactionManagement
@MapperScan("com.example.dao")
public class ApplicationConfig implements WebMvcConfigurer {

//	ViewResolverの設定
//	普通のプログラムとは違う
	@Bean
	public ViewResolver viewResolver() {

		var viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/view/");
		viewResolver.setSuffix(".jsp");

		return viewResolver;

	}


//	静的ファイルの有効化をする
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		フロントコントローラーにさばいてもらいたくないものを登録する
	registry.addResourceHandler("/images/**").addResourceLocations("/images/");
	registry.addResourceHandler("/css/**").addResourceLocations("/css/");
	registry.addResourceHandler("/js/**").addResourceLocations("/js/");

	}



//	バリデーションのエラー時に出すメッセージを変更するファイル

	@Override
	public Validator getValidator() {
	var validator = new LocalValidatorFactoryBean();
	validator.setValidationMessageSource(messageSource());
	return validator;
	}
	@Bean
	public ResourceBundleMessageSource messageSource() {
	var messageSource = new ResourceBundleMessageSource();
	messageSource.setBasename("messages");
	return messageSource;
	}


//	データベース関連の設定を書いていく

	@Bean
	public DataSource dataSource() throws Exception{
//		InitialContext ctx = new InitialContext();
////		個々のアドレスを変更することで、接続先を変更できます。
//		var ctxLU = ctx.lookup("java:comp/env/jdbc/practice_db");
//		return (DataSource)ctx;

//		c3p0
//		引数はなしで
		var dataSource = new ComboPooledDataSource();
//		セットドライバークラスを設定する
		dataSource.setDriverClass("com.mysql.cj.jdbc.Drivar");
//		今までサーバーＸＭＬに書いていたものと一緒。jdbcUrl
		String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/practice_db"
				+ "?"
				+ "useUnicode=true"
				+ "&"
				+ "characterEncoding=utf8"
				+ "&"
				+ "serverTimezone=JST";

		dataSource.setJdbcUrl(jdbcUrl);
		dataSource.setUser("root");
		dataSource.setPassword("");

		return dataSource;

	}

//	名前はなんでも大丈夫　
//	@Bean
//	public NamedParameterJdbcTemplate jdbcTemplate()throws Exception{
////上記のdataSourceを呼び出して利用する。
//		return new NamedParameterJdbcTemplate(dataSource());
//	}


//	トランザクションマネージャ
	@Bean
	public DataSourceTransactionManager txManager() throws Exception{
	return new DataSourceTransactionManager(dataSource());
	}



//	データソースの指定
	@Bean
	public SqlSessionFactoryBean sqlSessionFactory() throws Exception{
//	データソース処理をしてくれるファクトリーを作る
		var factory = new SqlSessionFactoryBean();

		var resolver = ResourcePatternUtils.getResourcePatternResolver(new DefaultResourceLoader());

//		スネークケースをキャメルケースに変更する。
//		confがもうすでに別名でインポートされているので
//		ＦＱＣＮで指定する
		var conf = new org.apache.ibatis.session.Configuration();
		conf.setMapUnderscoreToCamelCase(true);
		factory.setConfiguration(conf);

		factory.setDataSource(dataSource());
//		XMLファイルを指定する
		factory.setMapperLocations(resolver.getResources("classpath:**/dao/**/*.xml"));
	return factory;
	}




}
