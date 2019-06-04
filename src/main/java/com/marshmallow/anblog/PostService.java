package com.marshmallow.anblog;

import org.openapitools.api.PostsApiDelegate;
import org.openapitools.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService implements PostsApiDelegate {

  @Autowired
  private PostRepository postRepository;

  @Override
  public ResponseEntity<List<Post>> getAllPosts() {
    final List<Post> posts = new ArrayList<Post>();
    for (final PostEntity entity : postRepository.findAll()) {
      posts.add(entityToPost(entity));
    }
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
      postRepository.deleteById(path);
      return new ResponseEntity<>(entityToPost(entity.get()), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @Override
  public ResponseEntity<Post> getPostByPath(final String path) {
    final Optional<PostEntity> entity = postRepository.findById(path);
    if (entity.isPresent()) {
      return new ResponseEntity<>(entityToPost(entity.get()), HttpStatus.OK);
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

  private static Post entityToPost(final PostEntity entity) {
    return new Post()
            .path(entity.getPath())
            .content(entity.getContent())
            .created(entity.getCreated())
            .modified(entity.getModified());
  }

  private static PostEntity postToEntity(final Post post) {
    final PostEntity entity = new PostEntity();
    entity.setPath(post.getPath());
    entity.setContent(post.getContent());
    entity.setCreated(post.getCreated());
    entity.setModified(post.getModified());
    return entity;
  }
}
