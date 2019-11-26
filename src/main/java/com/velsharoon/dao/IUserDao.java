package com.velsharoon.dao;

import com.velsharoon.vo.User;
import org.apache.ibatis.annotations.Param;

public interface IUserDao {

    User selectUser(@Param("name") String userName, @Param("password") String password);

    User selectUserByCookie(@Param("cookie") String cookie);

    void updateCookie(@Param("userId") String userId, @Param("cookie") String cookie);

    Integer insertUser(@Param("user") User user);

    Integer selectUserCountByName(@Param("name") String userName);
}
