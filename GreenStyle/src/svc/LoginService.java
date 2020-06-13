package svc;

import static db.JdbcUtil.*;	// JdbcUtil�� static�޼ҵ���� �ٷ� ��밡��
import java.sql.*;

import javax.servlet.http.HttpServletRequest;

import dao.LoginDAO;
import dao.MemberDAO;
import vo.MemberInfo;

// �α��� ���� ����Ͻ� ������ ó���ϴ� ����
public class LoginService {
	public MemberInfo getLoginMember(String uid, String pwd) {
		LoginDAO loginDAO = LoginDAO.getInstance();
		// LoginDAO ������ loginDAO �ν��Ͻ� ����
		Connection conn = getConnection();
		// JdbcUtilŬ������ static���� import�Ͽ����Ƿ� �ν��Ͻ� ���� �޼ҵ�ȣ�� ����
		loginDAO.setConnection(conn);
		// loginDAO �ν��Ͻ����� ����� Connection��ü ����
		MemberInfo loginMember = loginDAO.getLoginMember(uid, pwd);
		// uid�� pwd�� �̿��Ͽ� ������ ���� �� ����� �޾ƿ�
		close(conn);
		// �����۾��� �������Ƿ� Connection��ü�� �ݾ���
		
		return loginMember;
	}
		
	public int memberLogin(HttpServletRequest request) {
		int result = 0;
		LoginDAO loginDAO = LoginDAO.getInstance();
		// DAOŬ������ �ν��Ͻ� ���� �� ����(�����۾��� ó���ϱ� ���� �ν��Ͻ�)
		Connection conn = getConnection();
		loginDAO.setConnection(conn);
		
		result = loginDAO.memberLogin(request);
		
		
		if (result == 1) { commit(conn);
		} else {				
			rollback(conn);		
		}
		close(conn);
		
		return result;
	}
	
}