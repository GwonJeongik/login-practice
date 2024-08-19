package hello.login.web.interceptor;

import hello.login.web.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();

        log.info("인증 체크 인터셉터 실행 {}", requestURI);

        HttpSession session = request.getSession(false);

        //세션이 없거나, 세션이 있어도 내부에 LOGIN_MEMBER의 key로 저정된 value가 없다면 미인증 사용자 요청
        if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
            log.info("미인증 사용자 요청");

            // 로그인 화면으로 redirect && 로그인 후에 요청했던 곳으로 돌아올 수 있게 파라미터로 현재 URI 넣어줌
            response.sendRedirect("/login?redirectURL=" + requestURI);
            return false;
        }

        log.info("인증 체크 인터셉터 종료 {}", requestURI);
        return true;
    }
}
