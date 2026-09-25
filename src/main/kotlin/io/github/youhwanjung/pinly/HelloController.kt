package io.github.youhwanjung.pinly

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

// 요청 처리기
@RestController
class HelloController {

	// /hello 주소를 연결
	@GetMapping("/hello")
	fun hello(): String = "Hello World"
}
