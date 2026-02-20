package com.example.comicbe.jpa.repository;


import com.example.comicbe.constant.SysConfigCode;
import com.example.comicbe.jpa.entity.SysConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("SysConfigRepository")
public interface SysConfigRepository extends JpaRepository<SysConfig, Long>, JpaSpecificationExecutor<SysConfig> {

    Optional<SysConfig> findByCode(SysConfigCode code);

    List<SysConfig> findAllByCode(SysConfigCode code);
}
