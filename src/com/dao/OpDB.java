package com.dao;

import com.dao.DB;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OpDB {

   private DB mydb = new DB();


   public int OpUpdate(String sql, Object[] params) {
      int i = -1;
      this.mydb.doPstm(sql, params);

      try {
         i = this.mydb.getCount();
      } catch (SQLException var8) {
         System.out.println("执行OpUpdate()方法失败！(更新数据库)");
         var8.printStackTrace();
      } finally {
         this.mydb.closed();
      }

      return i;
   }

   public boolean LogOn(String sql, Object[] params) {
      this.mydb.doPstm(sql, params);

      try {
         ResultSet e = this.mydb.getRs();
         boolean mark = e != null && e.next();
         e.close();
         boolean var6 = mark;
         return var6;
      } catch (SQLException var9) {
         System.out.println("登录失败！");
         var9.printStackTrace();
      } finally {
         this.mydb.closed();
      }

      return false;
   }
}
