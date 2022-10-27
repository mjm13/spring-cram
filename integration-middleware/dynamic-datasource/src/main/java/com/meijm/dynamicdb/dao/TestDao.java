package com.meijm.dynamicdb.dao;

import com.meijm.dynamicdb.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TestDao extends JpaRepository<Test, Long>, JpaSpecificationExecutor<Test> {
}
