package com.velsharoon.filter;

import com.velsharoon.service.PassportService;
import com.velsharoon.vo.User;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.velsharoon.http.ResponseCode.NEED_LOG;


//@WebFilter("/*")
public class AuthFilter implements Filter {

    private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList("/login", "/register", "")));

    @Resource
    PassportService passportService;

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }


    @Override
    public void doFilter(ServletRequest srequest, ServletResponse sresponse, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) srequest;
        String path = request.getRequestURI().substring(request.getContextPath().length()).replaceAll("[/]+$", "");
        boolean allowedPath = ALLOWED_PATHS.contains(path);
        if (allowedPath) {
            chain.doFilter(srequest, sresponse);
        } else {
            Cookie[] cookies = request.getCookies();
            boolean hasLogin = false;
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("Cookie".equals(cookie.getName())) {
                        String c = cookie.getValue();
                        User user = passportService.getUserByCookie(c);
                        request.setAttribute("user", user);
                        hasLogin = true;
                    }
                }
            }
            if (hasLogin) {
                chain.doFilter(srequest, sresponse);
            } else {
                HttpServletResponse response = (HttpServletResponse) sresponse;
                response.sendError(NEED_LOG.getCode(), "请先登录");
            }
        }

    }
}
