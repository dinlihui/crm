package com.service;

import com.dao.DB;
import java.sql.ResultSet;

public class liuService {

   public static String getKefangleixingName(int id) {
      String name = "";
      String sql = "select * from t_kefangleixing where id=" + id;
      Object[] params = new Object[0];
      DB mydb = new DB();

      try {
         mydb.doPstm(sql, params);
         ResultSet e = mydb.getRs();
         e.next();
         name = e.getString("name");
         e.close();
      } catch (Exception var6) {
         var6.printStackTrace();
      }

      mydb.closed();
      return name;
   }

   public static String getKefangFangjianhao(int id) {
      String fangjianhao = "";
      String sql = "select * from t_kefang where del=\'no\' and id=" + id;
      Object[] params = new Object[0];
      DB mydb = new DB();

      try {
         mydb.doPstm(sql, params);
         ResultSet e = mydb.getRs();
         e.next();
         fangjianhao = e.getString("fangjianhao");
         e.close();
      } catch (Exception var6) {
         var6.printStackTrace();
      }

      mydb.closed();
      return fangjianhao;
   }
}
