package io.github.youhwanjung.pinly.auth

import io.github.youhwanjung.pinly.common.BaseEntity
import io.github.youhwanjung.pinly.user.User
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

//리프래쉬 토큰을 저장하는 테이블
@Entity
@Table(name = "refresh_token")
class RefreshToken(

    @Column(nullable = false, unique = true, length = 100)  // 리프래쉬 토큰
    var token : String,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true) // 유저랑 1ㄷ1로 매칭
    val user: User,

    @Column(name = "expires_at", nullable = false) // 만료시간 저장
    var expiresAt: LocalDateTime,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //PK
    var id: Long? = null
) : BaseEntity() {

    // 리프래쉬 토큰 만료 확인 함수
    // 저장되어있는 만료시간이 현제 시간보다. 이전인가?
    fun isExpired(): Boolean = expiresAt.isBefore(LocalDateTime.now())
}