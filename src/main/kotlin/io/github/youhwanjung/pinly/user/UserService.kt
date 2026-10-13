package io.github.youhwanjung.pinly.user


import io.github.youhwanjung.pinly.common.exception.BusinessException
import io.github.youhwanjung.pinly.common.exception.ErrorCode
import io.github.youhwanjung.pinly.user.dto.UserResponse
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException

@Service
class UserService (
    private val userRepository: UserRepository
) {
    @Transactional(readOnly = true)
    fun getMe(userId: Long): UserResponse {
        val user = userRepository.findById(userId)
            .orElseThrow { BusinessException(ErrorCode.USER_NOT_FOUND) }
        val partner = user.couple?.let {
                userRepository.findByCoupleAndIdNot(it, userId)
        }

        return UserResponse.of(user, partner)
    }
}