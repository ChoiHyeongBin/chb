package svc;

import static db.JdbcUtil.*;
import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import dao.*;

public class MemberService {
	public int memberUpdate(HttpServletRequest request) {
		int result = 0;
		MemberDAO memberDAO = MemberDAO.getInstance();
		// DAOŬ������ �ν��Ͻ� ���� �� ����(�����۾��� ó���ϱ� ���� �ν��Ͻ�)
		Connection conn = getConnection();
		memberDAO.setConnection(conn);
		
		result = memberDAO.memberUpdate(request);
		
		
		if (result == 1) { commit(conn);
		} else {				
			rollback(conn);		
		}
		close(conn);
		
		return result;
	}
	
	public int memberDelete(HttpServletRequest request) {
		int result = 0;
		MemberDAO memberDAO = MemberDAO.getInstance();
		// DAOŬ������ �ν��Ͻ� ���� �� ����(�����۾��� ó���ϱ� ���� �ν��Ͻ�)
		Connection conn = getConnection();
		memberDAO.setConnection(conn);
		
		result = memberDAO.memberDelete(request);
		
		
		if (result == 1) { commit(conn);
		} else {				
			rollback(conn);		
		}
		close(conn);
		
		return result;
	}
}
