package com.test.jpa.dto;

import com.test.jpa.entity.Address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
	private Long seq;
	private String name;
	private Integer age;
	private String address;
	private String gender;
	
	
	public static Address toEntity(AddressDTO dto) {
		
		//AddressDTO > Address
		return Address.builder()
					.seq(dto.getSeq())
					.name(dto.getName())
					.age(dto.getAge())
					.address(dto.getAddress())
					.gender(dto.getGender())
					.build();		
	}
	
}









