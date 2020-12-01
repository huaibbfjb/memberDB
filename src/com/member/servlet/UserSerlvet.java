package com.member.servlet;

import com.google.gson.Gson;
import com.member.dao.BaseDao;
import com.member.entity.User;
import com.member.service.UserService;
import com.member.service.impl.UserServiceImpl;
import com.member.utils.Page;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ：liuyuntao
 * @date ：Created in 2020/12/1 15:57
 */
@WebServlet("/user.do")
public class UserSerlvet extends BaseServlet {
    UserService userService = new UserServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
    }

    public void queryPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer pageNo = Integer.valueOf(request.getParameter("pageNo"));
        Page<User> page = userService.queryByPage(pageNo, Page.PAGE_SIZE);
        Gson gson = new Gson();
        //转成字符串
        String jsonStr = gson.toJson(page);
        //写入返回信息
        response.getWriter().write(jsonStr);
    }
}
