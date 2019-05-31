package com.marshmallow.anblog;

import org.openapitools.api.PostsApiDelegate;
import org.openapitools.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostsService implements PostsApiDelegate {

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
  public ResponseEntity<Post> deletePost(final Long postId) {
    final Optional<PostEntity> entity = postRepository.findById(postId);
    if (entity.isPresent()) {
      postRepository.deleteById(postId);
      return new ResponseEntity<>(entityToPost(entity.get()), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @Override
  public ResponseEntity<Post> getPostById(Long postId) {
    final Optional<PostEntity> entity = postRepository.findById(postId);
    if (entity.isPresent()) {
      return new ResponseEntity<>(entityToPost(entity.get()), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @Override
  public ResponseEntity<Post> updatePost(Long postId, Post post) {
    final Optional<PostEntity> entity = postRepository.findById(postId);
    if (entity.isPresent()) {
      postRepository.save(entity.get());
      return new ResponseEntity<>(entityToPost(entity.get()), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  private static Post entityToPost(final PostEntity entity) {
    final Post post = new Post()
            .id(entity.getId())
            .title(entity.getTitle())
            .content(entity.getContent())
            .created(entity.getCreated())
            .modified(entity.getModified());
    return post;
  }

  private static PostEntity postToEntity(final Post post) {
    final PostEntity entity = new PostEntity();
    entity.setId(post.getId());
    entity.setTitle(post.getTitle());
    entity.setContent(post.getContent());
    entity.setCreated(post.getCreated());
    entity.setModified(post.getModified());
    return entity;
  }
}