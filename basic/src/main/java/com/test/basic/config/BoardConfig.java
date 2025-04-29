package com.test.basic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.test.basic.board.TagDAO;

@Configuration
public class BoardConfig {
	
	//<bean class="com.test.basic.board.TagDAO">
	//	<property name="age" ref="adto"/>
	//</bean>
	@Bean
	public TagDAO getTagDAO() {
		//ADTO adto = new ..;
		//tdao = new TagDAO();
		//tdao.setAdto(adto);
		//return tdao;
		return new TagDAO();
	}

}



