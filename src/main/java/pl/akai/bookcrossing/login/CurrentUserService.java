package pl.akai.bookcrossing.login;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.akai.bookcrossing.model.User;

@Service
@RequiredArgsConstructor
public class CurrentUserService {

    private final UserDaoMapper userDao;

    public User getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getEmail();
        return userDao.getUserByEmail(email);
    }
}
