package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DB {
	
	

			
	

   private Connection con;
   private PreparedStatement pstm;
   private String user = "root";
   private String password = "123456";
   private String className = "com.mysql.jdbc.Driver";
   private String url = "jdbc:mysql://localhost:3306/db_jiudian?characterEncoding=utf-8";


   public DB() {
      try {
         Class.forName(this.className);
      } catch (ClassNotFoundException var2) {
         System.out.println("成功");
         var2.printStackTrace();
      }

   }

   public Connection getCon() {
      try {
         this.con = DriverManager.getConnection(this.url, this.user, this.password);
      } catch (SQLException var2) {
         System.out.println("成功");
         this.con = null;
         var2.printStackTrace();
      }

      return this.con;
   }

   public void doPstm(String sql, Object[] params) {
      if(sql != null && !sql.equals("")) {
         if(params == null) {
            params = new Object[0];
         }

         this.getCon();
         if(this.con != null) {
            try {
               System.out.println(sql);
               this.pstm = this.con.prepareStatement(sql, 1004, 1007);

               for(int e = 0; e < params.length; ++e) {
                  this.pstm.setObject(e + 1, params[e]);
                  for (Object param : params) {
                     System.out.println(param);
                  }
               }
               System.out.println(1);
               this.pstm.execute();
            } catch (SQLException var4) {
               System.out.println("doPstm()");
               var4.printStackTrace();
            }
         }
      }

   }

   public ResultSet getRs() throws SQLException {
      return this.pstm.getResultSet();
   }

   public int getCount() throws SQLException {
      return this.pstm.getUpdateCount();
   }

   public void closed() {
      try {
         if(this.pstm != null) {
            this.pstm.close();
         }
      } catch (SQLException var3) {
         System.out.println("成功");
         var3.printStackTrace();
      }

      try {
         if(this.con != null) {
            this.con.close();
         }
      } catch (SQLException var2) {
         System.out.println("成功");
         var2.printStackTrace();
      }
   

   }
}
