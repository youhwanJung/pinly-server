package io.github.youhwanjung.pinly.auth

import io.jsonwebtoken.Jwt
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.Date
import javax.crypto.SecretKey

@Component
class JwtProvider (
    @Value("\${jwt.secret}") secret: String, //@Value 어노테이션은 설정 파일 (application.yaml 에서 값을 꺼내와서 사용할 때 사용)
    @Value("\${jwt.access-expiration-ms}")
    private val accessExpirationMs: Long
) {
    // 비밀키 문자열을 실제 도장으로 만드는 과정 (문자열 -> 바이트 배열 -> HMAC-SHA 키)
    // 클래스가 만들어질 때 한번만 만들어지고 재사용함.
    private val key: SecretKey = Keys.hmacShaKeyFor(secret.toByteArray())

    // 토큰을 새로 만드는 함수.
    fun createAccessToken(userId: Long): String {
        val now = System.currentTimeMillis()
        return Jwts.builder()
            .subject(userId.toString()) //토큰 주인 = 유저 ID
            .issuedAt(Date(now)) // 발급 시간
            .expiration(Date(now + accessExpirationMs)) // 만료 시간
            .signWith(key) // 위조 방지 서명
            .compact() // 최종적으로 문자열을 만들어서 반환.
    }

    // 토큰을 받아서 진짜인지 검증하고, 안에 적힌 유저 id를 꺼내준다.
    fun getUserId(token: String): Long? =
        try {
            Jwts.parser() // 토큰 해석기 준비
                .verifyWith(key) // 토큰 해석기에 도장 등록
                .build() // 해석기 완성
                .parseSignedClaims(token) // 토큰 검증 + 해석
                .payload // 토큰 안의 내용 부분
                .subject // 거기서 subject
                .toLong() // 문자열을 숫자로 바꿈 (숫자만)
                //검증 성공하면 유저 id 반환
        } catch (e: JwtException) {
            null // 위조, 만료, 형식 오류
        } catch(e: IllegalArgumentException) {
            null // 토큰이 빈 문자열이거나 id가 숫자가 아닐때
        }

}