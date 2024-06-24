package com.sparta.outsourcing.dto;

import com.sparta.outsourcing.entity.ContentType;
import lombok.Getter;

@Getter
public class LikeResponseDto {
    private final boolean Liked;
    private final Long cnt;

    public LikeResponseDto(boolean Liked, Long cnt) {
        this.Liked = Liked;
        this.cnt = cnt;
    }
}
