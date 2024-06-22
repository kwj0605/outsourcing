package com.sparta.outsourcing.exception;

public class LikeSelfException extends RuntimeException {
    public LikeSelfException() {
        super("본인이 작성한 게시물에 대해 좋아요를 등록할 수 없습니다.");
    }
}
