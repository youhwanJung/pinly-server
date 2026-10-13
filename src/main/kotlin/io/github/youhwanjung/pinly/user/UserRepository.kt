package io.github.youhwanjung.pinly.user

import io.github.youhwanjung.pinly.couple.Couple
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    // User라는 테이블에서 provider enum 속성이 같고 providerId 속성이 같은 애를 한명 데려와라
    // null이면 해당 소셜로 가입한 유저가 없다는거
    fun findByProviderAndProviderId(provider: Provider, providerId: String): User?
    // User라는 테이블에서 나와 커플 속성이 같고, id가 나른 User를 찾아 데려와라
    // null 이면 커플 상태가 연결이 안된것
    fun findByCoupleAndIdNot(couple: Couple, id: Long): User?
    // User라는 테이블에서 커플 속성이 같은 유저가 몇명인지 세서 알려줘라
    fun countByCouple(couple: Couple): Long
}