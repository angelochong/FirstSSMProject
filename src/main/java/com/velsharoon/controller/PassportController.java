package com.velsharoon.controller;

import com.velsharoon.http.ServerResponse;
import com.velsharoon.service.PassportService;
import com.velsharoon.vo.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class PassportController {

    @Resource
    PassportService passportService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ServerResponse<User> login(HttpServletRequest request, HttpServletResponse response) {
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        User user = passportService.getUser(userName, password);
        if (user != null) {
            String cookieStr = passportService.getCookie(user);
            Cookie cookie = new Cookie("Cookie", cookieStr);
            response.addCookie(cookie);
            return ServerResponse.createBySuccess("成功", user);
        }
        return ServerResponse.createByError("无此用户，请先注册");
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ServerResponse<User> register(HttpServletRequest request, HttpServletResponse response) {
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        Integer count = passportService.getUserCountByName(userName);
        if (count == 0) {
            User user = new User();
            user.setName(userName);
            user.setPassword(password);
            passportService.createUser(user);
            System.out.println(user.getUserId());
            System.out.println(user.getName() + user.getPassword());
            String cookieStr = passportService.getCookie(user);
            Cookie cookie = new Cookie("Cookie", cookieStr);
            response.addCookie(cookie);
            return ServerResponse.createBySuccess("成功", user);
        }
        return ServerResponse.createByError("用户已存在");
    }
}
