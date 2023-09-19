package com.meijm.seataDemo1.dao;

import com.meijm.seataDemo1.entity.Demo1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface Demo1Dao extends JpaRepository<Demo1, Long>, JpaSpecificationExecutor<Demo1> {
}
