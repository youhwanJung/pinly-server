package io.github.youhwanjung.pinly.pin

import org.springframework.data.jpa.repository.JpaRepository

/**
 * 레포지토리가 없으면, pin 테이블에서 전체 목록을 꺼내기 위해 많은 코드를 써야한다.
 * DB에 접속하고, SQL을 보내고, 결과를 한 줄 씩 읽어서 Pin객체로 바꾸고, 마지막에 연결을 닫는 행위
 *
 * 하지만 Spring이 제공하는 JpaRepository interface를 상속해서 PinRepository를 선언하면,
 *  Spring이 서버를 시작할때 이 interface를 읽고 작업을 실행할 클래스를 직접 생성하게 된다.
 *
 * 실제 일하는 코드는 Spring이 만들어 넣는다.
 * JpaRepository에는 함수 목록만 있고, 실제 동작하는 코드는 SimpleJpaRepository 클래스에 있다.
 * <Pin, Long>은 Pin이라는 엔티티를 다루며 Long은 그 PK 타입을 의미한다.
 */
interface PinRepository : JpaRepository<Pin, Long>