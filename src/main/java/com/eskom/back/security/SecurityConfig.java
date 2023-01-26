package com.eskom.back.security;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;



    @Autowired
    private BCryptPasswordEncoder bCryptEncoder;

    @Autowired
    private UnAuthorizedUserAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private SecurityFilter secFilter;

    //Required in case of Stateless Authentication
    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService)
                .passwordEncoder(bCryptEncoder);
    }

    private static final List<String> AUTH_LIST = Arrays.asList(
            "/swagger-resources/**",
            "/swagger-ui.html**",
            "/webjars/**",
            "/swagger-ui/**",
            "favicon.ico");
//    @Bean
//    public BasicAuthenticationEntryPoint swaggerAuthenticationEntryPoint() {
//        BasicAuthenticationEntryPoint entryPoint = new BasicAuthenticationEntryPoint();
//        entryPoint.setRealmName("Swagger Realm");
//        return entryPoint;
//    }
    private class CustomRequestMatcher implements RequestMatcher {

        private List<AntPathRequestMatcher> matchers;

        private CustomRequestMatcher(List<String> matchers) {
            this.matchers = matchers.stream().map(AntPathRequestMatcher::new).collect(Collectors.toList());
        }

        @Override
        public boolean matches(HttpServletRequest request) {
            return matchers.stream().anyMatch(a -> a.matches(request));
        }

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()    //Disabling CSRF as not using form based login
                .authorizeRequests()
                .antMatchers("/signup", "/login","/images/**","/swagger-ui/**"
                ,"/swagger-resources/**","/v2/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
              //  .defaultAuthenticationEntryPointFor(swaggerAuthenticationEntryPoint(), new CustomRequestMatcher(AUTH_LIST))
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                //To Verify user from second request onwards............
                .and()
                .addFilterBefore(secFilter, UsernamePasswordAuthenticationFilter.class)
        ;
    }
}
