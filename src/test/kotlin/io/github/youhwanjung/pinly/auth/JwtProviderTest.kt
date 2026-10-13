package io.github.youhwanjung.pinly.auth

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

/**
 * 서버도 DB도 띄우지 않는다. JwtProvider 를 직접 만들어서 쓴다.
 * @Value 를 생성자 파라미터에 붙인 덕분에 테스트에서는 값을 직접 넣을 수 있다.
 */
class JwtProviderTest {

    // 테스트용 키. HS256 은 32바이트 이상이어야 한다
    private val secret = "test-secret-key-for-unit-test-only-1234567890"

    private val provider = JwtProvider(secret = secret, accessExpirationMs = 60_000)

    @Test
    fun `발급한 토큰에서 userId 를 다시 꺼낼 수 있다`() {
        val token = provider.createAccessToken(3L)
        assertEquals(3L, provider.getUserId(token))
    }

    @Test
    fun `내용을 조작한 토큰은 거부된다`() {
        val token = provider.createAccessToken(3L)
        val tampered = token.dropLast(3) + "xxx" // 서명 일부를 망가뜨림
        assertNull(provider.getUserId(tampered))
    }

    @Test
    fun `이미 만료된 토큰은 거부된다`() {
        val expired = JwtProvider(secret = secret, accessExpirationMs = -1000) // 1초 전 만료
            .createAccessToken(3L)
        assertNull(provider.getUserId(expired))
    }
}