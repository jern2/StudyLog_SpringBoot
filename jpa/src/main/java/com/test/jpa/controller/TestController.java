package com.test.jpa.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.test.jpa.JpaApplication;
import com.test.jpa.dto.AddressDTO;
import com.test.jpa.entity.Address;
import com.test.jpa.repository.AddressRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TestController {

    private final JpaApplication jpaApplication;
	
	private final AddressRepository addressRepository;
	
	@GetMapping("/add")
	public String add(Model model) { 
		
		return "add";
	}
	
	@GetMapping("/edit")
	public String edit(Model model) { 
		
		Optional<Address> address = addressRepository.findById(51L);
		
		model.addAttribute("address", Address.toDTO(address.get()));
		
		return "edit";
	}
	
	@PostMapping("/m1")
	public String m1(Model model, AddressDTO dto) {
		
		/*
		
			JPA
			
			1. Query Method
			- 정해진 키워드 사용 > 메서드명 생성 > 메서드 호출 > SQL 생성 > JDBC 실행
						
			2. JPQL, Java Persistence Query Language
			- JPA에서 질의에 사용하는 전용 질의문(= SQL과 유사)
			- 직업 질의문 작성 > 실행 > JDBC 실행
			
			3. Query DSL
			- SQL 관련 모든 행동을 자바 메서드로 제공
			- 자바 메서드 > 실행 > JDBC 실행
		
			정리
			1. 단순한 CRUD > Query Method
			2. 정적이면서 복잡한 쿼리 > JPQL
			3. 정적/동적이면서 복잡한 쿼리 > Query DSL
			
			
			
			
			1. Query Method
			- 정해진 키워드 사용 > 메서드명 생성 > 메서드 호출 > SQL 생성 > JDBC 실행
			- 메서드 이름으로 쿼리를 생성하는 방식 > 정해진 키워드 + 조합 > 메서드명 > JPA가 메서드명을 분석해서 > JPQL을 생성 > 변환 > SQL > 실행
			- 장점
				- 별도의 쿼리을 작성하지 않는다. > 자동으로 생성
				- 단순한 CRUD에 효율적
				- 메서드명 == SQL > 가독성 향상O,X
			- 단점
				- 복잡한 업무 쿼리에는 부적합
				- 복잡한 업무 구현 > 메서드명 매우 길어짐 > 가독성 저하
				- 너무 복잡한 업무 > 이 방식으로는 불가능
			
		*/
		
		//[C]RUD
		//- 레코드 추가하기
		//- 추가할 정보 > 엔티티 객체를 생성하기(***)
		//- AddressDTO > (매핑,변환) > Address
//		Address address = Address.builder()
//							.name("꿀꿀이")
//							.age(5)
//							.address("서울시 강남구 역삼동")
//							.gender("m")
//							.build();
		
//		Address address = Address.builder()
//							.name(dto.getName())
//							.age(dto.getAge())
//							.address(dto.getAddress())
//							.gender(dto.getGender())
//							.build();
		
		Address address = AddressDTO.toEntity(dto);
		
		addressRepository.save(address); //insert 변환 + 실행
		
		return "result";
	}
	
	@GetMapping("/m2")
	public String m2(Model model) {
		
		//C[R]UD
		//- 1개의 레코드 가져오기
//		addressRepository.getById(1L)
//		addressRepository.getOne(1L)
		
		Optional<Address> address = addressRepository.findById(1L);
		
		System.out.println(address.isEmpty());  //false > true
		System.out.println(address.isPresent());//true > false
		
//		if (address.isPresent()) {
//			//model.addAttribute("address", address.get());
//			model.addAttribute("address", Address.toDTO(address.get()));			
//		}
		
		//Optional > ifPresent() > Address
		address.ifPresent(value 
			-> model.addAttribute("address", Address.toDTO(value)));
		
		return "result";	
	}
	
	@PostMapping("/m3")
	public String m3(Model model, AddressDTO dto) {
		
		//CR[U]D
		//- 레코드 수정하기
		//	1. 생성 후 수정
		//	2. 검색 후 수정
		
		//1. 꿀꿀이 수정
//		Address address = Address.builder()
//							.seq(51L)
//							.name("꿀꿀이")
//							.age(10)
//							.address("서울시 강동구 천호동")
//							.gender("m")
//							.build();
		
//		Address address = AddressDTO.toEntity(dto);
		
		
		//2. 검색 후 수정
		Address address = addressRepository.findById(51L).get();
		System.out.println(address);
		address.updateAddress("서울시 강남구 대치동"); //무게감!!
		
		Address result = addressRepository.save(address); //수정 > update 변환 + 실행
		
		model.addAttribute("address", Address.toDTO(result));
		
		return "result";
	}
	
	@GetMapping("/m4")
	public String m4(Model model) {
		
		//CRU[D]
		//- 레코드 삭제하기
		//Address address = Address.builder()
		//					.seq(51L)
		//					.build();
		
		Optional<Address> address = addressRepository.findById(51L);
		
		if (address.isPresent()) {
			addressRepository.delete(address.get());
		}
		
		return "result";
	}
	
//	@GetMapping("/m5")
//	public String m5(Model model, @RequestParam("name") String name) {
	@GetMapping("/m5")
	public String m5(Model model, @RequestParam("age") Integer age) {
		
		//m5?name=강아지
		//System.out.println("name: " + name);
		
		//Query Method
		//1. 이미 만들어진 메서드 사용(save, findById, delete 등)
		//2. 메서드명을 직접 만들어서 사용(+ 키워드 사용)
		
		//JPA(Query Method)은 메서드명을 가지고 분석 > SQL 생성 + 실행
		//- 메서드명 = Subject Part + Predicate Part
		//           = 가져올 컬럼  + 조건
		
		//Select Query
		//- findBy, getBy, readBy, queryBy, searchBy..
		//- findBy(***)
		
		//addressRepository.findById(51L);
		//Optional<Address> address = addressRepository.findByName(name);
		Optional<Address> address = addressRepository.findByAge(age);
		
		model.addAttribute("address", Address.toDTO(address.get()));
		
		return "result";
	}
	
	@GetMapping("/m6")
	public String m6(Model model) {
		
		//카운트
		long count = addressRepository.count();
		
		//레코드 존재? count(*) > 0
		boolean exist = addressRepository.existsById(100L);
		
		model.addAttribute("count", count);
		model.addAttribute("exist", exist);
		
		return "result";
	}
	
	@GetMapping("/m7")
	public String m7(Model model) {
		
		//전체 select
		List<Address> list = addressRepository.findAll();
		
		//List<Address> > (변환) > List<AddressDTO>
		model.addAttribute("list", list);
		
		return "result";
	}
	
	@GetMapping("/m8")
	public String m8(Model model) {
		
		//First, Top
		//- 가져올 레코드 개수를 지정한다.
		//- 결과셋에서 위에서부터 몇개
		//- select top 3 * from table;
		//- select * from table limit 0, 3;
		//- select * from table where rownum <= 3;
		
		//- 키워드 뒤에 가져올 레코드 수를 지정. 생략(1)
		
		Optional<Address> address = addressRepository.findFirstByAge(5);		
		model.addAttribute("address", Address.toDTO(address.get()));
		
//		List<Address> list = addressRepository.findFirst3ByAge(5);
//		model.addAttribute("list", list);
		
		List<Address> list = addressRepository.findFirst10ByAge(5);
		model.addAttribute("list", list);
		
		return "result";
	}
	
	@GetMapping("/m9")
	public String m9(Model model) {
		
		//And, Or
		
		//where name = '강아지' and gender = 'm'
		Optional<Address> address 
			= addressRepository.findByNameAndGender("강아지", "m");
		model.addAttribute("address", Address.toDTO(address.get()));
		
		//List<Address> list = addressRepository.findByNameOrGender("강아지", "f");
		
		//List<Address> list = addressRepository.findByAgeAndGender(5, "f");
		
		List<Address> list = addressRepository.findByAgeAndGenderAndName(5, "f", "공작새");
		model.addAttribute("list", list);
		
		return "result";
	}
	
	@GetMapping("/m10")
	public String m10(Model model) {
		
		//After, Before, GreaterThan(GreaterThanEqual), LessThan(LessThanEqual), Between
		//- After, GreaterThan(GreaterThanEqual) : 크다
		//- Before, LessThan(LessThanEqual) : 작다
		//- After, Before : 날짜시간 비교
		//- GreaterThan, LessThan : 숫자 비교
		//- Between: 범위(숫자, 날짜시간)
		
		//List<Address> list = addressRepository.findByAgeGreaterThan(3);
		//List<Address> list = addressRepository.findByAgeGreaterThanEqual(3);
		
		//List<Address> list = addressRepository.findByAgeLessThan(3);
		
		//List<Address> list = addressRepository.findByAgeBetween(3, 5);
		
		List<Address> list = addressRepository.findByGenderAndAgeGreaterThanEqual("m", 5);
		
		model.addAttribute("list", list);
		
		return "result";
	}
	
	@GetMapping("/m11")
	public String m11(Model model) {
		
		//isEmpty, isNull
		//isNotEmpty, isNotNull
		//- isNull > null 체크(where tel is null)
		//- isEmpty > 빈문자열, 컬렉션 size(0) 등을 체크
		
		//List<Address> list = addressRepository.findByAddressIsNull();
		//List<Address> list = addressRepository.findByAddressIsNullAndGender("f");
		
		List<Address> list = addressRepository.findByAddressIsNotNull();
		
		model.addAttribute("list", list);
		
		return "result";
	}
	
	@GetMapping("/m12")
	public String m12(Model model) {
		
		//In, NotIn
		//- 열거형 조건
		//- 매개변수 > List 요구
		
		//3, 5, 7
		List<Integer> ageList = List.of(3, 5, 7);
		//ageList.add(9);
		//System.out.println(ageList); //[3, 5, 7]
		
		//List<Address> list = addressRepository.findByAgeIn(ageList);
		List<Address> list = addressRepository.findByAgeNotIn(ageList);
		
		model.addAttribute("list", list);
	
		return "result";
	}
	
	@GetMapping("/m13")
	public String m13(Model model) {
		
		//StartingWith/StartsWith
		//EndingWith/EndsWith
		//Contains
		//Like
		
		//where address like '서울시%'
		//where address like '%역삼동'
		//where address like '%강남%'
		
		//List<Address> list = addressRepository.findByAddressStartingWith("서울특별시 강남구");
		//List<Address> list = addressRepository.findByAddressEndingWith("층");
		
		//List<Address> list = addressRepository.findByAddressContains("빌라");
		
		//List<Address> list = addressRepository.findByAddressNotContains("빌라");
		
		List<Address> list = addressRepository.findByAddressLike("%타워%");
		
		model.addAttribute("list", list);
			
		return "result";
	}
	
	@GetMapping("/m14")
	public String m14(Model model) {
		
		//Is, Equals			
		
		//Optional<Address> address = addressRepository.findByName("강아지");
		
//		Optional<Address> address 
//			= addressRepository.findByNameIs("강아지");
		
//		Optional<Address> address 
//			= addressRepository.findByNameEquals("강아지");
		
		Optional<Address> address 
			= addressRepository.findByNameIgnoreCase("강아지");
		
		//IgnoreCase
		//- addressRepository.findByColor("White")
		//- addressRepository.findByColorIgnoreCase("White")
		
		model.addAttribute("address", Address.toDTO(address.get()));
		
		return "result";
	}
	
	@GetMapping("/m15")
	public String m15(Model model) {
		
		//정렬
		//- OrderBy컬럼명Asc
		//- OrderBy컬럼명Desc
		
		//다중 정렬
		//- OrderBy컬럼명Asc컬럼명Desc컬럼명Asc
		
		//List<Address> list = addressRepository.findAllByOrderByNameAsc();
		
		//List<Address> list = addressRepository.findAllByOrderByAgeDesc();
		
//		List<Address> list = addressRepository.findByGenderOrderByAgeAsc("m");

		 //List<Address> list = addressRepository.findAllByOrderByNameAsc();
		
		List<Address> list = addressRepository.findAllByOrderByGenderAscAgeDesc();
		
		model.addAttribute("list", list);
		
		return "result";
	}

}












