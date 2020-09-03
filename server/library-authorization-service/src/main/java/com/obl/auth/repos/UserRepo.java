package com.obl.auth.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.obl.auth.beans.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

	@Query("select U from User U where U.userEmail=:mailId")
	public Optional<User> findByMailId(@Param("mailId") String mailId);
	
	@Query("select U from User U where U.userPhoneNo=:phoneNo")
	public Optional<User> findByPhoneNo(@Param("phoneNo") String phoneNo);
}
