package com.test.oauth2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.web.SecurityFilterChain;

import com.test.oauth2.service.CustomOAuth2UserSerivce;
import com.test.oauth2.service.OAuth2SuccessHandler;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final CustomOAuth2UserSerivce customOAuth2UserSerivce;
	private final OAuth2SuccessHandler oAuth2SuccessHandler;

	//암호 인코더 > 필요(X)
	@Bean
	SecurityFilterChain filterchain(HttpSecurity http) throws Exception {
		
		//CRSF > 비활성화
		http.csrf(auth -> auth.disable());
		
		//Form 로그인 방식(X) > 소셜 인증
		http.formLogin(auth -> auth.disable());
		
		//기본 인증 > 사용 안함
		http.httpBasic(auth -> auth.disable());
		
		//허가 URI
		http.authorizeHttpRequests(auth -> auth
			.requestMatchers("/").permitAll()
			.requestMatchers("/login/**").permitAll()
			.requestMatchers("/oauth2/**").permitAll()
			.requestMatchers("/register/**").permitAll()
			.anyRequest().authenticated()
		);
		
		//OAuth2 설정
		//- formLogin() > oauth2Login()
		http.oauth2Login(auth -> auth
			.loginPage("/login")
			.successHandler(oAuth2SuccessHandler) //로그인 성공 후 호출 > 첫방문 or 재방문?
			.userInfoEndpoint(config 
						-> config.userService(customOAuth2UserSerivce))
		);
		
		return http.build();
	}
	
}










