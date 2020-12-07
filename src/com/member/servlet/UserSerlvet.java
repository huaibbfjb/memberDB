package com.member.servlet;

import com.google.gson.Gson;
import com.member.dao.BaseDao;
import com.member.entity.User;
import com.member.service.UserService;
import com.member.service.impl.UserServiceImpl;
import com.member.utils.Page;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ：liuyuntao
 * @date ：Created in 2020/12/1 15:57
 */
@WebServlet("/user.do")
public class UserSerlvet extends BaseServlet {
    UserService userService = new UserServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        super.doGet(request, response);
    }

    public void queryPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer pageNo = Integer.valueOf(request.getParameter("pageNo"));
        Page<User> page = userService.queryByPage(pageNo, Page.PAGE_SIZE);
//        System.out.println(page);
        Gson gson = new Gson();
        //转成字符串
        String jsonStr = gson.toJson(page);
        //写入返回信息
        response.getWriter().write(jsonStr);
    }

    public void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long userId = Long.valueOf(request.getParameter("userId"));
        Integer result = userService.delete(userId);
        response.getWriter().write(result.toString());
    }

    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        User user1 = userService.login(user);
        System.out.println("当前登录用户:" + user1);
        if (user1 == null) {
            //登录失败
            response.getWriter().write("false");
        } else {
            //登录成功
            response.getWriter().write("true");
            request.getSession().setAttribute("user", user1);
        }
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().invalidate();
        response.sendRedirect("/login.html");
    }

    public void update(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        //1 先判断上传的数据是否多段数据 （只有是多段的数据，才是文件上传的）
        if (ServletFileUpload.isMultipartContent(request)) {
            // 创建FileItemFactory 工厂实现类
            FileItemFactory fileItemFactory = new DiskFileItemFactory();
            //创建用于解析上传数据的工具类
            ServletFileUpload servletFileUpload = new ServletFileUpload(fileItemFactory);
            User user = new User();
            try {
                //解析上传的数据 得到每一个表单项FileItem
                List<FileItem> list = servletFileUpload.parseRequest(request);
                //循环判断，每一个表单项 是普通类型 还是上传的文件
                for (FileItem fileItem :
                        list) {
                    if (fileItem.isFormField()) {
                        //普通表单项
                        //System.out.println("普通表单项 name = " + fileItem.getFieldName());
                        //参数UTF-8解决乱码
                        //System.out.println("value = " + fileItem.getString());
                        String str = fileItem.getFieldName();
                        if ("id".equals(str)) {
                            user.setId(Integer.valueOf(fileItem.getString()));

                        } else if ("username".equals(str)) {
                            user.setUsername(fileItem.getString());
                        } else if ("password".equals(str)) {
                            user.setPassword(fileItem.getString());
                        } else if ("type".equals(str)) {
                            user.setType(Integer.parseInt(fileItem.getString()));
                        } else if ("status".equals(str)) {
                            user.setStatus(Integer.parseInt(fileItem.getString()));
                        }
                    } else {
                        //上传的文件
                        //System.out.println("表单项的name = " + fileItem.getFieldName());
                        //System.out.println("上传的文件名:" + fileItem.getName());
                        if ("".equals(fileItem.getName())) {
                            user.setImage("E:\\upload\\default.png");
                            System.out.println("未上传头像，默认为默认头像!");
                        } else {
                            String reg = ".+(.JPEG|.jpeg|.JPG|.jpg)$";
                            Pattern pattern = Pattern.compile(reg);
                            Matcher matcher = pattern.matcher(fileItem.getName());
                            //System.out.println(matcher.find());
                            if (matcher.find() == false) {
                                user.setImage("E:\\upload\\default.png");
                                System.out.println("上传的文件不是图片格式，默认为默认头像!");
                            } else {
                                StringBuilder sb = new StringBuilder("E:\\upload\\");
                                sb.append(System.currentTimeMillis());
                                sb.append(fileItem.getName());
                                String path = sb.toString();
                                fileItem.write(new File(path));
                                user.setImage(path);
                            }
                        }
                    }
                }
            } catch (FileUploadException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("user:" + user);
            Integer result = userService.update(user);
            System.out.println("result:" + result);
            response.getWriter().write(result.toString());

        }
    }

    //增加
    public void insert(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //防止中文乱码的金句
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        //String username=request.getParameter("username");
        //System.out.println("username:"+username);
        //1 先判断上传的数据是否多段数据 （只有是多段的数据，才是文件上传的）
        if (ServletFileUpload.isMultipartContent(request)) {
            // 创建FileItemFactory 工厂实现类
            FileItemFactory fileItemFactory = new DiskFileItemFactory();
            //创建用于解析上传数据的工具类
            ServletFileUpload servletFileUpload = new ServletFileUpload(fileItemFactory);
            User user = new User();
            try {
                //解析上传的数据 得到每一个表单项FileItem
                List<FileItem> list = servletFileUpload.parseRequest(request);
                //循环判断，每一个表单项 是普通类型 还是上传的文件
                for (FileItem fileItem : list) {
                    if (fileItem.isFormField()) {
                        //普通表单项
                        //System.out.println("普通表单项 name = " + fileItem.getFieldName());
                        //参数UTF-8解决乱码
                        //System.out.println("value = " + fileItem.getString());
                        //获取键
                        String str = fileItem.getFieldName();
                        if ("id".equals(str)) {
                            user.setId(Integer.valueOf(fileItem.getString()));
                        } else if ("username".equals(str)) {
                            user.setUsername(fileItem.getString());
                        } else if ("password".equals(str)) {
                            user.setPassword(fileItem.getString());
                        } else if ("type".equals(str)) {
                            user.setType(Integer.parseInt(fileItem.getString()));
                        } else if ("status".equals(str)) {
                            user.setStatus(Integer.parseInt(fileItem.getString()));
                        }
                    } else {
                        //上传的头像
                        //System.out.println("表单项的name = " + fileItem.getFieldName());
                        //System.out.println("上传的文件名:" + fileItem.getName());
                        if ("".equals(fileItem.getName())) {
                            user.setImage("E:\\upload\\default.png");
                            System.out.println("未上传头像，默认为默认头像!");
                        } else {
                            String reg = ".+(.JPEG|.jpeg|.JPG|.jpg)$";
                            Pattern pattern = Pattern.compile(reg);
                            Matcher matcher = pattern.matcher(fileItem.getName());
                            //System.out.println(matcher.find());
                            if (matcher.find() == false) {
                                user.setImage("E:\\upload\\default.png");
                                System.out.println("上传的文件不是图片格式，默认为默认头像!");
                            } else {
                                StringBuilder sb = new StringBuilder("E:\\upload\\");
                                sb.append(System.currentTimeMillis());
                                sb.append(fileItem.getName());
                                String path = sb.toString();
                                fileItem.write(new File(path));
                                user.setImage(path);
                            }
                        }

                    }
                }
            } catch (FileUploadException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //System.out.println(user);
            Integer result = userService.insert(user);
            response.getWriter().write(result.toString());
        }


    }

}
