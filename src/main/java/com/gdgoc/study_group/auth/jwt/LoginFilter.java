package com.gdgoc.study_group.auth.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class LoginFilter extends AbstractAuthenticationProcessingFilter {

    private static final String LOGIN_URL = "/auth/login";
    private static final String CONTENT_TYPE = "application/json";
    private static final String STUDENTNUMBER_KEY = "studentNumber";
    private static final String PASSWORD_KEY = "password";
    private final ObjectMapper objectMapper;
    private final JwtUtil jwtUtil;

    public LoginFilter(ObjectMapper objectMapper, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        super(LOGIN_URL, authenticationManager);
        this.objectMapper = objectMapper;
        this.jwtUtil = jwtUtil;
    }

    /**
     * 요청과 응답을 가로채서 사용자의 로그인 정보로 회원 검증을 합니다.
     *
     * @param request HTTP 요청 객체
     * @param response HTTP 응답 객체
     * @return 인증 성공 시 Authentication 객체 반환
     * @throws AuthenticationException 인증 실패 시 발생
     * @throws IOException 요청이 비었을 때 발생
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {

        if (request.getContentType() == null || !request.getContentType().equals(CONTENT_TYPE)) {
            throw new AuthenticationServiceException("로그인 요청 형식이 잘못되었습니다.");
        }

        // json 형식의 로그인 정보 훔쳐오기
        Map<String, String> parameter = objectMapper.readValue(request.getInputStream(), Map.class);
        String studentNumber = parameter.get(STUDENTNUMBER_KEY);
        String password = parameter.get(PASSWORD_KEY);

        System.out.println(studentNumber);

        // authenticationManager에 가로챈 응답인 studentNumber와 password를 authToken에 담아서 보내주자 "authentication매니절~? 검증해줘"
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(studentNumber, password);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    /**
     * 로그인 인증 성공 시, 아래 메소드가 실행됩니다.
     * 이후, JWTUtil에서 JWT가 발급됩니다.
     *
     * @param request
     * @param response
     * @param chain
     * @param authentication
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {

        // 유저 정보
        String studentNumber = authentication.getName();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

//        String access = jwtUtil.createJWT("access", studentNumber, role, 600000L);
        String access = jwtUtil.createJWT(studentNumber, role, 600000L);
        response.setHeader("Authorization", "Bearer " + access);

        response.setStatus(HttpStatus.OK.value());
    }

    /**
     * 로그인 인증 실패 시, 아래 메소드가 실행됩니다.
     * @param request
     * @param response
     * @param failed
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {

        response.setStatus(401);
    }
}
