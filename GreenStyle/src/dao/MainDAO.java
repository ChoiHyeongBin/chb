package dao;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import javax.servlet.http.*;
import javax.servlet.ServletException;
import static db.JdbcUtil.*;
import vo.*;


public class MainDAO {
	private static MainDAO mainDAO;
	   private Connection conn;
	   
	   private MainDAO() {}   
	      
	   public static MainDAO getInstance() {
	      if (mainDAO == null) {   
	    	  mainDAO = new MainDAO();
	      }
	      
	      return mainDAO;
	   }
	   
	   public void setConnection(Connection conn) {
	      this.conn = conn;
	   }
	// ��ǰ�� ����� ArrayList ���·� �����ϴ� �޼ҵ�
	   public ArrayList<PdtInfo> getPdtList() {
		   ArrayList<PdtInfo> pdtList = new ArrayList<PdtInfo>();
		   Statement stmt = null;
		   ResultSet rs = null;
		   PdtInfo pdtInfo = null;
		   
		   try {
			   String sql = "select * from t_product_list where pl_isview = 'y' and pl_new = 'y' ";
			   stmt = conn.createStatement();
			   rs = stmt.executeQuery(sql);
			   
			   while (rs.next()) {
					   pdtInfo = new PdtInfo();
					   // �ϳ��� �Խñ� �����͸� ��� ���� PdtInfo �ν��Ͻ� ����
					   
					   pdtInfo.setPl_num(rs.getInt("pl_num"));
					   pdtInfo.setPl_id(rs.getString("pl_id"));
					   pdtInfo.setCs_id(rs.getString("cs_id"));
					   pdtInfo.setPl_name(rs.getString("pl_name"));
					   pdtInfo.setPl_price(rs.getInt("pl_price"));
					   pdtInfo.setPl_img1(rs.getString("pl_img1"));
					   pdtInfo.setPl_img2(rs.getString("pl_img2"));
					   pdtInfo.setPl_stock(rs.getInt("pl_stock"));
					   pdtInfo.setPl_optsize(rs.getString("pl_optsize"));
					   pdtInfo.setPl_optcolor(rs.getString("pl_optcolor"));
					   pdtInfo.setPl_new(rs.getString("pl_new"));
					   // ������ �ν��Ͻ��� ������ ä���
					   
					   pdtList.add(pdtInfo);
					   // ��ǰ ����� ���� ArrayList�� ������ �ν��Ͻ��� ����
			   }
			   
		   } catch(Exception e) {
			   System.out.println("getPdtList() �޼ҵ忡�� ���� �߻�");
		   } finally {
			   close(rs);
			   close(stmt);
		   }
		   
		   return pdtList;
	   }
	   
	   // �ϳ��� ��ǰ������ PdtInfo�� �ν��Ͻ��� ���Ϲ޴� �޼ҵ�
	   public PdtInfo getPdtInfo(String plid) {
			PdtInfo pdtInfo = null;
			
			Statement stmt = null;	// ������ ���۽����ִ� ��ü
			   ResultSet rs = null;
			   
			   try {
				   String sql = "select p.* from t_product_list p " + 
				   	"where p.pl_isview ='y' and p.pl_id = '" + plid + "' ";
				   stmt = conn.createStatement();
				   rs = stmt.executeQuery(sql);
				   if (rs.next()) {
					   pdtInfo = new PdtInfo();
					   // �ϳ��� �Խñ� �����͸� ��� ���� PdtInfo �ν��Ͻ� ����
					   pdtInfo.setPl_num(rs.getInt("pl_num"));
					   pdtInfo.setPl_id(rs.getString("pl_id"));
					   pdtInfo.setPl_name(rs.getString("pl_name"));
					   pdtInfo.setPl_price(rs.getInt("pl_price"));
					   pdtInfo.setCb_id(rs.getString("cb_id"));
					   pdtInfo.setCs_id(rs.getString("cs_id"));
					   pdtInfo.setPl_img1(rs.getString("pl_img1"));
					   pdtInfo.setPl_img2(rs.getString("pl_img2"));
					   pdtInfo.setPl_img3(rs.getString("pl_img3"));
					   pdtInfo.setPl_optsize(rs.getString("pl_optsize"));
					   pdtInfo.setPl_optcolor(rs.getString("pl_optcolor"));
					   pdtInfo.setPl_stock(rs.getInt("pl_stock"));
					   // ������ �ν��Ͻ��� ������ ä���
				   }
			   } catch(Exception e) {
				   System.out.println("getPdtInfo() �޼ҵ忡�� ���� �߻�");
			   } finally {
				   close(rs);
				   close(stmt);
			   }
			
			return pdtInfo;
	   }
}
