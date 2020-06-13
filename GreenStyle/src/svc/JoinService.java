package svc;

import static db.JdbcUtil.*;
import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import dao.JoinDAO;

// ���� �۾�(����Ͻ� ����)�� ó���ϴ� Ŭ����
public class JoinService {
	public int setMemberJoin(HttpServletRequest request) {
		int result = 0;
		JoinDAO joinDAO = JoinDAO.getInstance();
		// DAOŬ������ �ν��Ͻ� ���� �� ����(�����۾��� ó���ϱ� ���� �ν��Ͻ�)
		Connection conn = getConnection();
		joinDAO.setConnection(conn);
		
		result = joinDAO.setMemberJoin(request);
		// ȸ������ ������ �۾��ϱ� ���� setMemberJoin() �޼ҵ带 ȣ��
		// ��������δ� �����۾�(insert) �� ������ ���� ���ڵ��� ������ �޾ƿ�
		// ������� �� ���� ȸ�������̱� ������ 1�̾�� ��
		
		if (result == 1) {		// ȸ������ ������
			commit(conn);		// ���� ����
		} else {				// ȸ������ ���н�
			rollback(conn);		// ���� ���
		}
		close(conn);
		
		return result;
	}
}
