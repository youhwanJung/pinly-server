package io.github.youhwanjung.pinly.common.response

import com.fasterxml.jackson.annotation.JsonInclude
import io.github.youhwanjung.pinly.common.exception.ErrorCode
import java.time.LocalDateTime

data class ApiResponse<T>(
    val success: Boolean,
    val code: String,
    val message: String,
    val data: T?,

    @field:JsonInclude(JsonInclude.Include.NON_NULL)
    val timestamp: LocalDateTime? = null,

    @field:JsonInclude(JsonInclude.Include.NON_NULL)
    val path: String? = null
) {
    companion object {

        fun <T> success(data: T): ApiResponse<T> = ApiResponse(
            success = true,
            code = "SUCCESS",
            message = "요청이 성공했습니다.",
            data = data
        )

        /** 돌려줄 데이터가 없는 성공 (삭제, 로그아웃 등) */
        fun success(): ApiResponse<Any?> = success(null)

        fun fail(errorCode: ErrorCode, message: String, path: String): ApiResponse<Any?> =
            ApiResponse(
                success = false,
                code = errorCode.name, // enum 이름이 그대로 코드가 된다
                message = message,
                data = null,
                timestamp = LocalDateTime.now(),
                path = path
            )
    }
}