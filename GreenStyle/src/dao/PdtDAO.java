package dao;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import javax.servlet.http.*;
import javax.servlet.ServletException;
import static db.JdbcUtil.*;
import vo.*;

public class PdtDAO {
	private static PdtDAO pdtDAO;
	   private Connection conn;
	   
	   private PdtDAO() {}   
	      
	   public static PdtDAO getInstance() {
	      if (pdtDAO == null) {   
	    	  pdtDAO = new PdtDAO();
	      }
	      
	      return pdtDAO;
	   }
	   
	   public void setConnection(Connection conn) {
	      this.conn = conn;
	   }
	   
	// ��ǰ�� ����� ArrayList ���·� �����ϴ� �޼ҵ�
	   public ArrayList<PdtInfo> getPdtList(String where, int cpage, int limit) {
		   ArrayList<PdtInfo> pdtList = new ArrayList<PdtInfo>();
		   Statement stmt = null;
		   ResultSet rs = null;
		   PdtInfo pdtInfo = null;
		   
		   try {
			   int start = (cpage - 1) * limit;
			   String sql = "select * from t_product_list ";
			   sql += " where pl_isview = 'y' " + where;
			   sql += " limit " + start + ", " + limit;
			   stmt = conn.createStatement();
			   rs = stmt.executeQuery(sql);
			   while (rs.next()) {
					   pdtInfo = new PdtInfo();
					   // �ϳ��� �Խñ� �����͸� ��� ���� PdtInfo �ν��Ͻ� ����				
					   pdtInfo.setPl_num(rs.getInt("pl_num"));
					   pdtInfo.setPl_id(rs.getString("pl_id"));
					   pdtInfo.setPl_name(rs.getString("pl_name"));
					   pdtInfo.setPl_price(rs.getInt("pl_price"));
					   pdtInfo.setPl_img1(rs.getString("pl_img1"));
					   pdtInfo.setPl_img2(rs.getString("pl_img2"));
					   pdtInfo.setPl_stock(rs.getInt("pl_stock"));
					   pdtInfo.setCs_name(rs.getString("cs_name"));
					   pdtInfo.setCs_id(rs.getString("cs_id"));
					   pdtInfo.setPl_optsize(rs.getString("pl_optsize"));
					   pdtInfo.setPl_optcolor(rs.getString("pl_optcolor"));
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
					   pdtInfo.setPl_img4(rs.getString("pl_img4"));
					   pdtInfo.setPl_img5(rs.getString("pl_img5"));
					   pdtInfo.setPl_optsize(rs.getString("pl_optsize"));
					   pdtInfo.setPl_optcolor(rs.getString("pl_optcolor"));
					   pdtInfo.setPl_stock(rs.getInt("pl_stock"));
					   pdtInfo.setPl_issell(rs.getString("pl_issell"));
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
	   
	// ��ǰ�� �� �˻��Ǵ� ��ü ������ �����ϴ� �޼ҵ�
		public int getPdtCount( String where) {
			int rcount = 0;
			Statement stmt = null;
			ResultSet rs = null;
			try {
				String sql = "select count(*) from t_product_list where 1=1 " + where;
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				rs.next();	// if���� ������� �ʴ� ���� count()����̹Ƿ� ������ ������� �����ϹǷ�
				rcount = rs.getInt(1);
			} catch(Exception e) {
				System.out.println("getListCount() �޼ҵ忡�� ���� �߻�");
			} finally {
				close(rs);
				close(stmt);
			}

			return rcount;
		}
}
