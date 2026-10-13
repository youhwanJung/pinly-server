package io.github.youhwanjung.pinly.auth

import io.github.youhwanjung.pinly.auth.dto.TokenResponse
import io.github.youhwanjung.pinly.common.exception.BusinessException
import io.github.youhwanjung.pinly.common.exception.ErrorCode
import io.github.youhwanjung.pinly.user.User
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Duration
import java.time.LocalDateTime
import java.util.UUID

@Service
class TokenService (
    private val jwtProvider: JwtProvider,
    private val refreshTokenRepository: RefreshTokenRepository,
    @Value("\${jwt.refresh-expiration-ms}")
    private val refreshExpirationMs: Long
) {
    // @Transeactinal 은 All or Nothing이라는 뜻. 즉 이 어노테이션이 있는 함수가
    // 하나로 실패하면 롤백, 전부 다 통과하면 DB를 업데이트를 자동으로 쳐주고 커밋해줌.
    /**
     * issue란 발급하다라는 뜻 새로운 refresh 토큰의 값과 만료시간을 설정한다.
     * 만약 유저가 있으면 새로 생성한 refresh 토큰의 값과 만료시간으로 덮어씌어 refresh 테이블을 업데이트 쳐준다.
     * 만약 유저가 없으면 생성한 refresh 토큰의 값과 만료시간을 refresh 테이블에 값안에 넣어준다.
     * 그리고 accessToken과, jwtProvider로 accessToken을 만들어준후에 TokenResponse객체로 반환한다.
     **/
    @Transactional
    fun issue(user: User): TokenResponse {
        val newToken = UUID.randomUUID().toString()
        val expiresAt = LocalDateTime.now().plus(Duration.ofMillis(refreshExpirationMs))
        val existing = refreshTokenRepository.findByUser(user)

        if(existing != null) {
            existing.token = newToken
            existing.expiresAt = expiresAt
        } else {
            refreshTokenRepository.save(
                RefreshToken(token = newToken, user = user, expiresAt = expiresAt)
            )
        }
        return TokenResponse(
            accessToken = jwtProvider.createAccessToken(user.id!!),
            refreshToken = newToken
        )
    }


    /**
     * reissue는 재발급하다라는 뜻
     * 우선 refreshToken으로 refreshToken 테이블에 저장된 refreshToken이 있는지 확인 없으면 유효하지않은 refresh 토큰 오류
     * 만약에 있는데 만료시간이 지났으면 해당 RefreshToken행을 삭제하고 만료 refresh 토큰이라고 오류
     * 만료가 안됐으면, 새로 refresh 테이블을 업데이트하고 TokenResponse객체 반환
     */
    @Transactional
    fun reissue(refreshToken: String): TokenResponse {
        val saved = refreshTokenRepository.findByToken(refreshToken)
            ?: throw BusinessException(ErrorCode.REFRESH_TOKEN_INVALID)
        if (saved.isExpired()) {
            refreshTokenRepository.delete(saved)
            throw BusinessException(ErrorCode.TOKEN_EXPIRED)
        }
        return issue(saved.user)
    }

    /**
     * revoke는 패기라는 뜻
     * User를 받고, 유저랑 맞는 refreshToken 객체를 받음
     * 그리고 해당 refreshToken 객체를 삭제 즉 행을 삭제하는거겠지요?
     * ?. let은 let앞에 있는 값을 받아서 없으면 건너띄고 있으면 실행하라는 뜻
     */
    @Transactional
    fun revoke(user: User) {
        refreshTokenRepository.findByUser(user)
            ?.let { refreshTokenRepository.delete(it) }
    }
}