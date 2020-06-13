package dao;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.ArrayList;
import javax.servlet.http.*;
import javax.servlet.ServletException;
import static db.JdbcUtil.*;
import vo.*;

public class AdminMemDAO {
	private static AdminMemDAO adminMemDAO;
	private Connection conn;
	
	private AdminMemDAO() {}	
	
	public static AdminMemDAO getInstance() {
		if (adminMemDAO == null) {	
			adminMemDAO = new AdminMemDAO();
		}
		
		return adminMemDAO;
	}
	
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	
	public ArrayList<MemberInfo> getMemList(String where, int cpage, int limit) {
		ArrayList<MemberInfo> memberList = new ArrayList<MemberInfo>();
		Statement stmt = null;
		ResultSet rs = null;
		MemberInfo mem = null;
		try {
			int start = (cpage - 1) * limit;
			String sql = "select * from t_member_list where 1=1 " + where;
			sql += " order by ml_num desc limit " + start + ", " + limit;
			System.out.println(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				mem = new MemberInfo();
				mem.setMl_num(rs.getInt("ml_num"));
				mem.setMl_id(rs.getString("ml_id"));
				mem.setMl_name(rs.getString("ml_name"));
				mem.setMl_birth(rs.getString("ml_birth"));
				mem.setMl_phone(rs.getString("ml_phone"));
				mem.setMl_email(rs.getString("ml_email"));
				mem.setMl_date(rs.getString("ml_date"));
				mem.setMl_lastlogin(rs.getString("ml_lastlogin"));
				mem.setMl_isrun(rs.getString("ml_isrun"));
				
				   
				// ������ �ν��Ͻ��� ������ ä���
				   
				memberList.add(mem);
				// ��ǰ ����� ���� ArrayList�� ������ �ν��Ͻ��� ����

			}
		} catch (Exception e) {
			System.out.println("getMemberList() �޼ҵ忡�� ���� �߻�");
			
		} finally {
			close(rs);
			close(stmt);
		}
		return memberList;
	}
	public int getListCount(String where) {
		int rcount = 0;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "select count(*) from t_member_list where 1=1 " + where;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			rs.next();	// if���� ������� �ʴ� ���� count()����̹Ƿ� ������ ������� �����ϹǷ�
			rcount = rs.getInt(1);
			System.out.println(sql);
		} catch (Exception e) {
			System.out.println("getListCount() �޼ҵ忡�� ���� �߻�");
		} finally {
			close(rs);
			close(stmt);
		}
		
		return rcount;
	} 
	
	 public int memberUpdate(HttpServletRequest request) {
	      
	      int result = 0;	
	      Statement stmt = null;
	      
	      
	      try {
	    	  request.setCharacterEncoding("utf-8");
	    	
	    	  // request��ü�� ��뿡�� ����ó���� �ʿ��ϹǷ� try�� �ȿ��� �۾�
	    	  String uid = request.getParameter("uid");
	    	  // ȸ������ ������ ������Ʈ������ where���� �������� ����ϱ� ���� id
	         
	         
	         String p1       = request.getParameter("p1");
	         String p2       = request.getParameter("p2");
	         String p3       = request.getParameter("p3");
	         String e1       = request.getParameter("e1").trim();
	         String e2       = request.getParameter("e2");
	         String status	 = request.getParameter("status");


	         String phone    = p1 + "-" + p2 + "-" + p3;
	         String email   = e1 + "@" + e2;
	         
	         
	         String sql = "update t_member_list set ";
	         
	         sql += "ml_phone = '" 		+ phone 	+ "', ";
	         sql += "ml_email = '" 		+ email 	+ "', ";
	         sql += "ml_isrun = '"		+ status	+ "' ";
	         sql += "where ml_id = '" 	+ uid 		+ "' ";
	         System.out.println(sql);

	         stmt = conn.createStatement();
	         result = stmt.executeUpdate(sql);

	      } catch (Exception e) {
	         System.out.println("memberUpdate(admin) �޼ҵ� ����");
	         e.printStackTrace();
	      } finally {
	         try {
	            close(stmt);
	         } catch (Exception e) {
	            e.printStackTrace();
	         } 
	      }
	      
	      return result;
	   }
	 
	 public MemberInfo getMember(int num) {
		 	MemberInfo memberInfo = null;
			// ������ �������� �� �����͸� �����ϱ� ���� �ν��Ͻ�
			Statement stmt = null;
			// DB�� ������ �����ִ� ��ü
			ResultSet rs = null;
			// �޾ƿ� ������ ��� ��ü
			
			try {
				String sql = "select * from t_member_list where ml_num = " + num;
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);	// ResultSet ����
				if (rs.next()) {
					memberInfo = new MemberInfo();
					// �ϳ��� �Խñ� �����͸� ��� ���� NoticeInfo �ν��Ͻ� ����
					
					memberInfo.setMl_num(rs.getInt("ml_num"));
					memberInfo.setMl_id(rs.getString("ml_id"));
					
					memberInfo.setMl_name(rs.getString("ml_name"));
					memberInfo.setMl_phone(rs.getString("ml_phone"));
					memberInfo.setMl_email(rs.getString("ml_email"));
					memberInfo.setMl_birth(rs.getString("ml_birth"));
					memberInfo.setMl_date(rs.getString("ml_date"));
					memberInfo.setMl_lastlogin(rs.getString("ml_lastlogin"));
					memberInfo.setMl_isrun(rs.getString("ml_isrun"));
					// ������ �ν��Ͻ��� ������ ä���
				}
			} catch(Exception e) {
				System.out.println("getMember() �޼ҵ忡�� ���� �߻�");
			} finally {
				close(rs);
				close(stmt);
			}
			
			return memberInfo;
		}
	 
}
