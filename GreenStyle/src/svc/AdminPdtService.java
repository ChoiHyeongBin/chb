package svc;

import static db.JdbcUtil.*;
import java.sql.Connection;
import java.util.*; 
import javax.servlet.http.*;
import dao.*;
import svc.*;
import vo.*;


public class AdminPdtService {

	public ArrayList<PdtInfo> getAdminPdtList(HttpServletRequest request, String where, int cpage, int limit) {
		System.out.println("getAdminPdtList ����");
		ArrayList<PdtInfo> adminpdtList = new ArrayList<PdtInfo>();
		AdminPdtDAO adminpdtDAO = AdminPdtDAO.getInstance();
		Connection conn = getConnection();
		adminpdtDAO.setConnection(conn);
		adminpdtList = adminpdtDAO.getAdminPdtList(request, where, cpage, limit);
		System.out.println("getAdminPdtList ��������");
		close(conn);
		return adminpdtList;
	}
	
	// ��ǰ�� ��� ������ �����ϴ� �޼ҵ�
	public int getPdtCount(String where) {
		System.out.println("getPdtCount ����");
		int rcount = 0;
		AdminPdtDAO adminpdtDAO = AdminPdtDAO.getInstance();
		Connection conn = getConnection();
		adminpdtDAO.setConnection(conn);
		rcount = adminpdtDAO.getPdtCount(where);
		System.out.println("getPdtCount ��������");
		close(conn);
		return rcount;
	}
	
	// �ϳ��� ��ǰ ������ PdtInfo�� �ν��Ͻ��� ���Ϲ޴� �޼ҵ�
	public PdtInfo getPdt(String plid) {
		PdtInfo pdtInfo = new PdtInfo();
		System.out.println("plid " + plid);
		AdminPdtDAO adminpdtDAO = AdminPdtDAO.getInstance();
		Connection conn = getConnection();
		adminpdtDAO.setConnection(conn);
		pdtInfo = adminpdtDAO.getPdt(plid);
		close(conn);
		return pdtInfo;
	}
	
	// �������� ��ϰ���� �۹�ȣ�� String������ �����ϴ� �޼ҵ�
	public String insertPdt(HttpServletRequest request) {
		AdminPdtDAO adminpdtDAO = AdminPdtDAO.getInstance();
		Connection conn = getConnection();
		adminpdtDAO.setConnection(conn);
		String plResult = adminpdtDAO.insertPdt(request);
		int result = Integer.parseInt(plResult.substring(0, plResult.indexOf(':')));
		if (result == 1) { commit(conn); }
		else { rollback(conn); }
		close(conn);
		return plResult;
	}
	// �������� �Խñۿ� ���� ��������� int������ �����ϴ� �޼ҵ�
	public int updatePdt(HttpServletRequest request) {
		System.out.println("updatePdtService ����");
		AdminPdtDAO adminpdtDAO = AdminPdtDAO.getInstance();
		Connection conn = getConnection();
		adminpdtDAO.setConnection(conn);
		int result = adminpdtDAO.updatePdt(request);
		if (result == 1) { commit(conn); }
		else { rollback(conn); }
		close(conn);
		return result;
	}

	// �������� �Խñ��� ������ �� ����� int������ �����ϴ� �޼ҵ�
	public int deletePdt(HttpServletRequest request) {
		AdminPdtDAO adminpdtDAO = AdminPdtDAO.getInstance();
		Connection conn = getConnection();
		adminpdtDAO.setConnection(conn);
		int result = adminpdtDAO.deletePdt(request);
		if (result == 1) { commit(conn); }
		else { rollback(conn); }
		close(conn);
		return result;
	}
}
