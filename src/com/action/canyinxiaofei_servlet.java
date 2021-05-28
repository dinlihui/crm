package com.action;

import com.dao.DB;
import com.orm.Canyinxiaofei;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class canyinxiaofei_servlet extends HttpServlet {

   public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      String type = req.getParameter("type");
      if(type.endsWith("canyinxiaofeiMana")) {
         this.canyinxiaofeiMana(req, res);
      }

      if(type.endsWith("canyinxiaofeiAdd")) {
         this.canyinxiaofeiAdd(req, res);
      }

      if(type.endsWith("canyinxiaofeiDel")) {
         this.canyinxiaofeiDel(req, res);
      }

   }

   public void canyinxiaofeiAdd(HttpServletRequest req, HttpServletResponse res) {
      String shijian = req.getParameter("shijian");
      String kehuname = req.getParameter("kehuname");
      int jine = Integer.parseInt(req.getParameter("jine"));
      String del = "no";
      String sql = "insert into t_canyinxiaofei(shijian,kehuname,jine,del) values(?,?,?,?)";
      Object[] params = new Object[]{shijian, kehuname, Integer.valueOf(jine), del};
      DB mydb = new DB();
      mydb.doPstm(sql, params);
      mydb.closed();
      req.setAttribute("message", "操作成功");
      req.setAttribute("path", "canyinxiaofei?type=canyinxiaofeiMana");
      String targetURL = "/common/success.jsp";
      this.dispatch(targetURL, req, res);
   }

   public void canyinxiaofeiDel(HttpServletRequest req, HttpServletResponse res) {
      String sql = "update t_canyinxiaofei set del=\'yes\' where id=" + Integer.parseInt(req.getParameter("id"));
      Object[] params = new Object[0];
      DB mydb = new DB();
      mydb.doPstm(sql, params);
      mydb.closed();
      req.setAttribute("message", "操作成功");
      req.setAttribute("path", "canyinxiaofei?type=canyinxiaofeiMana");
      String targetURL = "/common/success.jsp";
      this.dispatch(targetURL, req, res);
   }

   public void canyinxiaofeiMana(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      ArrayList canyinxiaofeiList = new ArrayList();
      String sql = "select * from t_canyinxiaofei where del=\'no\'";
      Object[] params = new Object[0];
      DB mydb = new DB();

      try {
         mydb.doPstm(sql, params);
         ResultSet e = mydb.getRs();

         while(e.next()) {
            Canyinxiaofei canyinxiaofei = new Canyinxiaofei();
            canyinxiaofei.setId(e.getInt("id"));
            canyinxiaofei.setShijian(e.getString("shijian"));
            canyinxiaofei.setKehuname(e.getString("kehuname"));
            canyinxiaofei.setJine(e.getInt("jine"));
            canyinxiaofeiList.add(canyinxiaofei);
         }

         e.close();
      } catch (Exception var9) {
         var9.printStackTrace();
      }

      mydb.closed();
      req.setAttribute("canyinxiaofeiList", canyinxiaofeiList);
      req.getRequestDispatcher("admin/canyinxiaofei/canyinxiaofeiMana.jsp").forward(req, res);
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
