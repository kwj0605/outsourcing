package com.sparta.outsourcing.repository;

import com.sparta.outsourcing.entity.Like;
import com.sparta.outsourcing.enums.ContentTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByContentTypeAndContentId(ContentTypeEnum contentType, Long contentId);
}
