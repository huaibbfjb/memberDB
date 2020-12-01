package com.member.dao.impl;

import com.member.dao.BaseDao;
import com.member.dao.UserDao;
import com.member.entity.User;

import java.util.List;

/**
 * @author ：liuyuntao
 * @date ：Created in 2020/12/1 14:53
 */
public class UserDaoImpl extends BaseDao implements UserDao {

    public int insert(User user) {
        return 0;
    }

    public int delete(Long userId) {
        return 0;
    }

    public int update(User user) {
        return 0;
    }

    public List<User> queryAll() {
        return null;
    }

    public List<User> queryUserById(Long userId) {
        return null;
    }

    public Integer queryPageTotalCount() {
        String sql = "select count(1) from user";
        return Math.toIntExact((Long) queryForSingleValue(sql));
    }

    public List<User> queryUserByPage(int begin, int pageSize) {
        String sql = "select * from user limit ?,?";
        return queryForList(User.class, sql, begin, pageSize);
    }


}
