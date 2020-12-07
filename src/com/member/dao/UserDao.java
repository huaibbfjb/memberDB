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

    User queryUserById(Long id);

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

    User queryUserByNameAndPassword(User user);
}
