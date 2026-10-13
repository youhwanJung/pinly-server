package io.github.youhwanjung.pinly.common.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val status: HttpStatus,
    val message: String
) {
    // 인증
    TOKEN_MISSING(HttpStatus.UNAUTHORIZED, "토큰이 없습니다."),
    TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "토큰이 유효하지 않습니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    REFRESH_TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "유효하지 않은 refresh 토큰입니다."),

    // 유저
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다."),

    // 커플
    COUPLE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 초대 코드입니다."),
    COUPLE_ALREADY_FULL(HttpStatus.CONFLICT, "이미 두 명이 연결된 커플입니다."),
    COUPLE_SELF_JOIN(HttpStatus.BAD_REQUEST, "자신의 초대 코드는 입력할 수 없습니다."),

    // 공통
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력값이 올바르지 않습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "요청한 경로를 찾을 수 없습니다."),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다.")
}