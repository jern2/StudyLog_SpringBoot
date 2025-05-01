package com.test.jpa;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.test.jpa.entity.Address;
import com.test.jpa.repository.AddressRepository;

@SpringBootTest
public class AddressRepositoryTests {

	@Autowired
	private AddressRepository repo;
	
	@Test
	public void testRepo() {
		
		assertNotNull(repo);
		
		Optional<Address> address = repo.findById(1L);
		
		System.out.println(address);
		
	}
	
}













