package com.member.servlet;


import com.member.dao.UserDao;
import com.member.dao.impl.UserDaoImpl;
import com.member.entity.User;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

@WebServlet("/fileUpload")
public class FileUploadServlet extends HttpServlet {
    UserDao userDao = null;

    public FileUploadServlet() {
        userDao = new UserDaoImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
                        System.out.println("普通表单项 name = " + fileItem.getFieldName());
                        //参数UTF-8解决乱码
                        System.out.println("value = " + fileItem.getString());
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
                        System.out.println("表单项的name = " + fileItem.getFieldName());
                        System.out.println("上传的文件名:" + fileItem.getName());
                        StringBuilder sb = new StringBuilder("E:\\upload\\");
                        sb.append(System.currentTimeMillis());
                        sb.append(fileItem.getName());
                        String path = sb.toString();
                        user.setImage(path);
                        fileItem.write(new File(path));
                    }
                }
            } catch (FileUploadException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("user:"+user);
            int result = userDao.update(user);
            System.out.println("result:"+result);
            response.getWriter().write(result);
        }
    }
}
