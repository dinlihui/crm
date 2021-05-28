package com.action;

import com.dao.DB;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class kefangleixing_servlet extends HttpServlet {

   public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      String type = req.getParameter("type");
      if(type.endsWith("kefangleixingMana")) {
         this.kefangleixingMana(req, res);
      }

      if(type.endsWith("kefangleixingAdd")) {
         this.kefangleixingAdd(req, res);
      }

      if(type.endsWith("kefangleixingDel")) {
         this.kefangleixingDel(req, res);
      }

      if(type.endsWith("kefangleixingEdit")) {
         this.kefangleixingEdit(req, res);
      }

   }

   public void kefangleixingMana(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      ArrayList kefangleixingList = new ArrayList();
      String sql = "select * from t_kefangleixing where del=\'no\'";
      Object[] params = new Object[0];
      DB mydb = new DB();

      try {
         mydb.doPstm(sql, params);
         ResultSet e = mydb.getRs();

         while(e.next()) {
            ArrayList kefangleixing = new ArrayList();
            kefangleixing.add(e.getString("id"));
            kefangleixing.add(e.getString("name"));
            kefangleixing.add(e.getString("beizhu"));
            kefangleixing.add(e.getString("del"));
            kefangleixingList.add(kefangleixing);
         }

         e.close();
      } catch (Exception var9) {
         var9.printStackTrace();
      }

      mydb.closed();
      req.setAttribute("kefangleixingList", kefangleixingList);
      req.getRequestDispatcher("admin/kefangleixing/kefangleixingMana.jsp").forward(req, res);
   }

   public void kefangleixingAdd(HttpServletRequest req, HttpServletResponse res) {
      String name = req.getParameter("name");
      String beizhu = "";
      String del = "no";
      String sql = "insert into t_kefangleixing(name,beizhu,del) values(?,?,?)";
      Object[] params = new Object[]{name, beizhu, del};
      DB mydb = new DB();
      mydb.doPstm(sql, params);
      mydb.closed();
      req.setAttribute("message", "操作成功");
      req.setAttribute("path", "kefangleixing?type=kefangleixingMana");
      String targetURL = "/common/success.jsp";
      this.dispatch(targetURL, req, res);
   }

   public void kefangleixingDel(HttpServletRequest req, HttpServletResponse res) {
      String sql = "update t_kefangleixing set del=\'yes\' where id=" + Integer.parseInt(req.getParameter("id"));
      Object[] params = new Object[0];
      DB mydb = new DB();
      mydb.doPstm(sql, params);
      mydb.closed();
      req.setAttribute("message", "操作成功");
      req.setAttribute("path", "kefangleixing?type=kefangleixingMana");
      String targetURL = "/common/success.jsp";
      this.dispatch(targetURL, req, res);
   }

   public void kefangleixingEdit(HttpServletRequest req, HttpServletResponse res) {
      String name = req.getParameter("name");
      String beizhu = "";
      String del = "no";
      String sql = "update t_kefangleixing set name=? where id=" + Integer.parseInt(req.getParameter("id"));
      Object[] params = new Object[]{name};
      DB mydb = new DB();
      mydb.doPstm(sql, params);
      mydb.closed();
      req.setAttribute("message", "操作成功");
      req.setAttribute("path", "kefangleixing?type=kefangleixingMana");
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
