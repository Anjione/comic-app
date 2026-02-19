package com.example.comicbe.jpa.repository;


import com.example.comicbe.jpa.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("RoleRepository")
public interface RoleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {
    List<Role> findByName(String name);

    List<Role> findByNameIn(List<String> names);

}
