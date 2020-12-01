package com.member.dao;

import com.member.entity.User;

import java.util.List;

/**
 * @author ：liuyuntao
 * @date ：Created in 2020/12/1 14:50
 */
public interface UserDao {
    int insert(User user);

    int delete(Long userId);

    int update(User user);

    List<User> queryAll();

    List<User> queryUserById(Long userId);

    /**
     * 总页数
     *
     * @return
     */
    Integer queryPageTotalCount();

    /**
     * 分页查询
     *
     * @return
     */
    List<User> queryUserByPage(int begin, int pageSize);
}
