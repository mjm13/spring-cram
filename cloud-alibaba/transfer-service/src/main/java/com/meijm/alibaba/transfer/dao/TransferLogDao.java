package com.meijm.alibaba.transfer.dao;

import com.meijm.alibaba.transfer.model.TransferLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferLogDao extends JpaRepository<TransferLog, Long>, JpaSpecificationExecutor<TransferLog> {
}
