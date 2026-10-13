package io.github.youhwanjung.pinly.couple

import org.springframework.data.jpa.repository.JpaRepository

interface CoupleRepository : JpaRepository<Couple, Long> {
    // Couple이라는 테이블에서, inviteCode가 같은 Couple을 한개 가져와라
    fun findByInviteCode(inviteCode: String): Couple?
    // Couple이라는 테이블에서 해당 inviteCode가 존재하는지 확인하라
    fun existsByInviteCode(inviteCode: String): Boolean
}