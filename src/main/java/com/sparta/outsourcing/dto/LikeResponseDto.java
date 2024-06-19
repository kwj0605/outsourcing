package com.sparta.outsourcing.dto;

import com.sparta.outsourcing.entity.ContentType;
import lombok.Getter;

@Getter
public class LikeResponseDto {
    private final Long likecnt;

    public LikeResponseDto(Long likecnt) {
        this.likecnt = likecnt;
    }
}
