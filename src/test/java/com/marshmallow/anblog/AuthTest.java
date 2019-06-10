package com.marshmallow.anblog;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
public class AuthTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void success() throws Exception {
    final String rspString = mockMvc.perform(MockMvcRequestBuilders
            .post("/oauth/token")
            .param("grant_type", "client_credentials")
            .with(user("anblog_default_client_id").password("anblog_default_client_secret"))
    )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();

    final JsonNode rspJson = new ObjectMapper().readTree(rspString);
    final String token = rspJson.get("token").asText();

    mockMvc.perform(MockMvcRequestBuilders
            .get("/posts")
            .header("Authorization", "Bearer " + token)
    )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk());
  }

  @Test
  public void noTokenForWrongUser() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders
            .post("/oauth/token")
            .param("grant_type", "client_credentials")
            .with(user("wrong_user").password("anblog_default_client_secret"))
    )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isUnauthorized());
  }

  @Test
  public void noTokenForNoUser() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders
            .post("/oauth/token")
            .param("grant_type", "client_credentials")
    )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isUnauthorized());
  }

  @Test
  public void actuatorRequiresNoAuth() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders
            .get("/actuator/info")
    )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk());

    mockMvc.perform(MockMvcRequestBuilders
            .get("/actuator/health")
    )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk());
  }

}
