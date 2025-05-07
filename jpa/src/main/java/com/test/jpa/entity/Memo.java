package com.test.jpa.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@Table(name = "tblMemo")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Memo {
	
	@Id
	private Long seq;
	
	private String memo;
	private LocalDate regdate;
	private Long aseq;
	
}







