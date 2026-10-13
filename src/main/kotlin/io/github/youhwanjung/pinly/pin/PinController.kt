package io.github.youhwanjung.pinly.pin

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/pins")
class PinController (
    private val pinRepository: PinRepository
    // 여기서 아까 만들어놓은 pinRepository를 사용한다. (찾기, 수정하기, 삭제하기 등등의 행위를 할수 있는 객체)
    // 즉 쿼리를 쓰지 않고,
) {
    @GetMapping
    fun list(): List<PinResponse> =
        pinRepository.findAll().map { PinResponse.from(it) }
}