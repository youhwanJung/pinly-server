package io.github.youhwanjung.pinly.common

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass // 나는 테이블이 아니라. 내 필드를 자식에게 물려주는 부모예요
@EntityListeners(AuditingEntityListener::class)
//엔티티가 저장/수정될 때 이벤트를 감지하는 리스너를 붙이는 코드.
//이렇게 하면, 이 리스너가 아래 날짜 값을 자동으로 넣어줌.
//어차피 상속용으로만 쓰일거니깐 추상클래스로 만든것
abstract  class BaseEntity {
    @CreatedDate //처음 (INERT)될 때 현재 시간을 자동으로 넣어준다.
    @Column(name= "created_at", nullable = false, updatable = false)
    lateinit var createdAt: LocalDateTime
    // lateinit var 이 타입은 지금은 값이 없지만 나중에 반드시 채워질거라는 뜻

    @LastModifiedDate // 수정 (UPDATE) 될 때마다 시간을 자동으로 갱신한다.
    @Column(name="updated_at", nullable = false)
    lateinit var updatedAt: LocalDateTime
}