package com.test.security.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
//@Table(name = "Member")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

	@Id
	@SequenceGenerator(sequenceName = "seqMember", name="member_seq_generator", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq_generator")
	private Long seq;
	
	private String username;
	private String password;
	private String role;
	
}












