package com.kaishengit.web;

import com.google.gson.Gson;
import com.kaishengit.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by loveoh on 2016/12/15.
 */
public class BaseServlet extends HttpServlet {


    protected void forward(String path,HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/"+path+".jsp").forward(req,resp);
    }

    public void renderText(String str,HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print(str);
        out.flush();
        out.close();
    }

    public void renderJSON(Object object,HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        String json = new Gson().toJson(object);
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
        out.close();
    }

    /**
     * 获取当前session的用户
     * @param request
     * @return
     */
    public User getCurrentUser (HttpServletRequest request){
        HttpSession session = request.getSession();

        if(session.getAttribute("curr_user") == null){
            return null;
        }else{
            return (User) session.getAttribute("curr_user");
        }

    }
}
