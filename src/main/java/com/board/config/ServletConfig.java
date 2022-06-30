package com.board.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.FileSystemResource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

// ServletConfig: servlet-context.xml
// @EnableWebMvc: 
// @ComponentScan: 다른 패키지에 작성된 스프링의 객체(Bean)을 인식할 때 사용
// WebMvcConfigurer: 스프링 MVC와 관련된 설정을 메서드로 오버라이드 하는 형태를 이용할 때 사용
@EnableWebMvc
@ComponentScan(basePackages = {"com.board.controller"})
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class ServletConfig implements WebMvcConfigurer {
	
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		InternalResourceViewResolver bean = new InternalResourceViewResolver();
		bean.setViewClass(JstlView.class);
		bean.setPrefix("/WEB-INF/views/");
		bean.setSuffix(".jsp");
		registry.viewResolver(bean);
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}
	
	@Bean
	public MultipartResolver multipartResolver(){
		StandardServletMultipartResolver resolver = new StandardServletMultipartResolver();
		return resolver;
	}
	
//	@Bean(name = "multipartResolver")
//	public CommonsMultipartResolver getResolver() throws IOException {
//		
//		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
//		
//		resolver.setMaxUploadSize(1024 * 1024 * 10);
//		
//		resolver.setMaxUploadSizePerFile(1024 * 1024 * 2);
//		
//		resolver.setMaxInMemorySize(1024 * 1024);
//		
//		resolver.setUploadTempDir(new FileSystemResource("C:\\upload\\tmp"));
//		
//		resolver.setDefaultEncoding("UTF-8");
//		
//		return resolver;
//	}
	
}
