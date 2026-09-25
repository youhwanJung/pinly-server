package io.github.youhwanjung.pinly

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

/**
 * 이 클래스는 엔티티 클래스인데, 엔티티 클래스를 작성한 후에 서버를 재실행 하면, 테이블이 하나 추가된다
 * 엔티티 클래스는 반드시 테이블과 1:1이여야한다. 즉 이 클래스는 테이블의 내용과 동일하다.
 * CREATE TABLE 쿼리를 쓰지않고 테이블을 하나 만든다.
 *
 * 이게 가능한 이유는 application.yaml 에 설정 때문에 가능하다
 * # ddl-auto: update
 */

@Entity
@Table(name = "pin")
class Pin (

    @Column(nullable = false, length = 100)  //NOT NULL, VARCHAR(100)
    var title: String,

    @Column(columnDefinition = "TEXT")
    var memo: String? = null, // ? -> DB에서 NULL 허용

    @Id //기본키 (PK)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 번호는 DB가 자동으로 매김
    var id: Long? = null

)

/**
 * @Entity : 이 클래스를 테이블과 연결
 * @Id : 기본키 지정 (PK)
 * @GeneratedValue : 번호를 자동 증가 시킴 (PK는 겹치지 않는다.)
 * @Table : 테이블 이름 지정
 * @Column : 컬럼 조건 지정
 */