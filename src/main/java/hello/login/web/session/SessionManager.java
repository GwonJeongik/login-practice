package hello.login.web.session;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {

    public static final String SESSION_COOKIE_NAME = "mySessionId";

    private final Map<String, Object> sessionStore = new ConcurrentHashMap<>();


    /**
     * 세션 생성
     */
    public void createSession(Object value, HttpServletResponse response) {

        //세션 ID 생성하고, 값을 세션 저장소에 저장
        String sessionId = UUID.randomUUID().toString();
        sessionStore.put(sessionId, value);

        //세션ID를 말은 쿠키 생성
        Cookie cookie = new Cookie(SESSION_COOKIE_NAME, sessionId);

        //응답에 쿠키 보냄
        response.addCookie(cookie);
    }

    /**
     * 세션 조회
     */
    public Object getSession(HttpServletRequest request) {

        Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);

        if (sessionCookie == null) {
            return null;
        }

        // 찾은 쿠키에 저장한 토큰을 통해서 세션 저장소에 저장되어있는 객체를 반환 (value == uuid -> token)
        return sessionStore.get(sessionCookie.getValue());
    }

    /**
     * @return 찾고싶은 쿠키의 이름의 쿠키를 찾아서 반환 없으면 null
     */
    private Cookie findCookie(HttpServletRequest request, String cookieName) {

        if (request.getCookies() == null) {
            return null;
        }

        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(cookieName))
                .findFirst()
                .orElse(null);
    }

    /**
     * 세션 만료
     */
    public void expireSession(HttpServletRequest request) {

        //삭제할 쿠키를 찾아야함
        Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);

        //찾은 쿠키의 값이 비어있지 않으면, 세션 저장소에서 쿠키에 맵핑 되는 값을 삭제한다.
        //반환이 없기 때문에 if(sessionCookie == null)로 하게 되면 return null;로 반환값을 받아야 하는 건 별로잖아.
        //아닐 때는 그냥 넘어가고, 맞으면 삭제하고 로직 끝.
        if (sessionCookie != null) {
            sessionStore.remove(sessionCookie.getValue());
        }
    }
}
