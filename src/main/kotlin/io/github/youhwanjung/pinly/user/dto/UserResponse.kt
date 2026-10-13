package io.github.youhwanjung.pinly.user.dto

import io.github.youhwanjung.pinly.couple.CoupleStatus
import io.github.youhwanjung.pinly.user.OnboardingStatus
import io.github.youhwanjung.pinly.user.User
import java.time.LocalDate

data class UserResponse (
    val id: Long,
    val nickname: String?,
    val birthday: LocalDate?,
    val onboardingStatus: OnboardingStatus,
    val couple: CoupleInfo?
) {
    data class CoupleInfo(
        val startedAt: LocalDate?,
        val partnerNickname: String?,
        val inviteCode: String?,
    )
    // companion object는 객체를 생성하지 않고 사용할 수 있게 함수이다.
    // 이렇게 쓰면, UserResponse객체를 일일히 만드는게 아니라 필요한 애들만 넣어서 UserResponse 객체를 생성할 수 있다.
    companion object {
        //User(엔티티)를 받아서 UserResponse(응답용 DTO)로 바꿔주는 함수.
        fun of(user: User, partner: User?): UserResponse = UserResponse(
            id = user.id!!,
            nickname = user.nickname,
            birthday = user.birthday,
            onboardingStatus =  user.onboardingStatus,
            couple = user.couple?.let { couple ->
                CoupleInfo(
                    startedAt = couple.startedAt,
                    partnerNickname = partner?.nickname,
                    inviteCode = if (couple.status == CoupleStatus.WAITING)
                    couple.inviteCode else null,
                )
            }
        )
    }
}