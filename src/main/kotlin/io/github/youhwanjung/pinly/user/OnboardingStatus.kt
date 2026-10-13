package io.github.youhwanjung.pinly.user

/**
 * 유저의 회원가입 진행 상태
 */
enum class OnboardingStatus {
    PROFILE_REQUIRED, // 카카오 인증만 끝남. <-> 닉네임,생일 등 추가 정보 입력 필요
    COUPLE_REQUIRED, // 프로필 입력 끝남 <-> 커플 연결 필요
    ACTIVE, // 연결 완료. 서비스 이용 가능
}