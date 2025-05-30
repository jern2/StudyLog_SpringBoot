package com.test.basic.dto;

public class User {
	
	private Long seq;
	private String name;
	private Integer age;
	private String address;
	private String gender;
	
	public User(Builder builder) {
		this.seq = builder.seq;
		this.name = builder.name;
		this.age = builder.age;
		this.address = builder.address;
		this.gender = builder.gender;
	}
	
	//중첩 클래스
	public static class Builder {
		
		private Long seq;
		private String name;
		private Integer age;
		private String address;
		private String gender;
		
		//Setter
		public Builder setSeq(Long seq) {
			this.seq = seq;
			return this;
		}
		
		public Builder setName(String name) {
			this.name = name;
			return this;
		}
		
		public Builder setAge(Integer age) {
			this.age = age;
			return this;
		}
		
		public Builder setAddress(String address) {
			this.address = address;
			return this;
		}
		
		public Builder setGender(String gender) {
			this.gender = gender;
			return this;
		}
		
		public User build() {
			return new User(this);
		}
		
	}//Builder

	@Override
	public String toString() {
		return "User [seq=" + seq + ", name=" + name + ", age=" + age + ", address=" + address + ", gender=" + gender
				+ "]";
	}	

}


















