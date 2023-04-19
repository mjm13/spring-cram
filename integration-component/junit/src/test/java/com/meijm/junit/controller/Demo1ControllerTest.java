package com.meijm.junit.controller;

import com.meijm.junit.service.DemoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class Demo1ControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private DemoService demoService;

    @Test
    public void insertTest() throws Exception {
        Mockito.when(demoService.insert(Mockito.any())).then(invocation -> 1L);
        ResultActions actions = mvc.perform(post("/demo1/insert").contentType(MediaType.APPLICATION_JSON).content("{\"i\":2,\"str\":\"aaa\",\"lon\":3}"));
        actions.andExpect(status().isOk())
                .andExpect(content().string("2"));
    }

    @Test
    public void queryTest() throws Exception {
        Mockito.when(demoService.query()).then(invocation -> new ArrayList<>());
        ResultActions actions = mvc.perform(get("/demo1/query"));
        actions.andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

}
