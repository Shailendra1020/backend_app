package com.thenewsgrit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thenewsgrit.entities.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role, Integer>{

}
   