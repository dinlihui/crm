package com.service;

import com.dao.DB;
import com.orm.Kefang;
import com.orm.Kefangleixing;
import com.orm.TAdmin;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

public class loginService {

   public String login(String userName, String userPw, int userType) {
      System.out.println("userType" + userType);

      try {
         Thread.sleep(700L);
      } catch (InterruptedException var17) {
         var17.printStackTrace();
      }

      String result = "no";
      if(userType == 0) {
         String sql = "select * from t_admin where userName=? and userPw=?";
         Object[] params = new Object[]{userName, userPw};
         DB mydb = new DB();
         mydb.doPstm(sql, params);

         try {
            ResultSet e = mydb.getRs();
            boolean mark = e != null && e.next();
            if(!mark) {
               result = "no";
            } else {
               result = "yes";
               TAdmin admin = new TAdmin();
               admin.setUserId(e.getInt("userId"));
               admin.setUserName(e.getString("userName"));
               admin.setUserPw(e.getString("userPw"));
               WebContext ctx = WebContextFactory.get();
               HttpSession session = ctx.getSession();
               session.setAttribute("userType", Integer.valueOf(0));
               session.setAttribute("admin", admin);
            }

            e.close();
         } catch (SQLException var18) {
            System.out.println("ok");
            var18.printStackTrace();
         } finally {
            mydb.closed();
         }
      }

      return result;
   }

   public String adminPwEdit(String userPwNew) {
      System.out.println("DDDD");

      try {
         Thread.sleep(700L);
      } catch (InterruptedException var8) {
         var8.printStackTrace();
      }

      WebContext ctx = WebContextFactory.get();
      HttpSession session = ctx.getSession();
      TAdmin admin = (TAdmin)session.getAttribute("admin");
      String sql = "update t_admin set userPw=? where userId=?";
      Object[] params = new Object[]{userPwNew, Integer.valueOf(admin.getUserId())};
      DB mydb = new DB();
      mydb.doPstm(sql, params);
      return "yes";
   }

   public List kefangleixingSelect() {
      ArrayList kefangleixingList = new ArrayList();
      String sql = "select * from t_kefangleixing where del=\'no\'";
      Object[] params = new Object[0];
      DB mydb = new DB();

      try {
         mydb.doPstm(sql, params);
         ResultSet e = mydb.getRs();

         while(e.next()) {
            Kefangleixing kefangleixing = new Kefangleixing();
            kefangleixing.setId(e.getInt("id"));
            kefangleixing.setName(e.getString("name"));
            kefangleixingList.add(kefangleixing);
         }

         e.close();
      } catch (Exception var7) {
         var7.printStackTrace();
      }

      mydb.closed();
      return kefangleixingList;
   }

   public List kefangSelect() {
      ArrayList kefangList = new ArrayList();
      String sql = "select * from t_kefang where del=\'no\'";
      Object[] params = new Object[0];
      DB mydb = new DB();

      try {
         mydb.doPstm(sql, params);
         ResultSet e = mydb.getRs();

         while(e.next()) {
            Kefang kefang = new Kefang();
            kefang.setId(e.getInt("id"));
            kefang.setFangjianhao(e.getString("fangjianhao"));
            kefangList.add(kefang);
         }

         e.close();
      } catch (Exception var7) {
         var7.printStackTrace();
      }

      mydb.closed();
      return kefangList;
   }
}
