package com.action;

import com.dao.DB;
import com.orm.Kefangruzhu;
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

public class kefangruzhu_servlet extends HttpServlet {

   public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      String type = req.getParameter("type");
      if(type.endsWith("kefangruzhuMana")) {
         this.kefangruzhuMana(req, res);
      }

      if(type.endsWith("kefangruzhuAdd")) {
         this.kefangruzhuAdd(req, res);
      }

      if(type.endsWith("kefangruzhuDel")) {
         this.kefangruzhuDel(req, res);
      }

   }

   public void kefangruzhuAdd(HttpServletRequest req, HttpServletResponse res) {
      int kefang_id = Integer.parseInt(req.getParameter("kefang_id"));
      String shijian = req.getParameter("shijian");
      String kehuname = req.getParameter("kehuname");
      String kehutel = req.getParameter("kehutel");
      String tianshu = req.getParameter("tianshu");
      String xiaofeijine = req.getParameter("xiaofeijine");
      String del = "no";
      String sql = "insert into t_kefangruzhu(kefang_id,shijian,kehuname,kehutel,tianshu,xiaofeijine,del) values(?,?,?,?,?,?,?)";
      Object[] params = new Object[]{Integer.valueOf(kefang_id), shijian, kehuname, kehutel, tianshu, xiaofeijine, del};
      DB mydb = new DB();
      mydb.doPstm(sql, params);
      mydb.closed();
      req.setAttribute("message", "操作成功");
      req.setAttribute("path", "kefangruzhu?type=kefangruzhuMana");
      String targetURL = "/common/success.jsp";
      this.dispatch(targetURL, req, res);
   }

   public void kefangruzhuDel(HttpServletRequest req, HttpServletResponse res) {
      String sql = "update t_kefangruzhu set del=\'yes\' where id=" + Integer.parseInt(req.getParameter("id"));
      Object[] params = new Object[0];
      DB mydb = new DB();
      mydb.doPstm(sql, params);
      mydb.closed();
      req.setAttribute("message", "操作成功");
      req.setAttribute("path", "kefangruzhu?type=kefangruzhuMana");
      String targetURL = "/common/success.jsp";
      this.dispatch(targetURL, req, res);
   }

   public void kefangruzhuMana(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      ArrayList kefangruzhuList = new ArrayList();
      String sql = "select * from t_kefangruzhu where del=\'no\'";
      Object[] params = new Object[0];
      DB mydb = new DB();

      try {
         mydb.doPstm(sql, params);
         ResultSet e = mydb.getRs();

         while(e.next()) {
            Kefangruzhu kefangruzhu = new Kefangruzhu();
            kefangruzhu.setId(e.getInt("id"));
            kefangruzhu.setKefang_id(e.getInt("kefang_id"));
            kefangruzhu.setShijian(e.getString("shijian"));
            kefangruzhu.setKehuname(e.getString("kehuname"));
            kefangruzhu.setKehutel(e.getString("kehutel"));
            kefangruzhu.setTianshu(e.getString("tianshu"));
            kefangruzhu.setXiaofeijine(e.getInt("xiaofeijine"));
            kefangruzhu.setKefang_fanjianhao(liuService.getKefangFangjianhao(e.getInt("kefang_id")));
            kefangruzhuList.add(kefangruzhu);
         }

         e.close();
      } catch (Exception var9) {
         var9.printStackTrace();
      }

      mydb.closed();
      req.setAttribute("kefangruzhuList", kefangruzhuList);
      req.getRequestDispatcher("admin/kefangruzhu/kefangruzhuMana.jsp").forward(req, res);
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
