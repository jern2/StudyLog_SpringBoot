package com.test.jpa.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.test.jpa.JpaApplication;
import com.test.jpa.config.QueryDSLConfig;
import com.test.jpa.dto.AddressDTO;
import com.test.jpa.entity.Address;
import com.test.jpa.entity.Info;
import com.test.jpa.repository.AddressRepository;
import com.test.jpa.repository.CustomAddressRepository;
import com.test.jpa.repository.InfoRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TestController {

    private final QueryDSLConfig queryDSLConfig;

    private final JPAQueryFactory jpaQueryFactory;

    private final JpaApplication jpaApplication;
	
	private final AddressRepository addressRepository;
	private final InfoRepository infoRepository;
	private final CustomAddressRepository customAddressRepository;
	
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
	
	@GetMapping("/m16")
	public String m16(Model model) {
		
		//정렬
		//List<Address> list = addressRepository.findAllByOrderByAddressAsc();
		
		//List<Address> list = addressRepository.findAll(Sort.by("age"));
		//List<Address> list = addressRepository.findAll(Sort.by(Sort.Direction.DESC, "age"));
		
		//List<Address> list = addressRepository.findAll(Sort.by("age", "gender", "name"));
		
//		List<Address> list = addressRepository.findAll(Sort.by(
//			Sort.Order.asc("age"),
//			Sort.Order.desc("gender"),
//			Sort.Order.asc("name")
//		));
		
		//addressRepository.findByGenderOrderByAgeAsc("f")
		List<Address> list = addressRepository.findByGender(Sort.by("age"), "f");
		
		model.addAttribute("list", list);
		
		return "result";
	}
	
	@GetMapping("/m17")
	public String m17(Model model) {
		
		//페이징
		PageRequest pageRequest = PageRequest.of(0, 10);
		
		//List 대신 Page 사용
		Page<Address> plist = addressRepository.findAll(pageRequest);
		
		System.out.println(plist.getNumber()); //0 > 페이지 번호
		System.out.println(plist.getNumberOfElements()); //10 > 가져온 엔티티 수
		System.out.println(plist.getTotalElements()); //52 > 총 엔티티 수
		System.out.println(plist.getTotalPages()); //6 > 총 페이지 수
		System.out.println(plist.getSize()); //10 > 한 페이지 당 엔티티 수
		
		model.addAttribute("plist", plist);
		
		return "result";
	}
	
	@GetMapping("/m18")
	public String m18(Model model, @RequestParam(name = "page", required = false, defaultValue = "1") Integer page) {
		
		//m18
		//m18?page=1
		//m18?page=2
		//m18?page=3
		page--;
		
		PageRequest pageRequest = PageRequest.of(page, 10, Sort.by("name"));
		
		Page<Address> plist = addressRepository.findAll(pageRequest);
		
		//페이지바 > 이전과 동일하게 직접 구현
		StringBuilder sb = new StringBuilder();
		
		for (int i=0; i<plist.getTotalPages(); i++) {
			sb.append("""
				<a href="/m18?page=%d">%d</a>
			""".formatted(i + 1, i + 1));
		}
				
		model.addAttribute("plist", plist);
		model.addAttribute("sb", sb.toString());
		
		return "result";
	}
	
	@GetMapping("/m19")
	public String m19(Model model, Pageable pageable) {
		
		//m19?page=0
		//m19?page=1
		//m19?page=2
		
		//m19?page=0&size=10
		//m19?page=1&size=10
		//m19?page=2&size=10
		
		//m19?page=0&size=10&sort=name,asc
		//m19?page=1&size=10&sort=name,asc
		//m19?page=2&size=10&sort=name,asc
		
		//System.out.println(pageable.getSort());
		
		//m19?page=0&size=10&sort=seq,desc
		
		Page<Address> plist = addressRepository.findAll(pageable);
		
		StringBuilder sb = new StringBuilder();
		
		for (int i=0; i<plist.getTotalPages(); i++) {
			sb.append("""
				<a href="/m19?page=%d&size=10&sort=seq,desc">%d</a>
			""".formatted(i, i + 1));
		}
				
		model.addAttribute("plist", plist);
		model.addAttribute("sb", sb.toString());
		
		return "result";
	}
	
	@GetMapping("/m20")
	public String m20(Model model, @PageableDefault(size = 10) Pageable pageable) {
		
		Page<Address> plist = addressRepository.findAll(pageable);
		
		System.out.println(plist.getNumber());
		System.out.println(plist.getTotalPages());
		System.out.println(plist.getNumberOfElements());
		
		System.out.println(plist.hasContent());
		System.out.println(plist.hasNext());
		System.out.println(plist.hasPrevious());
		System.out.println(plist.nextOrLastPageable());
		System.out.println(plist.nextPageable());
		System.out.println(plist.previousOrFirstPageable());
		System.out.println(plist.previousPageable());
		System.out.println(plist.isFirst());
		System.out.println(plist.isLast());
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("""
			<a href="/m20?page=%d">이전 페이지</a>
		""".formatted(plist.previousOrFirstPageable().getPageNumber()));
		
		sb.append("""
			<a href="/m20?page=%d">다음 페이지</a>
		""".formatted(plist.nextOrLastPageable().getPageNumber()));	
								
		model.addAttribute("plist", plist);
		model.addAttribute("sb", sb.toString());
		
		return "result";
	}
	
	@GetMapping("/m21")
	public String m21(Model model) {
		
		//Join
		//- 테이블 > 엔티티 조작
		//- 엔티티에서 먼저 관계를 설정
		
		//1:1
		//tblAddress:tblInfo
		//Address:Info
		Optional<Info> info = infoRepository.findById(1L);
		
		System.out.println(info);
		
		model.addAttribute("info", info.get());
		
		return "result";
	}
	
	@GetMapping("/m22")
	public String m22(Model model) {
		
		//1:N
		//tblAddress:tblMemo
		//Address:Memo
		
		Optional<Address> user = addressRepository.findById(5L);
		
		model.addAttribute("user", user.get());
		
		return "result";
	}
	
	@GetMapping("/m23")
	public String m23(Model model) {
		
		//JPQL, Java Persistence Query Language
		//- SQL를 개발자가 직접 작성하는 방식
		//- 추상 메서드명은 자유롭게 작성한다.
		//- DB(테이블)을 대상으로 하는 SQL(X)
		//- 엔티티를 대상으로 하는 SQL(O)
		//- 엔티티를 반드시 별칭을 사용해야 된다.(as 생략 가능)
		
		
		//List<Address> list = addressRepository.findAll();
		
		//List<Address> list = addressRepository.list();
		
		//List<String> list = addressRepository.listName();
		//System.out.println(list);
		
		//List<Address> list = addressRepository.list("m");
		
		//List<Address> list = addressRepository.list(5);
		
		AddressDTO dto = new AddressDTO();
		dto.setGender("f"); // =
		dto.setAddress("강남구"); // like
		
		List<Address> list = addressRepository.list(dto);
		
		model.addAttribute("list", list);
		
		return "result";
	}
	
	@GetMapping("/m24")
	public String m24(Model model) {
		
		//Query DSL(Domain Specific Language)
		//- JPQL 작성을 도와주는 동적 쿼리 빌더
		//- 대부분의 행동이 자바 메서드로 제공된다.
		//- 직접 JPQL 작성에 비해 오타 감소
		
		//QClass 생성
		//- 각 엔티티에 대응되는 정적 클래스
		//- 해당 엔티티를 조작(쿼리)하는 각종 메서드를 구현해준다.
		
		//전체 리스트 조회
		List<Address> list = customAddressRepository.findAll();
		
		model.addAttribute("list", list);
		
		return "result";
	}
	
	@GetMapping("/m25")
	public String m25(Model model) {
		
		//단일 조회(레코드 1개)
		Address address = customAddressRepository.findByName("타조");
		
		model.addAttribute("address", Address.toDTO(address));
		
		return "result";
	}
	
	@GetMapping("/m26")
	public String m26(Model model) {
		
		//select * from
		//select name from
		
		List<String> names = customAddressRepository.findName();
		
		System.out.println(names);
		
		return "result";
	}
	
	@GetMapping("/m27")
	public String m27(Model model) {
		
		List<Tuple> list = customAddressRepository.findNameAndAgeAndAddress();
		
		//System.out.println("list: " + list);
		
		for (Tuple tuple : list) {
			System.out.println(tuple.get(0, String.class));
			System.out.println(tuple.get(1, Integer.class));
			System.out.println(tuple.get(2, String.class));
			System.out.println();
		}
		
		model.addAttribute("tlist", list);
		
		return "result";
	}
	
	@GetMapping("/m28")
	public String m28(Model model) {
	
		//일부 컬럼 조회
		//1. Tuple
		//2. DTO
		List<AddressDTO> list = customAddressRepository.findNameAndAgeAndAddress2();
		
		model.addAttribute("list", list);
		
		return "result";
	}

}












