package com.meijm.sqlite.dao;

import com.meijm.sqlite.data.Jsuser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author mjm
 * @createtime 2018/7/3 12:09
 */
@Repository
public interface JsuserDao extends JpaRepository<Jsuser, Long>, JpaSpecificationExecutor<Jsuser> {

}
