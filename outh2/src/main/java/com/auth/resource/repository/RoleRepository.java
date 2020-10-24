package com.auth.resource.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.auth.resource.model.Roles;

public interface RoleRepository extends JpaRepository<Roles, Long> {
	Roles findByUsername(String username);
}
