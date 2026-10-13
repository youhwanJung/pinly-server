package io.github.youhwanjung.pinly.common.resolver

import io.github.youhwanjung.pinly.auth.JwtProvider
import io.github.youhwanjung.pinly.common.exception.BusinessException
import io.github.youhwanjung.pinly.common.exception.ErrorCode
import org.springframework.core.MethodParameter
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import org.springframework.web.server.ResponseStatusException

/**
 * 로그인 유저 파라미터 해결사 : 즉 LoginUser라는 사용자 어노테이션을 보면 어떤 행동을 할지를 정의한 클래스이다.
 * HandlerMethodArgumentResolver 라는 인터페이스를 구현하기 때문에 override된 함수를 채워 넣어주면 된다.
 * (※ WebMvcConfigurer에 등록해야 실제로 동작한다.)
 *
 * 우선 Spring의 Controller함수의 파라미터 어노테이션에 대한 실행을 이해해야하는데
 * Controller함수의 파라미터에 붙는 어노테이션은 해결사(Resolver)가 존재한다.
 * [예를들면 RequestParam어노테이션도 RequestResolver가 존재한다.]
 *
 * Spring은 Controller함수의 파라미터를 하나하나씩 돌며 해결사에게 순서대로 물어본다.
 * 너가 해당 어노테이션을 처리하는 애인지 물어보고, 해당 어노테이션을 정의해놓은 사람이 나오면 멈춘다.
 *
 *
 * supportsParameter와 resolveArgument 함수이다.
 * 1. supportsParameter : 이 함수는 LoginUser라는 어노테이션 달린 애가 내 담당인지 확인한다.
 *    붙어있으면 true 안붙어있으면 false를 리턴한다.
 * 2. resolveArgument : 이 함수는 파라미터에 들어갈 값을 만들어서 돌려주는 함수이다.
 */
@Component
class LoginUserArgumentResolver (
    private val jwtProvider: JwtProvider
) : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean =
        parameter.hasParameterAnnotation(LoginUser::class.java)


    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Long {
        // 1. 요청에서 Authorization이라는 헤더를 꺼내어 변수에 담고 없으면 401에러를 내라.
        // 즉 사용자가 로그인을 안한 상태로 이칸을 아예 안적고 보내면 getHeader가 null을 보내준다.
        val header = webRequest.getHeader("Authorization")
            ?: throw BusinessException(ErrorCode.TOKEN_MISSING)

        // 2. header가 만약 Bearer로 시작하지 않으면 401 에러를 내라.
        // 우리 서버는 Bearer방식만 받는다고 설정을 해놓아서 다른 인증방식은 들어오지 못하게 막는다.
        if(!header.startsWith("Bearer ")) {
            throw BusinessException(ErrorCode.TOKEN_INVALID, "토큰 형식이 잘못되었습니다.")
        }

        // 3. Bearer를 떼고 남은 토큰만 따로 뺀 후에, jwtProvider 클래스에 getUserId 함수를 실행시켜. 유저의 pk 값을 알아내고 리턴한다.
        // 그럼 파라미터에 사용자의 pk 값이 들어가게 된다.
        val token = header.removePrefix("Bearer ")
        return jwtProvider.getUserId(token)
            ?: throw BusinessException(ErrorCode.TOKEN_INVALID)
    }
}