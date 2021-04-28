package pl.akai.bookcrossing.login;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService oAuth2UserService;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private static final String MAIN_PAGE = "/";
    private static final String LOGOUT_PAGE = "/logout";
    private static final String LOGIN_PAGE = "/login";
    private static final String OAUTH2_PAGE = "/oauth2/**";
    private static final String[] ALLOWED_RESOURCES = new String[]{
            "/img/google-button.png",
            "/img/google-button-hover.png",
            "/img/google-button-focus.png",
            "/img/favicon.svg",
            "/styles/login.css",
            "/webjars/**"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(LOGIN_PAGE).permitAll()
                .antMatchers(OAUTH2_PAGE).permitAll()
                .antMatchers(ALLOWED_RESOURCES).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage(LOGIN_PAGE)
                .permitAll()
                .defaultSuccessUrl(MAIN_PAGE)
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher(LOGOUT_PAGE))
                .logoutSuccessUrl(LOGIN_PAGE)
                .and()
                .oauth2Login()
                .loginPage(LOGIN_PAGE)
                .userInfoEndpoint().userService(oAuth2UserService)
                .and()
                .successHandler(oAuth2LoginSuccessHandler);
    }
}
