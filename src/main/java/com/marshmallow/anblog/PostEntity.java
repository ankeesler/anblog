package com.marshmallow.anblog;

import java.util.Map;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.MapKeyColumn;

@Entity
public class PostEntity {
  
  @Id
  @Column(name = "path", length = 100)
  private String path;
  @Lob
  private String content;
  private Long created;
  private Long modified;
  @ElementCollection
  @MapKeyColumn(length = 100)
  @Column(length = 100)
  private Map<String, String> labels;
  
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
  
  public Long getCreated() {
    return created;
  }
  
  public void setCreated(Long created) {
    this.created = created;
  }
  
  public Long getModified() {
    return modified;
  }
  
  public void setModified(Long modified) {
    this.modified = modified;
  }
  
  public Map<String, String> getLabels() {
    return labels;
  }
  
  public void setLabels(Map<String, String> labels) {
    this.labels = labels;
  }
}
