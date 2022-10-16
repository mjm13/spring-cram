package com.meijm.alibaba.cmb.controller;

import com.meijm.alibaba.cmb.feign.CmbAcoountFeign;
import com.meijm.alibaba.cmb.model.Account;
import com.meijm.alibaba.cmb.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private CmbAcoountFeign cmbAcoountFeign;

    @PostMapping("/update")
    public ResponseEntity<Boolean> update(@RequestBody Account account) {
        accountService.updateAccount(account);
        return ResponseEntity.ok(Boolean.TRUE);
    }

    @GetMapping("/print")
    public ResponseEntity<String> print(String word) {
        return ResponseEntity.ok("icbc:"+word);
    }

    @GetMapping("/printByOtherService")
    public ResponseEntity<String> printByOtherService() {
        String word = "param in icbc ";
        word = cmbAcoountFeign.print(word);
        return ResponseEntity.ok(word);
    }
}
