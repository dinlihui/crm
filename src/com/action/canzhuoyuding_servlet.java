package com.action;

import com.dao.DB;
import com.orm.Canzhuoyuding;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class canzhuoyuding_servlet extends HttpServlet {

   public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      String type = req.getParameter("type");
      if(type.endsWith("canzhuoyudingMana")) {
         this.canzhuoyudingMana(req, res);
      }

      if(type.endsWith("canzhuoyudingAdd")) {
         this.canzhuoyudingAdd(req, res);
      }

      if(type.endsWith("canzhuoyudingDel")) {
         this.canzhuoyudingDel(req, res);
      }

   }

   public void canzhuoyudingAdd(HttpServletRequest req, HttpServletResponse res) {
      String shijian = req.getParameter("shijian");
      String kehuname = req.getParameter("kehuname");
      String zhuohao = req.getParameter("zhuohao");
      String del = "no";
      String sql = "insert into t_canzhuoyuding(shijian,kehuname,zhuohao,del) values(?,?,?,?)";
      Object[] params = new Object[]{shijian, kehuname, zhuohao, del};
      DB mydb = new DB();
      mydb.doPstm(sql, params);
      mydb.closed();
      req.setAttribute("message", "ok");
      req.setAttribute("path", "canzhuoyuding?type=canzhuoyudingMana");
      String targetURL = "/common/success.jsp";
      this.dispatch(targetURL, req, res);
   }

   public void canzhuoyudingDel(HttpServletRequest req, HttpServletResponse res) {
      String sql = "update t_canzhuoyuding set del=\'yes\' where id=" + Integer.parseInt(req.getParameter("id"));
      Object[] params = new Object[0];
      DB mydb = new DB();
      mydb.doPstm(sql, params);
      mydb.closed();
      req.setAttribute("message", "ok");
      req.setAttribute("path", "canzhuoyuding?type=canzhuoyudingMana");
      String targetURL = "/common/success.jsp";
      this.dispatch(targetURL, req, res);
   }

   public void canzhuoyudingMana(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      ArrayList canzhuoyudingList = new ArrayList();
      String sql = "select * from t_canzhuoyuding where del=\'no\'";
      Object[] params = new Object[0];
      DB mydb = new DB();

      try {
         mydb.doPstm(sql, params);
         ResultSet e = mydb.getRs();

         while(e.next()) {
            Canzhuoyuding canzhuoyuding = new Canzhuoyuding();
            canzhuoyuding.setId(e.getInt("id"));
            canzhuoyuding.setShijian(e.getString("shijian"));
            canzhuoyuding.setKehuname(e.getString("kehuname"));
            canzhuoyuding.setZhuohao(e.getString("zhuohao"));
            canzhuoyudingList.add(canzhuoyuding);
         }

         e.close();
      } catch (Exception var9) {
         var9.printStackTrace();
      }

      mydb.closed();
      req.setAttribute("canzhuoyudingList", canzhuoyudingList);
      req.getRequestDispatcher("admin/canzhuoyuding/canzhuoyudingMana.jsp").forward(req, res);
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
