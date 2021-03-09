package pl.akai.bookcrossing.login;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import pl.akai.bookcrossing.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {


    private final UserDaoMapper userDao;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        User user = userDao.getUserByEmail(oAuth2User.getEmail());

        if (user == null) {
            user = User.builder()
                    .fullName(oAuth2User.getName())
                    .email(oAuth2User.getEmail())
                    .build();
            userDao.insertUser(user);
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
