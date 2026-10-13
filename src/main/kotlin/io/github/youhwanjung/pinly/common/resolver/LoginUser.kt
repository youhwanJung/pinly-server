package io.github.youhwanjung.pinly.common.resolver

/**
 * 일단 이 파일은 사용자화 어노테이션을 만드는 파일이다.
 * @Target은 이 어노테이션을 코드의 어느 위치에 붙일 수 있는지 정한다.
 * 종류로는 클래스, 함수, 필드, 함수 파라미터가 있고 우리가 쓰는건 함수 파라미터에 붙일 수 있는 어노테이션이다.
 * @Retention은 언제까지 이 어노테이션이 남아있는지를 설정한다.
 * 종류로는 코드 작성할 때만, 컴파일이된 파일까지, 서버 실행도중에고 우리가 설정한 값은 서버 실행 중에이다.
 *
 * API 마다 토큰을 꺼내서 getUserId() 함수로 검증하는 코드를 반복해서 쓰면 귀찮기 때문에,
 * @LoginUser 를 붙이면 로그인한 유저 id가 자동으로 들어오게 한다.
 */
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class LoginUser
