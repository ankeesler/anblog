package com.marshmallow.anblog;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openapitools.model.Post;
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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OpenAPI2SpringBoot.class)
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PostTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void createPosts() throws Exception {
    final Post[] posts = new Post[]{
            new Post()
                    .path(".some.path.1")
                    .content("some content\n\non multiple lines\n\nfor post 1")
                    .created(1L)
                    .modified(2L),
            new Post()
                    .path(".some.path.2")
                    .content("some content\n\non multiple lines\n\nfor post 2")
                    .created(3L)
                    .modified(4L),
            new Post()
                    .path(".some.path.3")
                    .content("some content\n\non multiple lines\n\nfor post 3")
                    .created(5L)
                    .modified(6L),
    };
    for (final Post post : posts) {
      assertEquals(post, postFromJson(post("/posts", postToJson(post))));
    }

    assertArrayEquals(posts, postsFromJson(get("/posts")));
    for (final Post post : posts) {
      assertEquals(post, postFromJson(get(String.format("/posts/%s", post.getPath()))));
    }
  }

  @Test
  public void updatePost() throws Exception {
    final Post[] posts = new Post[]{
            new Post()
                    .path(".some.path.1")
                    .content("some content\n\non multiple lines\n\nfor post 1")
                    .created(1L)
                    .modified(2L),
            new Post()
                    .path(".some.path.2")
                    .content("some content\n\non multiple lines\n\nfor post 2")
                    .created(3L)
                    .modified(4L),
            new Post()
                    .path(".some.path.3")
                    .content("some content\n\non multiple lines\n\nfor post 3")
                    .created(5L)
                    .modified(6L),
    };
    for (final Post post : posts) {
      assertEquals(post, postFromJson(post("/posts", postToJson(post))));
    }

    for (final Post post : posts) {
      post.content("some new content!");
      assertEquals(post, postFromJson(put(String.format("/posts/%s", post.getPath()), postToJson(post))));
    }

    assertArrayEquals(posts, postsFromJson(get("/posts")));
    for (final Post post : posts) {
      assertEquals(post, postFromJson(get(String.format("/posts/%s", post.getPath()))));
    }
  }

  @Test
  public void deletePost() throws Exception {
    final Post[] posts = new Post[]{
            new Post()
                    .path(".some.path.1")
                    .content("some content\n\non multiple lines\n\nfor post 1")
                    .created(1L)
                    .modified(2L),
            new Post()
                    .path(".some.path.2")
                    .content("some content\n\non multiple lines\n\nfor post 2")
                    .created(3L)
                    .modified(4L),
            new Post()
                    .path(".some.path.3")
                    .content("some content\n\non multiple lines\n\nfor post 3")
                    .created(5L)
                    .modified(6L),
    };
    for (final Post post : posts) {
      assertEquals(post, postFromJson(post("/posts", postToJson(post))));
    }

    assertEquals(posts[0], postFromJson(delete("/posts/.some.path.1")));
    assertEquals(posts[1], postFromJson(delete("/posts/.some.path.2")));

    assertArrayEquals(new Post[]{posts[2]}, postsFromJson(get("/posts")));
    assertEquals(posts[2], postFromJson(get("/posts/.some.path.3")));
  }

  @Test
  public void getPostsByPrefix() throws Exception {
    final Post[] posts = new Post[]{
            new Post()
                    .path(".some.path.9")
                    .content("some content\n\non multiple lines\n\nfor post 1")
                    .created(1L)
                    .modified(2L),
            new Post()
                    .path(".some.path.1")
                    .content("some content\n\non multiple lines\n\nfor post 1")
                    .created(1L)
                    .modified(2L),
            new Post()
                    .path(".some.other.path.2")
                    .content("some content\n\non multiple lines\n\nfor post 2")
                    .created(3L)
                    .modified(4L),
            new Post()
                    .path(".some.path.3")
                    .content("some content\n\non multiple lines\n\nfor post 3")
                    .created(5L)
                    .modified(6L),
    };
    for (final Post post : posts) {
      assertEquals(post, postFromJson(post("/posts", postToJson(post))));
    }

    assertArrayEquals(
            new Post[]{posts[1], posts[3], posts[0]},
            postsFromJson(get("/posts?prefix=.some.path"))
    );
  }

  private String get(final String path) throws Exception {
    return mockMvc.perform(MockMvcRequestBuilders
            .get(path)
            .accept(MediaType.APPLICATION_JSON)
            .with(user("anblog_default_username").password("anblog_default_password"))
    )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();
  }

  private String put(final String path, final String content) throws Exception {
    return mockMvc.perform(MockMvcRequestBuilders
            .put(path)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .with(user("anblog_default_username").password("anblog_default_password"))
            .content(content)
    )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();
  }

  private String post(final String path, final String content) throws Exception {
    return mockMvc.perform(MockMvcRequestBuilders
            .post(path)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .with(user("anblog_default_username").password("anblog_default_password"))
            .content(content)
    )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();
  }

  private String delete(final String path) throws Exception {
    return mockMvc.perform(MockMvcRequestBuilders
            .delete(path)
            .accept(MediaType.APPLICATION_JSON)
            .with(user("anblog_default_username").password("anblog_default_password"))
    )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();
  }

  private String postToJson(final Post post) throws Exception {
    return new ObjectMapper().writeValueAsString(post);
  }

  private Post postFromJson(final String string) throws Exception {
    return new ObjectMapper().readValue(string, Post.class);
  }

  private Post[] postsFromJson(final String string) throws Exception {
    return new ObjectMapper().readValue(string, Post[].class);
  }
}
