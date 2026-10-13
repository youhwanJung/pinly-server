package io.github.youhwanjung.pinly.couple

import io.github.youhwanjung.pinly.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "couple")
class Couple (
    @Column(name = "invite_code", nullable = false, unique = true, length = 20)
    var inviteCode: String, // 상대방이 입력할 초대 코드

    @Column(name = "started_at")
    var startedAt: LocalDate? = null, // 사귄 날짜

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    var status: CoupleStatus = CoupleStatus.WAITING, // 커플의 상태

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null //PK

) : BaseEntity()