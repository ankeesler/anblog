package com.marshmallow.anblog;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
public class PostEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;
  private String title;
  private String content;
  private OffsetDateTime created;
  private OffsetDateTime modified;
//  private PostEntity parent;
//  private List<PostEntity> children;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public OffsetDateTime getCreated() {
    return created;
  }

  public void setCreated(OffsetDateTime created) {
    this.created = created;
  }

  public OffsetDateTime getModified() {
    return modified;
  }

  public void setModified(OffsetDateTime modified) {
    this.modified = modified;
  }

//  public PostEntity getParent() {
//    return parent;
//  }
//
//  public void setParent(PostEntity parent) {
//    this.parent = parent;
//  }
//
//  public List<PostEntity> getChildren() {
//    return children;
//  }
//
//  public void setChildren(List<PostEntity> children) {
//    this.children = children;
//  }
}
