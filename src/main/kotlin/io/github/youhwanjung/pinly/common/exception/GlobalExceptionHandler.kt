package io.github.youhwanjung.pinly.common.exception

import io.github.youhwanjung.pinly.common.response.ApiResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.servlet.resource.NoResourceFoundException

@RestControllerAdvice
class GlobalExceptionHandler {
    companion object {
        private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)
    }

    /** 우리가 의도적으로 던진 예외 */
    @ExceptionHandler(BusinessException::class)
    fun handleBusiness(
        e: BusinessException,
        request: HttpServletRequest
    ): ResponseEntity<ApiResponse<Any?>> {
        logger.warn("비즈니스 예외: {} - {}", e.errorCode.name, e.message)
        return ResponseEntity
            .status(e.errorCode.status)
            .body(ApiResponse.fail(e.errorCode, e.message, request.requestURI))
    }

    /** 없는 주소로 요청이 온 경우. 이걸 안 두면 아래 Exception 에 걸려 500 이 된다 */
    @ExceptionHandler(NoResourceFoundException::class)
    fun handleNoResource(
        e: NoResourceFoundException,
        request: HttpServletRequest
    ): ResponseEntity<ApiResponse<Any?>> = ResponseEntity
        .status(ErrorCode.NOT_FOUND.status)
        .body(ApiResponse.fail(ErrorCode.NOT_FOUND, ErrorCode.NOT_FOUND.message, request.requestURI))

    /** 아직 BusinessException 으로 안 바꾼 곳에서 올라오는 예외 */
    @ExceptionHandler(ResponseStatusException::class)
    fun handleResponseStatus(
        e: ResponseStatusException,
        request: HttpServletRequest
    ): ResponseEntity<ApiResponse<Any?>> {
        val code = if (e.statusCode.value() == 401) ErrorCode.TOKEN_INVALID else ErrorCode.INVALID_INPUT
        return ResponseEntity
            .status(e.statusCode)
            .body(ApiResponse.fail(code, e.reason ?: code.message, request.requestURI))
    }

    /** 예상 못 한 모든 오류. 내부 사정을 밖으로 보내지 않고 로그에만 남긴다 */
    @ExceptionHandler(Exception::class)
    fun handleUnexpected(
        e: Exception,
        request: HttpServletRequest
    ): ResponseEntity<ApiResponse<Any?>> {
        logger.error("예상치 못한 오류", e)
        return ResponseEntity
            .status(ErrorCode.INTERNAL_ERROR.status)
            .body(ApiResponse.fail(ErrorCode.INTERNAL_ERROR, ErrorCode.INTERNAL_ERROR.message, request.requestURI))
    }
}