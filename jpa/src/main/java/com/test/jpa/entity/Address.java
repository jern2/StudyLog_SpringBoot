package com.test.jpa.entity;

import java.util.List;

import com.test.jpa.dto.AddressDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

//Entity
//- DTO와 같은 상자 개념(X)
//- DB의 테이블과 연결된 객체
//- Entity 조작 > DB의 테이블에 반영
@Entity
@Getter
//@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tblAddress")
public class Address {
	
	@Id
	@SequenceGenerator(sequenceName = "seqAddress", name="address_seq_generator", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_seq_generator")
	private Long seq;
	
	private String name;
	private Integer age;
	private String address;
	private String gender;
	
	@OneToMany
	@JoinColumn(name = "aseq")
	private List<Memo> memo;	
	
	public static AddressDTO toDTO(Address address) {
		
		//Address > AddressDTO
		return AddressDTO.builder()
					.seq(address.getSeq())
					.name(address.getName())
					.age(address.getAge())
					.address(address.getAddress())
					.gender(address.getGender())
					.build();		
	}
	
	//setAddress(X)
	//updateAddress(O)
	public void updateAddress(String address) {
		this.address = address;
	}

}










