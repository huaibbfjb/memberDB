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
        String sql = "INSERT INTO `User` (`username`, `password`, `image`, `type`, `status`) VALUES (?, ?, ?, ?, ?)";
        return update(sql, user.getUsername(), user.getPassword(), user.getImage(), user.getType(), user.getStatus());
    }

    public int delete(Long userId) {
        String sql = "DELETE FROM `User` WHERE `id` = ?";
        return update(sql, userId);
    }

    public int update(User user) {
        String sql = "UPDATE `User` SET `username` = ?, `password` = ?, `image` = ?, `type` = ?, `status` = ? WHERE `id` = ?";
        return update(sql, user.getUsername(), user.getPassword(), user.getImage(), user.getType(), user.getStatus(), user.getId());
    }

    public List<User> queryAll() {
        String sql = "SELECT * FROM `User`";
        return queryForList(User.class, sql);
    }

    public User queryUserById(Long id) {
        String sql = "SELECT * FROM User WHERE id = ?";
        return queryForOne(User.class, sql, id);
    }

    public Integer queryPageTotalCount() {
        String sql = "select count(1) from user";
        return Math.toIntExact((Long) queryForSingleValue(sql));
    }

    public List<User> queryUserByPage(int begin, int pageSize) {
        String sql = "select * from user limit ?,?";
        return queryForList(User.class, sql, begin, pageSize);
    }

    public User queryUserByNameAndPassword(User user) {
        String sql = "SELECT * FROM `User` WHERE  `username` = ? AND `password` = ?";
        return queryForOne(User.class, sql, user.getUsername(), user.getPassword());
    }


}
