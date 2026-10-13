package io.github.youhwanjung.pinly.auth

import io.github.youhwanjung.pinly.auth.dto.TokenResponse
import io.github.youhwanjung.pinly.common.response.ApiResponse
import io.github.youhwanjung.pinly.user.Provider
import io.github.youhwanjung.pinly.user.User
import io.github.youhwanjung.pinly.user.UserRepository
import org.springframework.context.annotation.Profile
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

// @Profile 어노테이션을 붙이면 application.yaml 파일에 profile이 active: dev일때만 이 클래스가 만들어진다.
// @RestController는 요청을 받고 Json으로 응답하는 클래스라는 뜻
@Profile("dev")
@RestController
class DevAuthController (
    private val userRepository: UserRepository,
    private val tokenService: TokenService
) {
    /**
     * providerId로 유저가 있는지 확인하고 없으면 해당 providerID로 테이블안에 행을 하나 넣어라 그리고, 토큰을 발급하라
     * (참고로 지금은 KAKAO로 고정되어있음)
     */
    @Transactional
    @PostMapping("/auth/dev-login")
    fun devLogin(@RequestParam providerId: String) : ApiResponse<TokenResponse> {
        val user = userRepository.findByProviderAndProviderId(Provider.KAKAO, providerId)
            ?: userRepository.save(User(provider = Provider.KAKAO, providerId))

        return ApiResponse.success(tokenService.issue(user))
    }
}