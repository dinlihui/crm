package com.action;

import com.dao.DB;
import com.orm.Goods;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class goods_servlet extends HttpServlet {

   public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      String type = req.getParameter("type");
      if(type.endsWith("goodsAdd")) {
         this.goodsAdd(req, res);
      }

      if(type.endsWith("goodsMana")) {
         this.goodsMana(req, res);
      }

      if(type.endsWith("goodsDel")) {
         this.goodsDel(req, res);
      }

   }

   public void goodsAdd(HttpServletRequest req, HttpServletResponse res) {
      String name1 = req.getParameter("name1");
      String miaoshu = req.getParameter("miaoshu");
      String fujian = req.getParameter("fujian");
      int jiage = Integer.parseInt(req.getParameter("jiage"));
      System.out.println(jiage);
      String del = "no";
      String sql = "insert into t_goods(name1,miaoshu,pic,jiage,del) values(?,?,?,?,?)";
      Object[] params = new Object[]{name1, miaoshu, fujian, Integer.valueOf(jiage), del};
      DB mydb = new DB();
      mydb.doPstm(sql, params);
      mydb.closed();
      req.setAttribute("message", "ok");
      req.setAttribute("path", "goods?type=goodsMana");
      String targetURL = "/common/success.jsp";
      this.dispatch(targetURL, req, res);
   }

   public void goodsDel(HttpServletRequest req, HttpServletResponse res) {
      int id = Integer.parseInt(req.getParameter("id"));
      String sql = "update t_goods set del=\'yes\' where id=?";
      Object[] params = new Object[]{Integer.valueOf(id)};
      DB mydb = new DB();
      mydb.doPstm(sql, params);
      mydb.closed();
      req.setAttribute("message", "ok");
      req.setAttribute("path", "goods?type=goodsMana");
      String targetURL = "/common/success.jsp";
      this.dispatch(targetURL, req, res);
   }

   public void goodsMana(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      ArrayList goodsList = new ArrayList();
      String sql = "select * from t_goods where del=\'no\'";
      Object[] params = new Object[0];
      DB mydb = new DB();

      try {
         mydb.doPstm(sql, params);
         ResultSet e = mydb.getRs();

         while(e.next()) {
            Goods goods = new Goods();
            goods.setId(e.getInt("id"));
            goods.setName1(e.getString("name1"));
            goods.setMiaoshu(e.getString("miaoshu"));
            goods.setPic(e.getString("pic"));
            goods.setJiage(e.getInt("jiage"));
            goodsList.add(goods);
         }

         e.close();
      } catch (Exception var9) {
         var9.printStackTrace();
      }

      mydb.closed();
      req.setAttribute("goodsList", goodsList);
      req.getRequestDispatcher("admin/goods/goodsMana.jsp").forward(req, res);
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
