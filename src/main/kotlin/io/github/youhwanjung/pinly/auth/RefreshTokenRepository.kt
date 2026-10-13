package io.github.youhwanjung.pinly.auth

import io.github.youhwanjung.pinly.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface RefreshTokenRepository : JpaRepository<RefreshToken, Long> {

    //RefreshToken 테이블에서 token값이 같은애의 RefreshToken 객체를 돌려주라
    fun findByToken(token: String): RefreshToken?

    //RefreshToken 테이블에서 User(user_id)가 같은애의 RefreshToken 객체로 돌려주라
    fun findByUser(user: User): RefreshToken?
}