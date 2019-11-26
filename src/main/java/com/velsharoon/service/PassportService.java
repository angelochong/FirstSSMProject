package com.velsharoon.service;

import com.velsharoon.dao.IUserDao;
import com.velsharoon.utils.Convert;
import com.velsharoon.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PassportService {

    @Autowired
    IUserDao userDao;

    public User getUser(String userName, String password) {
        return userDao.selectUser(userName, password);
    }

    public String getCookie(User user) {
        int userId = user.getUserId();
        String password = user.getPassword();
        long currentTime = System.currentTimeMillis();
        String cookie = Convert.getMD5String(userId + password + currentTime);
        if (cookie != null) {
            userDao.updateCookie(String.valueOf(userId), cookie);
        }
        return cookie;
    }

    public Integer getUserCountByName(String userName) {
        return userDao.selectUserCountByName(userName);
    }

    public User getUserByCookie(String cookie) {
        return userDao.selectUserByCookie(cookie);
    }


    public Integer createUser(User user) {
        return userDao.insertUser(user);
    }
}
