package com.meijm.seataDemo2.dao;

import com.meijm.seataDemo2.entity.Demo2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface Demo2Dao extends JpaRepository<Demo2, Long>, JpaSpecificationExecutor<Demo2> {
}
