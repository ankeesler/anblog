package com.marshmallow.anblog;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openapitools.api.PostsApiDelegate;
import org.openapitools.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.json.*;
import java.util.*;

@Service
public class PostService implements PostsApiDelegate {

  @Autowired
  private PostRepository postRepository;

  @Override
  public ResponseEntity<List<Post>> getAllPosts(final String prefix, final List<String> fields) {
    // TODO: we should only be getting the fields that we care about from the repository!
    final List<Post> posts = new ArrayList<Post>();
    final Iterable<PostEntity> entities = (
            prefix == null
                    ? postRepository.findAll()
                    : postRepository.findByPathStartingWith(prefix));
    for (final PostEntity entity : entities) {
      posts.add(entityToPost(entity, fields));
    }
    posts.sort(Comparator.comparing(Post::getPath));
    return new ResponseEntity<>(posts, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<Post> addPost(final Post post) {
    postRepository.save(postToEntity(post));
    return new ResponseEntity<>(post, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<Post> deletePost(final String path) {
    final Optional<PostEntity> entity = postRepository.findById(path);
    if (entity.isPresent()) {
      final Post post = entityToPost(entity.get(), null);
      // I think we need to create a copy of this map since the map that we
      // get from the entity is lazily created, and if we try to create it
      // after the call to deleteById() below, then bad things happen.
      final Map<String, String> labels = post.getLabels();
      post.labels(labels == null ? new HashMap<>() : new HashMap<>(labels));
      final ResponseEntity<Post> response = new ResponseEntity<>(post, HttpStatus.OK);

      postRepository.deleteById(path);

      return response;
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @Override
  public ResponseEntity<Post> getPostByPath(final String path, final List<String> fields) {
    final Optional<PostEntity> entity = postRepository.findById(path);
    if (entity.isPresent()) {
      return new ResponseEntity<>(entityToPost(entity.get(), fields), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @Override
  public ResponseEntity<Post> updatePost(final String path, final Post post) {
    final Optional<PostEntity> entity = postRepository.findById(path);
    if (entity.isPresent()) {
      postRepository.save(postToEntity(post));
      return new ResponseEntity<>(post, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @Override
  public ResponseEntity<Post> patchPost(final String path, final Post post) {
    final Optional<PostEntity> entity = postRepository.findById(path);
    if (entity.isPresent()) {
      final ObjectMapper mapper = new ObjectMapper()
              .setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL)
              .findAndRegisterModules();
      final JsonValue target = mapper.convertValue(entity.get(), JsonValue.class);
      final JsonMergePatch patch = Json.createMergePatch(mapper.convertValue(post, JsonValue.class));
      final JsonValue patched = patch.apply(target);
      final Post patchedPost = mapper.convertValue(patched, Post.class);
      postRepository.save(postToEntity(patchedPost));
      return new ResponseEntity<>(patchedPost, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  private static Post entityToPost(final PostEntity entity, final List<String> fields) {
    final Post post = new Post();
    if (fields == null || fields.contains("path"))
      post.path(entity.getPath());
    if (fields == null || fields.contains("content"))
      post.content(entity.getContent());
    if (fields == null || fields.contains("created"))
      post.created(entity.getCreated());
    if (fields == null || fields.contains("modified"))
      post.modified(entity.getModified());
    if (fields == null || fields.contains("labels"))
      post.labels(entity.getLabels());
    return post;
  }

  private static PostEntity postToEntity(final Post post) {
    final PostEntity entity = new PostEntity();
    entity.setPath(post.getPath());
    entity.setContent(post.getContent());
    entity.setCreated(post.getCreated());
    entity.setModified(post.getModified());
    entity.setLabels(post.getLabels());
    return entity;
  }
}
