package hello.login.web.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();

        String uuid = UUID.randomUUID().toString();

        request.setAttribute("uuid", uuid);

        //@RequestMapping -> HandlerMethod 사용
        //정적 리소소 -> ResourceHttpRequestHandler
        if (handler instanceof HandlerMethod) {
            //호출할 컨트롤러의 모든 메서드가 포함되어있다.
            HandlerMethod hm = (HandlerMethod) handler;
        }

        log.info("preHandel REQUEST [{}][{}][{}]", uuid, requestURI, handler);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle [{}]", modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        String requestURI = request.getRequestURI();

        String uuid = (String) request.getAttribute("uuid");

        log.info("RESPONSE [{}][{}]", uuid, requestURI);

        if (ex != null) {
            log.error("afterCompltion error!!", ex);
        }
    }
}
