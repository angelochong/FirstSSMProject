package com.velsharoon.controller;

import com.velsharoon.service.UserService;
import com.velsharoon.vo.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class UserController {

    @Resource
    UserService userService;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public User test(HttpServletRequest request, HttpServletResponse response) {
        User user = (User)request.getAttribute("user");
        return user;
    }
}
