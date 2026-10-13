package io.github.youhwanjung.pinly.common.config

import io.github.youhwanjung.pinly.common.resolver.LoginUserArgumentResolver
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * @Configuration 이라는 어노테이션은 Spring설정 파일이라는 표시이다.
 * WebMvcConfigurer 는 Spring MVC의 설정을 바꿀 수 있게 해주는 인터페이스이다.
 *
 * 여기에 있는 함수를 override하면 Spring의 기본 동작에 우리 설정을 끼워 넣을 수 있다.
 * 즉 우리는 우리가 정의해놓은 LoginUser 어노테이션의 해결사 (Resolver)를 등록한다.
 */
@Configuration
class WebMvcConfig (
    private val loginUserArgumentResolver: LoginUserArgumentResolver
) : WebMvcConfigurer {
    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(loginUserArgumentResolver)
    }
}