package com.member.test;

import com.member.dao.UserDao;
import com.member.dao.impl.UserDaoImpl;
import org.junit.Test;

/**
 * @author ：liuyuntao
 * @date ：Created in 2020/12/1 15:07
 */
public class UserTest {
    UserDao userDao = new UserDaoImpl();

    @Test
    public void test1() {
        System.out.println(userDao.queryUserByPage(0, 2));
    }
}
