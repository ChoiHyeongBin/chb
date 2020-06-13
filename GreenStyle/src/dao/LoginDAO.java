package dao;

import java.sql.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import vo.MemberInfo;
import static db.JdbcUtil.*;

public class LoginDAO {
	private static LoginDAO loginDAO;
	// ���� ���� �ƴ� �ϳ��� �����ϰ� �ϱ� ���� static
	private Connection conn;
	private LoginDAO() {}
	
	public static LoginDAO getInstance() {
	// LoginDAO�� �ν��Ͻ��� �������ִ� �޼ҵ�� �ϳ��� �ν��Ͻ��� �����ϰ� ��
		if (loginDAO == null) {
			loginDAO = new LoginDAO();
			// ���ο� �ν��Ͻ��� ����
		}
		
		return loginDAO;
	}
	
	public void setConnection(Connection conn) {
	// LoginDAO���� ����� ���ؼ� ��ü�� �����ϴ� �޼ҵ�
		this.conn = conn;
	}
	
	public MemberInfo getLoginMember(String uid, String pwd) {
	// �Է¹��� ���̵�� ��й�ȣ�� DB�� ȸ�����̺� ���� �ϴ��� �˻��ϴ� �޼ҵ�
		MemberInfo loginMember = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		String sql = "select l.*, a.ma_zip, a.ma_addr1, a.ma_addr2 from t_member_list l, t_member_addr a where l.ml_isrun = 'y'" +
				" and l.ml_id = '" + uid + "' and l.ml_pwd = '"+ pwd +"' and l.ml_id = a.ml_id";
		System.out.println(sql);
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if (rs.next()) {	// �����Ͱ� ������
				loginMember = new MemberInfo();
				loginMember.setMl_num(rs.getInt("ml_num"));
				loginMember.setMl_id(rs.getString("ml_id"));
				loginMember.setMl_pwd(rs.getString("ml_pwd"));
				loginMember.setMl_name(rs.getString("ml_name"));
				loginMember.setMl_birth(rs.getString("ml_birth"));
				loginMember.setMl_phone(rs.getString("ml_phone"));
				loginMember.setMl_email(rs.getString("ml_email"));
				loginMember.setMl_point(rs.getString("ml_point"));
				loginMember.setMl_date(rs.getString("ml_date"));
				loginMember.setMl_lastlogin(rs.getString("ml_lastlogin"));
				loginMember.setMa_zip(rs.getString("ma_zip"));
				loginMember.setMa_addr1(rs.getString("ma_addr1"));
				loginMember.setMa_addr2(rs.getString("ma_addr2"));
				
			}
		} catch(Exception e) {
			System.out.println("getLoginMember() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			try {
				close(rs);
				close(stmt);
				
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
		
		return loginMember;
	}

	
	
	public int memberLogin(HttpServletRequest request) {
	      
	      int result = 0;	
	      Statement stmt = null;
	      
	      try {
	    	  HttpSession session = request.getSession();
	    	  request.setCharacterEncoding("utf-8");
	    	  MemberInfo memberInfo = (MemberInfo)session.getAttribute("memberInfo");
	    	  // request��ü�� ��뿡�� ����ó���� �ʿ��ϹǷ� try�� �ȿ��� �۾�
	    	  String uid = request.getParameter("uid");
	    	  System.out.println(uid);
	    	  // ȸ������ ������ ������Ʈ������ where���� �������� ����ϱ� ���� id
	    	  String sql = "update t_member_list set ml_lastlogin = now() where ml_id = '" + uid + "';";
	    	  System.out.println(sql);
	    	  
		      stmt = conn.createStatement();
		      result = stmt.executeUpdate(sql);
		      
	      } catch (Exception e) {
		         System.out.println("memberLogin() �޼ҵ� ����");
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
}
