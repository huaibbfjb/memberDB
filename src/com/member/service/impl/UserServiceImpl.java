package com.member.service.impl;

import com.member.dao.UserDao;
import com.member.dao.impl.UserDaoImpl;
import com.member.entity.User;
import com.member.service.UserService;
import com.member.utils.Page;

import java.util.List;

public class UserServiceImpl implements UserService {
    UserDao userDao = new UserDaoImpl();

    public Page<User> queryByPage(int pageNo, int pageSize) {
        Page<User> page = new Page<User>();
        //设置当前页码
        page.setPageNo(pageNo);
        //设置每页展示的数量
        page.setPageSize(pageSize);
        //求总记录数
        Integer pageTotalCount = userDao.queryPageTotalCount();
        //设置总记录数
        page.setPageTotalCount(pageTotalCount);
        //求总页码
        Integer pageTotal = pageTotalCount / pageSize;
        //如果总记录数 对 每页展示的记录数 不能整除==>总页数要加一
        if (pageTotalCount % pageSize > 0) {
            pageTotal += 1;
        }
        //设置总页码
        page.setPageTotal(pageTotal);
        //求当前页数据的开始索引
        int begin = (page.getPageNo() - 1) * pageSize;
        List<User> items = userDao.queryUserByPage(begin, pageSize);
        //设置当前页面数据
        page.setItems(items);
        return page;
    }
    public int insert(User user) {
        return userDao.insert(user);
    }

    public int delete(Long userId) {
        return userDao.delete(userId);
    }

    public int update(User user) {
        return userDao.update(user);
    }

    public List<User> queryAll() {
        return userDao.queryAll();
    }

    public User queryUserById(Long id) {
        return userDao.queryUserById(id);
    }

    public User login(User user) {
        return userDao.queryUserByNameAndPassword(user);
    }


}
