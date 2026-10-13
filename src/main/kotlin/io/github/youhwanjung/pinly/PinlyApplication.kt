package io.github.youhwanjung.pinly

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
// 이 친구가 있어야지 자동으로 주입되는 값들이 주입을 할 수 있음
// 일종에 그런 행위를 할 수 있게 켜주는 스위치 같은 거
class PinlyApplication

fun main(args: Array<String>) {
	runApplication<PinlyApplication>(*args)
}

