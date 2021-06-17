package com.sbdc.sbdcweb.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.sbdc.sbdcweb.security.jwt.JwtAuthEntryPoint;
import com.sbdc.sbdcweb.security.jwt.JwtAuthTokenFilter;

/**
 * WebSecurityConfig
 * WebSecurityConfig 상속 구현  클래스
 * 
 * @author  : 이승희
 * @version : 1.0
 * @since   : 1.0
 * @date    : 2019-05-05
 */
@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true
)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private JwtAuthEntryPoint unauthorizedHandler;

    @Bean
    public JwtAuthTokenFilter authenticationJwtTokenFilter() {
        return new JwtAuthTokenFilter();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
        		.userDetailsService(userDetailsServiceImpl)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 패스워드 인코딩 함수
     * Damo 솔루션 암호화 적용을 위해 자바에서는 인코딩 적용하지 않음
     */
	@Bean
    public PasswordEncoder passwordEncoder() {
//      return new BCryptPasswordEncoder();
      return NoOpPasswordEncoder.getInstance();
    }

	/**
     * configure
     * cors, csrf 설정 적용
     * antMatchers 이용하여 각 api 별 HTTP 함수 컨트롤 및 어플리케이션의 대한 권한 설정
     * exception 핸들링 처리
     * 필터 적용 처리
     * (Jwt 필터 추가 적용)
     *
     * @param http     HttpSecurity 객체
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
        .and()
        .csrf().disable().authorizeRequests()
    	.antMatchers(HttpMethod.POST, "/board/bid").authenticated()
    	.antMatchers(HttpMethod.PUT, "/board/bid/{id}").authenticated()
    	.antMatchers(HttpMethod.DELETE, "/board/bid/{id}").authenticated()
    	.antMatchers(HttpMethod.POST, "/board/dongjung").authenticated()
    	.antMatchers(HttpMethod.PUT, "/board/dongjung/{id}").authenticated()
    	.antMatchers(HttpMethod.DELETE, "/board/dongjung/{id}").authenticated()
    	.antMatchers(HttpMethod.POST, "/board/incruit").authenticated()
    	.antMatchers(HttpMethod.PUT, "/board/incruit/{id}").authenticated()
    	.antMatchers(HttpMethod.DELETE, "/board/incruit/{id}").authenticated()
    	.antMatchers(HttpMethod.POST, "/board/notice").authenticated()
    	.antMatchers(HttpMethod.PUT, "/board/notice/{id}").authenticated()
    	.antMatchers(HttpMethod.DELETE, "/board/notice/{id}").authenticated()
    	.antMatchers(HttpMethod.POST, "/info/**").authenticated()
    	.antMatchers(HttpMethod.PUT, "/info/**").authenticated()
    	.antMatchers(HttpMethod.DELETE, "/info/**").authenticated()
//        .antMatchers("/","/admin/api/auth/admin/signin","/admin/board/attach/**","/admin/jwt/**").permitAll()
//        .antMatchers("/admin/api/auth/admin/member/**","/admin/api/auth/admin/log/**","/admin/api/auth/admin/role/**","/admin/api/auth/admin/user/**","/admin/mail/**","/admin/menu/**").hasRole("SYSADMIN")
//        .antMatchers("/admin/api/auth/admin/mypage/**").hasAnyRole("SYSADMIN","PW_UPDATE")
//        .antMatchers("/admin/banner").hasAnyRole("SYSADMIN","BANNER")
//        .antMatchers("/admin/board/bid/**","/board/contract-type/**").hasAnyRole("SYSADMIN","BID")
//        .antMatchers("/admin/board/consult/**","/board/consult-type/**").hasAnyRole("SYSADMIN","CONSULT")
//        .antMatchers("/admin/board/dongjung/**").hasAnyRole("SYSADMIN","DONGJUNG")
//        .antMatchers("/admin/board/eqna/**").hasAnyRole("SYSADMIN","EQNA")
//        .antMatchers("/admin/board/hongbo/**").hasRole("SYSADMIN")
//        .antMatchers("/admin/board/incruit/**").hasAnyRole("SYSADMIN","INCRUIT")
//        .antMatchers("/admin/board/inside/**").hasAnyRole("SYSADMIN","INSIDE")
//        .antMatchers("/admin/board/notice/**").hasAnyRole("SYSADMIN","NOTICE")
//        .antMatchers("/admin/board/qna/**").hasAnyRole("SYSADMIN","QNA")
//        .antMatchers("/admin/board/report/**","/admin/info/inspect/**","/admin/info/money/**","/admin/info/money-two/**").hasAnyRole("SYSADMIN","MINWON")
//        .antMatchers("/admin/company/**").hasAnyRole("SYSADMIN","OGCHART")
//        .antMatchers("/admin/info/ethic/**").hasAnyRole("SYSADMIN","ETHIC")
//        .antMatchers("/admin/info/business/**").hasAnyRole("SYSADMIN","INFOBUSINESS")
//        .antMatchers("/admin/info/busi/**","/info/open/**","/info/open-two/**").hasAnyRole("SYSADMIN","INFOOPEN")
//        .antMatchers("/admin/info/cost/**").hasAnyRole("SYSADMIN","EXECUTIVECOST")
//        .antMatchers("/admin/info/list/**").hasAnyRole("SYSADMIN","INFOLIST")
//        .antMatchers("/admin/info/pre/**","/admin/info/private/**","/admin/info/private-two/**").hasAnyRole("SYSADMIN","CONTRACT")
//        .antMatchers("/admin/info/records/**").hasAnyRole("SYSADMIN","INFORECORDS")
//        .antMatchers("/admin/api/auth/admin").hasRole("SYSADMIN")
//        .antMatchers("/admin/api/auth/admin").access("hasRole('SYSADMIN')")
        .anyRequest().permitAll() // 인증없이 모두 가능 //
//        .anyRequest().authenticated() // 인증있이 모두 가능
        .and()
        .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
        .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.headers().disable();

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

}