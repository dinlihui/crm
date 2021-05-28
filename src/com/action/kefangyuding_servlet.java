package com.action;

import com.dao.DB;
import com.orm.Kefangyuding;
import com.service.liuService;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class kefangyuding_servlet extends HttpServlet {

   public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      String type = req.getParameter("type");
      if(type.endsWith("kefangyudingMana")) {
         this.kefangyudingMana(req, res);
      }

      if(type.endsWith("kefangyudingAdd")) {
         this.kefangyudingAdd(req, res);
      }

      if(type.endsWith("kefangyudingDel")) {
         this.kefangyudingDel(req, res);
      }

   }

   public void kefangyudingAdd(HttpServletRequest req, HttpServletResponse res) {
      int kefang_id = Integer.parseInt(req.getParameter("kefang_id"));
      String shijian = req.getParameter("shijian");
      String kehuname = req.getParameter("kehuname");
      String kehutel = req.getParameter("kehutel");
      String tianshu = req.getParameter("tianshu");
      String del = "no";
      String sql = "insert into t_kefangyuding(kefang_id,shijian,kehuname,kehutel,tianshu,del) values(?,?,?,?,?,?)";
      Object[] params = new Object[]{Integer.valueOf(kefang_id), shijian, kehuname, kehutel, tianshu, del};
      DB mydb = new DB();
      mydb.doPstm(sql, params);
      mydb.closed();
      req.setAttribute("message", "操作成功");
      req.setAttribute("path", "kefangyuding?type=kefangyudingMana");
      String targetURL = "/common/success.jsp";
      this.dispatch(targetURL, req, res);
   }

   public void kefangyudingDel(HttpServletRequest req, HttpServletResponse res) {
      String sql = "update t_kefangyuding set del=\'yes\' where id=" + Integer.parseInt(req.getParameter("id"));
      Object[] params = new Object[0];
      DB mydb = new DB();
      mydb.doPstm(sql, params);
      mydb.closed();
      req.setAttribute("message", "操作成功");
      req.setAttribute("path", "kefangyuding?type=kefangyudingMana");
      String targetURL = "/common/success.jsp";
      this.dispatch(targetURL, req, res);
   }

   public void kefangyudingMana(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      ArrayList kefangyudingList = new ArrayList();
      String sql = "select * from t_kefangyuding where del=\'no\'";
      Object[] params = new Object[0];
      DB mydb = new DB();

      try {
         mydb.doPstm(sql, params);
         ResultSet e = mydb.getRs();

         while(e.next()) {
            Kefangyuding kefangyuding = new Kefangyuding();
            kefangyuding.setId(e.getInt("id"));
            kefangyuding.setKefang_id(e.getInt("kefang_id"));
            kefangyuding.setShijian(e.getString("shijian"));
            kefangyuding.setKehuname(e.getString("kehuname"));
            kefangyuding.setKehutel(e.getString("kehutel"));
            kefangyuding.setTianshu(e.getString("tianshu"));
            kefangyuding.setKefang_fanjianhao(liuService.getKefangFangjianhao(e.getInt("kefang_id")));
            kefangyudingList.add(kefangyuding);
         }

         e.close();
      } catch (Exception var9) {
         var9.printStackTrace();
      }

      mydb.closed();
      req.setAttribute("kefangyudingList", kefangyudingList);
      req.getRequestDispatcher("admin/kefangyuding/kefangyudingMana.jsp").forward(req, res);
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
