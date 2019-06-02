package com.marshmallow.anblog;

import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<PostEntity, String> {
}
