package io.github.youhwanjung.pinly

/**
 * 이 클래스는, API 응답 (Json) 모양을 어떻게 잡고 보낼지를 결정한다.
 * 즉 DTO 다
 */
data class PinResponse(
    val id: Long,
    val title: String,
    val memo: String?
) {
    // companion object 가 없으면 객체 생성 코드를 써야하지만
    // 있으면 클래스 이름으로 바로 호출 가능하다.
    // from 함수를 만들었다 이 함수는, Pin 객체를 받아서 PinResponse로 리턴하는 함수다.
    companion object {
        fun from(pin: Pin) = PinResponse(
            id = pin.id!!,
            title = pin.title,
            memo = pin.memo
        )
    }
}