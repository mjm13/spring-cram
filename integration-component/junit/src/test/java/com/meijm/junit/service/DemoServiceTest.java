package com.meijm.junit.service;

import com.meijm.junit.dao.DemoDao;
import com.meijm.junit.model.Demo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoServiceTest {
    @MockBean
    private DemoDao demoDao;
    @Autowired
    private DemoService demoService;

    @Test
    public void insertTest() throws Exception {
        Mockito.when(demoDao.insert(Mockito.any())).then(invocation -> 1L);
        assertEquals(1L, demoService.insert(new Demo()));
    }

    @Test
    public void queryTest() throws Exception {
        Mockito.when(demoDao.query()).then(invocation -> new ArrayList<>());
        assertThat(demoService.query(), empty());
    }
}
