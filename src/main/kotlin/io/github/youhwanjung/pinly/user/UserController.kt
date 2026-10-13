package io.github.youhwanjung.pinly.user

import io.github.youhwanjung.pinly.common.resolver.LoginUser
import io.github.youhwanjung.pinly.common.response.ApiResponse
import io.github.youhwanjung.pinly.user.dto.UserResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController (
    private val userService: UserService
) {
    @GetMapping("/me")
    fun getMe(@LoginUser userId: Long): ApiResponse<UserResponse> =
        ApiResponse.success(userService.getMe(userId))
}