package com.test.jpa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.jpa.entity.Address;

//엔티티명 + "Repository"
//JpaRepository<엔티티 자료형, 엔티티 @Id 자료형> 
public interface AddressRepository extends JpaRepository<Address, Long> {

	Optional<Address> findByName(String name);

	Optional<Address> findByAge(Integer age);

	//Optional<Address> findFirst();

	Optional<Address> findFirstByAge(int age);

	List<Address> findFirst3ByAge(int age);

	List<Address> findFirst10ByAge(int age);

	Optional<Address> findByNameAndGender(String name, String gender);

	List<Address> findByNameOrGender(String name, String gender);

	List<Address> findByAgeAndGender(int age, String gender);

	List<Address> findByAgeAndGenderAndName(int i, String string, String string2);

	List<Address> findByAgeGreaterThan(int i);

	List<Address> findByAgeGreaterThanEqual(int i);

	List<Address> findByAgeLessThan(int i);

	List<Address> findByAgeBetween(int i, int j);

	List<Address> findByGenderAndAgeGreaterThanEqual(String string, int i);

	List<Address> findByAddressIsNull();

	List<Address> findByAddressIsNullAndGender(String string);

	List<Address> findByAddressIsNotNull();

	List<Address> findByAgeIn(List<Integer> ageList);

	List<Address> findByAgeNotIn(List<Integer> ageList);

	List<Address> findByAddressStartingWith(String string);

	List<Address> findByAddressEndingWith(String string);

	List<Address> findByAddressContains(String string);

	List<Address> findByAddressNotContains(String string);

	List<Address> findByAddressLike(String string);

	Optional<Address> findByNameIs(String string);

	Optional<Address> findByNameEquals(String string);

	Optional<Address> findByNameIgnoreCase(String string);

	List<Address> findAllByOrderByNameAsc();

	//List<Address> findAllByOrderByAgedesc();

	List<Address> findAllByOrderByAgeDesc();

	List<Address> findByGenderOrderByAgeAsc(String string);

	List<Address> findAllByOrderByGenderAscAgeDesc();

	//List<Address> findAllByOrderByGenderAgeDesc();
	
}










