package com.alex.spring_rest_task.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PhraseControllerTest {
    @Autowired
    PhraseController phraseController;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testPost() throws Exception{
        mockMvc.perform(post("/phrase")
                .param("playerName", "Alex")
                .param("phrase", "Madam, I'm Adam!"))
                .andExpect(content().string(containsString("\"points\":11")));

        mockMvc.perform(post("/phrase")
                .param("playerName", "Jack")
                .param("phrase", "Madam, I'm Adam!"))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(post("/phrase")
                .param("playerName", "Jack")
                .param("phrase", "rotor"))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(post("/phrase")
                .param("playerName", "Alex")
                .param("phrase", "test"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testGet() throws Exception{
        mockMvc.perform(get("/phrase"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}