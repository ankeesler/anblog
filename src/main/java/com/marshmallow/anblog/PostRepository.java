package com.marshmallow.anblog;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<PostEntity, String> {
    @Query(
            value = "SELECT * FROM post_entity WHERE path LIKE ?1%",
            nativeQuery = true
    )
    public Iterable<PostEntity> findByPathPrefix(final String prefix);
}
