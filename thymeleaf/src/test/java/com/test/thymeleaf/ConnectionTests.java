package com.test.thymeleaf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.sql.Connection;

import javax.sql.DataSource;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.test.thymeleaf.mapper.AddressMapper;

@SpringBootTest
public class ConnectionTests {
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private AddressMapper mapper;

	@DisplayName("데이터 소스가 연결되었습니다.")
	@Test
	public void testConnection() throws Exception {
		Connection conn = dataSource.getConnection();
		assertEquals(false, conn.isClosed());
	}
	
	@DisplayName("인터페이스 매퍼가 정상적으로 동작합니다.")
	@Test
	public void testMapper() {
		
		System.out.println(mapper.time());
		
		assertNotEquals("", mapper.time());
		
	}
	
}









