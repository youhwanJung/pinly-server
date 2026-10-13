package io.github.youhwanjung.pinly.couple

/**
 * 커플 연결의 진행 단계
 */
enum class CoupleStatus {
    WAITING, // 한명만 들어옴, 초대 코드 발급된 상태
    CONNECTED, // 두 명 연결 완료
    DISCONNECTED, // 헤어져서 해제됨
}