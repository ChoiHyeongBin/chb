package svc;

import static db.JdbcUtil.*;	// JdbcUtil�� static�޼ҵ���� �ٷ� ��밡��
import java.sql.*;

import javax.servlet.http.HttpServletRequest;

import dao.*;
import dao.*;
import vo.*;

public class AdminService {
	// �α��� ���� ����Ͻ� ������ ó���ϴ� ����
	public AdminInfo main(String uid, String pwd) {
		System.out.println("���� ���� ����");
		AdminDAO adminDAO = AdminDAO.getInstance();
		// AdminDAO ������ AdminDAO �ν��Ͻ� ����
		Connection conn = getConnection();
		// JdbcUtilŬ������ static���� import�Ͽ����Ƿ� �ν��Ͻ� ���� �޼ҵ�ȣ�� ����
		adminDAO.setConnection(conn);
		// adminDAO �ν��Ͻ����� ����� Connection��ü ����
		AdminInfo main = adminDAO.main(uid, pwd);
		// uid�� pwd�� �̿��Ͽ� ������ ���� �� ����� �޾ƿ�
		close(conn);
		// �����۾��� �������Ƿ� Connection��ü�� �ݾ���
		
		return main;
	}

}
