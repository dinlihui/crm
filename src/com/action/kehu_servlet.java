package com.action;

import com.dao.DB;
import com.orm.kehu;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class kehu_servlet extends HttpServlet {

   public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      String type = req.getParameter("type");
      if(type.endsWith("kehuAdd")) {
         this.kehuAdd(req, res);
      }

      if(type.endsWith("kehuMana")) {
         this.kehuMana(req, res);
      }

      if(type.endsWith("kehuDel")) {
         this.kehuDel(req, res);
      }

      if(type.endsWith("kehuEdit")) {
         this.kehuEdit(req, res);
      }

      if(type.endsWith("kehuSearch")) {
         this.kehuSearch(req, res);
      }

      if(type.endsWith("kehuXinxi")) {
         this.kehuXinxi(req, res);
      }

   }

   public void kehuAdd(HttpServletRequest req, HttpServletResponse res) {
      String mingcheng = req.getParameter("mingcheng");
      String dizhi = req.getParameter("dizhi");
      String lianxiren = req.getParameter("lianxiren");
      String dianhua = req.getParameter("dianhua");
      String youbian = req.getParameter("youbian");
      String chuanzhen = req.getParameter("chuanzhen");
      String youxiang = req.getParameter("youxiang");
      String sql = "insert into t_kehu(mingcheng,dizhi,lianxiren,dianhua,youbian,chuanzhen,youxiang,kahuhang,zhanghao,type,del) values(?,?,?,?,?,?,?,?,?,?,?)";
      Object[] params = new Object[]{mingcheng, dizhi, lianxiren, dianhua, youbian, chuanzhen, youxiang, "", "", "kehu", "no"};
      DB mydb = new DB();
      mydb.doPstm(sql, params);
      mydb.closed();
      req.setAttribute("message", "ok");
      req.setAttribute("path", "kehu?type=kehuMana");
      String targetURL = "/common/success.jsp";
      this.dispatch(targetURL, req, res);
   }

   public void kehuDel(HttpServletRequest req, HttpServletResponse res) {
      int id = Integer.parseInt(req.getParameter("id"));
      String sql = "update t_kehu set del=\'yes\' where id=?";
      Object[] params = new Object[]{Integer.valueOf(id)};
      DB mydb = new DB();
      mydb.doPstm(sql, params);
      mydb.closed();
      req.setAttribute("message", "ok");
      req.setAttribute("path", "kehu?type=kehuMana");
      String targetURL = "/common/success.jsp";
      this.dispatch(targetURL, req, res);
   }

   public void kehuMana(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      ArrayList kehuList = new ArrayList();
      String sql = "select * from t_kehu where type=\'kehu\' and del=\'no\'";
      Object[] params = new Object[0];
      DB mydb = new DB();

      try {
         mydb.doPstm(sql, params);
         ResultSet e = mydb.getRs();

         while(e.next()) {
            kehu kehu = new kehu();
            kehu.setId(e.getInt("id"));
            kehu.setMingcheng(e.getString("mingcheng"));
            kehu.setDizhi(e.getString("dizhi"));
            kehu.setLianxiren(e.getString("lianxiren"));
            kehu.setDianhua(e.getString("dianhua"));
            kehu.setYoubian(e.getString("youbian"));
            kehu.setChuanzhen(e.getString("chuanzhen"));
            kehu.setYouxiang(e.getString("youxiang"));
            kehuList.add(kehu);
         }

         e.close();
      } catch (Exception var9) {
         var9.printStackTrace();
      }

      mydb.closed();
      req.setAttribute("kehuList", kehuList);
      req.getRequestDispatcher("admin/kehu/kehuMana.jsp").forward(req, res);
   }

   public void kehuXinxi(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      ArrayList kehuList = new ArrayList();
      String sql = "select * from t_kehu where type=\'kehu\' and id=?";
      Object[] params = new Object[]{Integer.valueOf(Integer.parseInt(req.getParameter("kehu_id")))};
      DB mydb = new DB();

      try {
         mydb.doPstm(sql, params);
         ResultSet e = mydb.getRs();

         while(e.next()) {
            kehu kehu = new kehu();
            kehu.setId(e.getInt("id"));
            kehu.setMingcheng(e.getString("mingcheng"));
            kehu.setDizhi(e.getString("dizhi"));
            kehu.setLianxiren(e.getString("lianxiren"));
            kehu.setDianhua(e.getString("dianhua"));
            kehu.setYoubian(e.getString("youbian"));
            kehu.setChuanzhen(e.getString("chuanzhen"));
            kehu.setYouxiang(e.getString("youxiang"));
            kehuList.add(kehu);
         }

         e.close();
      } catch (Exception var9) {
         var9.printStackTrace();
      }

      mydb.closed();
      req.setAttribute("kehuList", kehuList);
      req.getRequestDispatcher("admin/kehu/kehuXinxi.jsp").forward(req, res);
   }

   public void kehuEdit(HttpServletRequest req, HttpServletResponse res) {
      int id = Integer.parseInt(req.getParameter("id"));
      String mingcheng = req.getParameter("mingcheng");
      String dizhi = req.getParameter("dizhi");
      String lianxiren = req.getParameter("lianxiren");
      String dianhua = req.getParameter("dianhua");
      String youbian = req.getParameter("youbian");
      String chuanzhen = req.getParameter("chuanzhen");
      String youxiang = req.getParameter("youxiang");
      String sql = "update t_kehu set mingcheng=?,dizhi=?,lianxiren=?,dianhua=?,youbian=?,chuanzhen=?,youxiang=? where id=" + id;
      Object[] params = new Object[]{mingcheng, dizhi, lianxiren, dianhua, youbian, chuanzhen, youxiang};
      DB mydb = new DB();
      mydb.doPstm(sql, params);
      mydb.closed();
      req.setAttribute("message", "ok");
      req.setAttribute("path", "kehu?type=kehuMana");
      String targetURL = "/common/success.jsp";
      this.dispatch(targetURL, req, res);
   }

   public void kehuSearch(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      ArrayList kehuList = new ArrayList();
      String sql = "select * from t_kehu where type=\'kehu\' and del=\'no\' and mingcheng like \'%" + req.getParameter("mingcheng").trim() + "%\'";
      System.out.println(sql);
      Object[] params = new Object[0];
      DB mydb = new DB();

      try {
         mydb.doPstm(sql, params);
         ResultSet e = mydb.getRs();

         while(e.next()) {
            kehu kehu = new kehu();
            kehu.setId(e.getInt("id"));
            kehu.setMingcheng(e.getString("mingcheng"));
            kehu.setDizhi(e.getString("dizhi"));
            kehu.setLianxiren(e.getString("lianxiren"));
            kehu.setDianhua(e.getString("dianhua"));
            kehu.setYoubian(e.getString("youbian"));
            kehu.setChuanzhen(e.getString("chuanzhen"));
            kehu.setYouxiang(e.getString("youxiang"));
            kehuList.add(kehu);
         }

         e.close();
      } catch (Exception var9) {
         var9.printStackTrace();
      }

      mydb.closed();
      req.setAttribute("kehuList", kehuList);
      req.getRequestDispatcher("admin/kehu/kehuMana.jsp").forward(req, res);
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
