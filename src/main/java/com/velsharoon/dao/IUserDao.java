package com.velsharoon.dao;

import com.velsharoon.vo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IUserDao {

    List<User> getUserList();

    User getUser(@Param("name") String userName, @Param("password") String password);

    User getUserByCookie(@Param("cookie") String cookie);

    void saveCookie(@Param("userId") String userId, @Param("cookie") String cookie);

    boolean userExist(@Param("name") String userName);

    User createUser(@Param("name") String userName, @Param("password") String password);
}
