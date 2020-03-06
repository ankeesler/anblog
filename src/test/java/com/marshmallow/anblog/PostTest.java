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

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
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
                        .modified(2L)
                        .putLabelsItem("tuna", "fish")
                        .putLabelsItem("marlin", "shark"),
                new Post()
                        .path(".some.path.2")
                        .content("some content\n\non multiple lines\n\nfor post 2")
                        .created(3L)
                        .modified(4L)
                        .putLabelsItem("treason", "doughnut"),
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
    public void getPostFields() throws Exception {
        final Post[] posts = new Post[]{
                new Post()
                        .path(".some.path.1")
                        .content("some content\n\non multiple lines\n\nfor post 1")
                        .created(1L)
                        .modified(2L)
                        .putLabelsItem("tuna", "fish")
                        .putLabelsItem("marlin", "shark")
        };
        for (final Post post : posts) {
            assertEquals(post, postFromJson(post("/posts", postToJson(post))));
        }

        final Post[] partialPosts = postsFromJson(get("/posts?$fields=path,created,modified,labels"));
        for (final Post partialPost : partialPosts) {
            assertEquals(".some.path.1", partialPost.getPath());
            assertNull(partialPost.getContent());
            assertEquals(Long.valueOf(1L), partialPost.getCreated());
            assertEquals(Long.valueOf(2L), partialPost.getModified());

            final Map<String, String> labels = new HashMap<>();
            labels.put("tuna", "fish");
            labels.put("marlin", "shark");
            assertEquals(labels, partialPost.getLabels());
        }
    }

    @Test
    public void updatePost() throws Exception {
        final Post[] posts = new Post[]{
                new Post()
                        .path(".some.path.1")
                        .content("some content\n\non multiple lines\n\nfor post 1")
                        .created(1L)
                        .modified(2L)
                        .putLabelsItem("broccoli", "treason")
                        .putLabelsItem("tuna", "fish")
                        .putLabelsItem("marlin", "shark"),
                new Post()
                        .path(".some.path.2")
                        .content("some content\n\non multiple lines\n\nfor post 2")
                        .created(3L)
                        .modified(4L),
                new Post()
                        .path(".some.path.3")
                        .content("some content\n\non multiple lines\n\nfor post 3")
                        .created(5L)
                        .modified(6L)
                        .putLabelsItem("um", "idk"),
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
    public void patchPost() throws Exception {
        final Post post = new Post()
                .path(".some.path")
                .content("some content")
                .created(1L)
                .modified(2L)
                .putLabelsItem("foo", "bar");
        assertEquals(post, postFromJson(post("/posts", postToJson(post))));

        final String partialPost = "{\"content\": \"some new content\", \"modified\": 3}";
        final Post newPost = new Post()
                .path(".some.path")
                .content("some new content")
                .created(1L)
                .modified(3L)
                .putLabelsItem("foo", "bar");
        assertEquals(postToJson(newPost), patch("/posts/.some.path", partialPost));
    }

    @Test
    public void deletePost() throws Exception {
        final Post[] posts = new Post[]{
                new Post()
                        .path(".some.path.1")
                        .content("some content\n\non multiple lines\n\nfor post 1")
                        .created(1L)
                        .modified(2L)
                        .labels(new HashMap<>()),
                new Post()
                        .path(".some.path.2")
                        .content("some content\n\non multiple lines\n\nfor post 2")
                        .created(3L)
                        .modified(4L)
                        .labels(new HashMap<>()),
                new Post()
                        .path(".some.path.3")
                        .content("some content\n\non multiple lines\n\nfor post 3")
                        .created(5L)
                        .modified(6L)
                        .labels(new HashMap<>()),
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

    @Test
    public void getPostsByLabels() throws Exception {
        final Post[] posts = new Post[]{
                new Post()
                        .path(".some.path.9")
                        .content("some content\n\non multiple lines\n\nfor post 1")
                        .created(1L)
                        .modified(2L)
                        .putLabelsItem("type", "memoir")
                        .putLabelsItem("feeling", "sad"),
                new Post()
                        .path(".some.path.1")
                        .content("some content\n\non multiple lines\n\nfor post 1")
                        .created(1L)
                        .modified(2L)
                        .putLabelsItem("type", "memoir")
                        .putLabelsItem("feeling", "happy"),
                new Post()
                        .path(".some.other.path.2")
                        .content("some content\n\non multiple lines\n\nfor post 2")
                        .created(3L)
                        .modified(4L)
                        .putLabelsItem("type", "soliloquy")
                        .putLabelsItem("feeling", "sad"),
                new Post()
                        .path(".some.path.3")
                        .content("some content\n\non multiple lines\n\nfor post 3")
                        .created(5L)
                        .modified(6L)
                        .putLabelsItem("type", "soliloquy")
                        .putLabelsItem("feeling", "happy"),
        };
        for (final Post post : posts) {
            assertEquals(post, postFromJson(post("/posts", postToJson(post))));
        }

        assertArrayEquals(
                new Post[]{posts[1], posts[0]},
                postsFromJson(get("/posts?labels=type=memoir"))
        );
        assertArrayEquals(
                new Post[]{posts[1], posts[3]},
                postsFromJson(get("/posts?labels=feeling=happy"))
        );
        assertArrayEquals(
                new Post[]{posts[3]},
                postsFromJson(get("/posts?labels=type=soliloquy,feeling=happy"))
        );
        assertArrayEquals(
                new Post[]{},
                postsFromJson(get("/posts?labels=type=soliloquy,doesnt=exist"))
        );
    }

    @Test
    public void getPostsByRegex() throws Exception {
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
                new Post[]{posts[2], posts[3]},
                postsFromJson(get("/posts?content=.*[23]$"))
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

    private String patch(final String path, final String content) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders
                .patch(path)
                .contentType("application/merge-patch+json")
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
