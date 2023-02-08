package com.meijm.basis.service;


import com.meijm.basis.bean.TestBean;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    private TestBean testBean = new TestBean();

    public TestBean getTestBean() {
        return testBean;
    }

    public void setTestBean(TestBean testBean) {
        this.testBean = testBean;
    }
}
