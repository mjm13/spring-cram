package com.meijm.alibaba.transfer.service;

import com.meijm.alibaba.transfer.dao.TransferLogDao;
import com.meijm.alibaba.transfer.model.TransferLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional(rollbackOn = Exception.class)
@Service
public class TransferLogService {
    @Autowired
    private TransferLogDao transferLogDao;

    public void updateAccount(TransferLog account) {
        TransferLog transferLog = transferLogDao.save(account);
    }
}
