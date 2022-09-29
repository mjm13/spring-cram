package com.meijm.alibaba.cmb.service;

import com.meijm.alibaba.cmb.model.Account;
import com.meijm.alibaba.cmb.dao.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional(rollbackOn = Exception.class)
@Service
public class AccountService {
    @Autowired
    private AccountDao accountDao;

    public void updateAccount(Account account){
        Account accountDb = accountDao.save(account);
        if(accountDb.getMoney().doubleValue() < 0){
            throw new RuntimeException("icbc账户操作失败，金额不能为负数!");
        }
    }
}
