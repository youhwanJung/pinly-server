package io.github.youhwanjung.pinly

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PinlyApplication

fun main(args: Array<String>) {
	runApplication<PinlyApplication>(*args)
}

