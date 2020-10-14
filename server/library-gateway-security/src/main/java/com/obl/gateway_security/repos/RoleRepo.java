package com.obl.gateway_security.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.obl.gateway_security.models.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role, Integer> {

	@Query("select R from Role R where R.name=:roleName")
	public Optional<Role> getRoleFromRoleName(@Param("roleName") String roleName);
}
