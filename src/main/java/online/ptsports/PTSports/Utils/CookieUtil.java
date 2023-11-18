package online.ptsports.PTSports.Utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookieUtil {
    public static void clearTokenCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) { // Thay "token" bằng tên của cookie chứa token
                    cookie.setValue("");
                    cookie.setPath("/");
                    cookie.setMaxAge(0);

                    break;
                }
            }
        }
    }
}
