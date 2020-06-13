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

public class NologOrdService {

	public ArrayList<CartInfo> getnologOrdList(HttpServletRequest request) {
		System.out.println("OrdService�� ��ǰ �ٷ� ����");
		ArrayList<CartInfo> NologOrdList = null;
		NologOrdDAO nologordDAO = NologOrdDAO.getInstance();
		Connection conn = getConnection();
		nologordDAO.setConnection(conn);
		NologOrdList = nologordDAO.getnologOrdList(request);
		close(conn);
		return NologOrdList;
	}

	// ��ǰ ���Ÿ� ó���ϴ� �޼ҵ�
	public int nologordProcess(HttpServletRequest request) {
		int result = 0;
		NologOrdDAO nologordDAO = NologOrdDAO.getInstance();
		Connection conn = getConnection();
		nologordDAO.setConnection(conn);
		result = nologordDAO.nologordProcess(request);
		if (result >= 1)	commit(conn);
		else 				rollback(conn);
		close(conn);
		return result;
	}
	
	// �ֹ������ ���ڵ� ������ �����ϴ� �޼ҵ�
	public int getnologOrdListCount(String olbuyer) {
		int rcount = 0;
		NologOrdDAO nologordDAO = NologOrdDAO.getInstance();
		Connection conn = getConnection();
		nologordDAO.setConnection(conn);
		rcount = nologordDAO.getnologOrdListCount(olbuyer);
		close(conn);
		
		return rcount;
	}
	
	// ȸ���� ������ ��ǰ(��)�� ����� ArrayList������ �����ϴ� �޼ҵ�
	public ArrayList<OrdInfo> getOrdList(HttpServletRequest request, String olbuyer, int cpage, int limit) {
		ArrayList<OrdInfo> ordList = null;
		NologOrdDAO nologordDAO = NologOrdDAO.getInstance();
		Connection conn = getConnection();
		nologordDAO.setConnection(conn);
		ordList = nologordDAO.getOrdList(request, olbuyer, cpage, limit);
		close(conn);
		
		return ordList;
	}
	
	
	// ȸ���� ������ ��ǰ(��)�� ����� ArrayList������ �����ϴ� �޼ҵ�
		public ArrayList<OrdInfo> getNologOrdList(HttpServletRequest request, String olbuyer, String olids) {
			System.out.println("getNologOrdList ����");
			ArrayList<OrdInfo> nologordList = null;
			NologOrdDAO nologordDAO = NologOrdDAO.getInstance();
			Connection conn = getConnection();
			nologordDAO.setConnection(conn);
			nologordList = nologordDAO.getNologOrdList(request, olbuyer, olids);
			System.out.println("getNologOrdListDAO ���� ����");
			close(conn);
			
			return nologordList;
		}
			
	// ȸ���� ������ Ư�� �ֹ��� �������� OrdInfo������ �����ϴ� �޼ҵ�
	public OrdInfo getOrdInfo(HttpServletRequest request, String olid) {
		System.out.println("getOrdinfo ����");
		OrdInfo ordInfo = null;
		NologOrdDAO nologordDAO = NologOrdDAO.getInstance();
		Connection conn = getConnection();
		nologordDAO.setConnection(conn);
		ordInfo = nologordDAO.getOrdInfo(request, olid);
		System.out.println("getOrdinfo ���� ����");
		close(conn);
		
		return ordInfo;
	}
	
	// ���� ��Ҹ� ó���ϴ� �޼ҵ�
	public int orderCancel(HttpServletRequest request) {
		System.out.println("orderCancel ����");
		int result = 0;
		NologOrdDAO nologordDAO = NologOrdDAO.getInstance();
		Connection conn = getConnection();
		nologordDAO.setConnection(conn);
		result = nologordDAO.orderCancel(request);
		
		if (result >= 1)    commit(conn); 
		else 				rollback(conn);
		close(conn);
		return result;
	}
	
	
	
	
}
