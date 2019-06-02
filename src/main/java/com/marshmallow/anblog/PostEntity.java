package com.marshmallow.anblog;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
public class PostEntity {

  @Id
  @Column(length = 100)
  private String path;
  @Lob
  private String content;
  private OffsetDateTime created;
  private OffsetDateTime modified;

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
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
}
