package com.action;

import com.dao.DB;
import com.orm.TAdmin;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class admin_servlet extends HttpServlet {

   public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      String type = req.getParameter("type");
      if(type.endsWith("adminMana")) {
         this.adminMana(req, res);
      }

      if(type.endsWith("adminAdd")) {
         this.adminAdd(req, res);
      }

      if(type.endsWith("adminDel")) {
         this.adminDel(req, res);
      }

   }

   public void adminMana(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      ArrayList adminList = new ArrayList();
      String sql = "select * from t_admin";
      Object[] params = new Object[0];
      DB mydb = new DB();

      try {
         mydb.doPstm(sql, params);
         ResultSet e = mydb.getRs();

         while(e.next()) {
            TAdmin admin = new TAdmin();
            admin.setUserId(e.getInt("userId"));
            admin.setUserName(e.getString("userName"));
            admin.setUserPw(e.getString("userPw"));
            adminList.add(admin);
         }

         e.close();
      } catch (Exception var9) {
         var9.printStackTrace();
      }

      mydb.closed();
      req.setAttribute("adminList", adminList);
      req.getRequestDispatcher("admin/admin/adminMana.jsp").forward(req, res);
   }

   public void adminAdd(HttpServletRequest req, HttpServletResponse res) {
      String userName = req.getParameter("userName");
      String userPw = req.getParameter("userPw");
      String sql = "insert into t_admin(userName,userPw) values(?,?)";
      Object[] params = new Object[]{userName, userPw};
      DB mydb = new DB();
      mydb.doPstm(sql, params);
      mydb.closed();
      req.setAttribute("message", "ok");
      req.setAttribute("path", "admin?type=adminMana");
      String targetURL = "/common/success.jsp";
      this.dispatch(targetURL, req, res);
   }

   public void adminDel(HttpServletRequest req, HttpServletResponse res) {
      System.out.println(req.getParameter("userId") + "**");
      String sql = "delete from t_admin where userId=" + Integer.parseInt(req.getParameter("userId"));
      Object[] params = new Object[0];
      DB mydb = new DB();
      mydb.doPstm(sql, params);
      mydb.closed();
      req.setAttribute("message", "ok");
      req.setAttribute("path", "admin?type=adminMana");
      String targetURL = "/common/success.jsp";
      this.dispatch(targetURL, req, res);
   }

   public void dispatch(String targetURI, HttpServletRequest request, HttpServletResponse response) {
      RequestDispatcher dispatch = this.getServletContext().getRequestDispatcher(targetURI);

      try {
         dispatch.forward(request, response);
         return;
      } catch (ServletException var6) {
         var6.printStackTrace();
      } catch (IOException var7) {
         var7.printStackTrace();
      }

   }

   public void init(ServletConfig config) throws ServletException {
      super.init(config);
   }

   public void destroy() {}
}
