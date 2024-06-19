package com.sparta.outsourcing.repository;

import com.sparta.outsourcing.entity.ContentType;
import com.sparta.outsourcing.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByContentTypeAndContentId(ContentType contentType, Long contentId);
}
