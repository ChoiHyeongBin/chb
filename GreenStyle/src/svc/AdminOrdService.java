package svc;

import static db.JdbcUtil.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import javax.servlet.http.*;
import dao.*;
import svc.*;
import vo.*;

public class AdminOrdService {
	public int getListCount(String where) {
		int rcount = 0;
		AdminOrdDAO adminOrdDAO = AdminOrdDAO.getInstance();
		Connection conn = getConnection();
		adminOrdDAO.setConnection(conn);
		rcount = adminOrdDAO.getListCount(where);
		close(conn);
		return rcount;
	}
	
	public ArrayList<OrdInfo> getOrdList(String where, int cpage, int limit) {
		ArrayList<OrdInfo> ordList = null;
		AdminOrdDAO adminOrdDAO = AdminOrdDAO.getInstance();
		Connection conn = getConnection();
		adminOrdDAO.setConnection(conn);
		ordList = adminOrdDAO.getOrdList(where, cpage, limit);
		close(conn);
		
		return ordList;
	}
	
	public ArrayList<OrdInfo> getOrdList2(String where, int cpage, int limit) {
		ArrayList<OrdInfo> ordList = null;
		AdminOrdDAO adminOrdDAO = AdminOrdDAO.getInstance();
		Connection conn = getConnection();
		adminOrdDAO.setConnection(conn);
		ordList = adminOrdDAO.getOrdList2(where, cpage, limit);
		close(conn);
		
		return ordList;
	}
	
	public OrdInfo getOrd(int num) {
		OrdInfo ordInfo = null;
		AdminOrdDAO adminOrdDAO = AdminOrdDAO.getInstance();
		Connection conn = getConnection();
		adminOrdDAO.setConnection(conn);
		ordInfo = adminOrdDAO.getOrd(num);
		close(conn);
		
		return ordInfo;
	}
	
	public int ordUpdate(HttpServletRequest request) {
		int result = 0;
		AdminOrdDAO adminOrdDAO = AdminOrdDAO.getInstance();
		// DAOŬ������ �ν��Ͻ� ���� �� ����(�����۾��� ó���ϱ� ���� �ν��Ͻ�)
		Connection conn = getConnection();
		adminOrdDAO.setConnection(conn);
		
		result = adminOrdDAO.ordUpdate(request);
		
		
		if (result == 1) { commit(conn);
		} else {				
			rollback(conn);		
		}
		close(conn);
		
		return result;
	}
	
	// ���� ��� ����
	public ArrayList<OrdInfo> getMainOrdList() {
		ArrayList<OrdInfo> ordList = null;
		AdminOrdDAO adminOrdDAO = AdminOrdDAO.getInstance();
		Connection conn = getConnection();
		adminOrdDAO.setConnection(conn);
		ordList = adminOrdDAO.getMainOrdList();
		close(conn);
		
		return ordList;
	}
}
