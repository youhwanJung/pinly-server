package io.github.youhwanjung.pinly.user

import io.github.youhwanjung.pinly.common.BaseEntity
import io.github.youhwanjung.pinly.couple.Couple
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import java.time.LocalDate

@Entity
@Table(
    name = "users" ,
    // uniqueConstraints : 이 테이블에 제약조건을 하나 정의하겠다라는 의미
    // 제약 조건의 이름은 uk_users_provider 이며
    // 프로바이더와 프로바이더 고유 아이디가 한 유저당 2개를 만들 수 없게 함.
    // 즉 카카오로 2개의 아이디 생성을 막는 제약조건
    uniqueConstraints = [
        UniqueConstraint(
            name = "uk_users_provider",
            columnNames = ["provider", "provider_id"]
        )
    ]
)
class User (
    //@Enumerated는 DB에 Enum값을 정의해둔 문자열 그대로 저장한다는 뜻
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    val provider: Provider,

    @Column(name = "provider_id", nullable = false, length = 100)
    val providerId: String,

    @Column(length = 30)
    var nickname: String? = null,

    @Column(name = "birthday")
    var birthday: LocalDate? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "onboarding_status", nullable = false, length = 30)
    var onboardingStatus: OnboardingStatus = OnboardingStatus.PROFILE_REQUIRED,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    var status: UserStatus = UserStatus.ACTIVE,

    // ManyToOne은 다대일 관계라는 뜻 즉 커플이라는 엔티티에 유저가 2명 있으니 다대일 관계
    // couple_id 라는 PK 값을 외래키 컬럼으로 설정하여 couple_id를 통해 두 유저를 묶음
    // fetch = FetchType.LAZY는 지연 로딩으로 User를 조회할때 Couple 정보를 바로 가져오는 것이 아니라 실제로 쓸 때 가져오라는 뜻
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "couple_id")
    var couple: Couple? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
) : BaseEntity()
