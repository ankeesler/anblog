package com.marshmallow.anblog;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import javax.transaction.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OpenAPI2SpringBoot.class)
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class DownloadTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void download() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders
            .get("/download")
            .accept(MediaType.APPLICATION_JSON)
    )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk());
  }
}
