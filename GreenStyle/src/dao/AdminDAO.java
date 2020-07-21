package dao;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.ArrayList;
import javax.servlet.http.*;
import javax.servlet.ServletException;
import static db.JdbcUtil.*;
import vo.*;

public class AdminDAO {
	private static AdminDAO adminDAO;
	// ���� ���� �ƴ� �ϳ��� �����ϰ� �ϱ� ���� static
	private Connection conn;
	private AdminDAO() {}
	
	public static AdminDAO getInstance() {
	// LoginDAO�� �ν��Ͻ��� �������ִ� �޼ҵ�� �ϳ��� �ν��Ͻ��� �����ϰ� ��
		if (adminDAO == null) {
			adminDAO = new AdminDAO();
			// ���ο� �ν��Ͻ��� ����
		}
		
		return adminDAO;
	}
	
	public void setConnection(Connection conn) {
	// LoginDAO���� ����� ���ؼ� ��ü�� �����ϴ� �޼ҵ�
		this.conn = conn;
	}
	
	public AdminInfo main(String uid, String pwd) {
		// �Է¹��� ���̵�� ��й�ȣ�� DB�� ȸ�����̺� ���� �ϴ��� �˻��ϴ� �޼ҵ�
		AdminInfo main = null;
			Statement stmt = null;
			ResultSet rs = null;
			
			String sql = "select * from t_admin_list where al_id = '" + uid + "' and al_pwd = '" + pwd + "' and al_isrun = 'y' ";
			System.out.println("main SQL : " + sql);
			
			try {
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				
				if (rs.next()) {	// �����Ͱ� ������
					main = new AdminInfo();
					main.setAl_num(rs.getInt("al_num"));
					main.setAl_id(rs.getString("al_id"));
					main.setAl_pwd(rs.getString("al_pwd"));
					main.setAl_name(rs.getString("al_name"));
					main.setAl_phone(rs.getString("al_phone"));
					main.setAl_email(rs.getString("al_email"));
					main.setAl_isrun(rs.getString("al_isrun"));
					main.setAl_date(rs.getString("al_date"));
					main.setReg_al_num(rs.getInt("reg_al_num"));
					
				}
			} catch(Exception e) {
				System.out.println("main() �޼ҵ� ����");
				e.printStackTrace();
			} finally {
				try {
					close(rs);
					close(stmt);
					
				} catch(Exception e) {
					e.printStackTrace();
				}
				
			}
			
			
			return main;
		}

}
