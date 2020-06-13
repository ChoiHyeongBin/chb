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

public class OrdService {
	// �����ڰ� ������ ��ǰ�� ���� ������ ��ٱ��Ͽ� ��� �޼ҵ�
	public int cartIn(HttpServletRequest request) {
		int result = 0;
		
		OrdDAO ordDAO = OrdDAO.getInstance();
		Connection conn = getConnection();
		ordDAO.setConnection(conn);
		result = ordDAO.cartIn(request);
		if (result == 1)	commit(conn);
		else				rollback(conn);
		close(conn);
		
		return result;
	}
	
	// �����ڰ� ������ ��ǰ�� ��ٱ��Ͽ��� �����ϴ� �޼ҵ�
	public int cartDel(HttpServletRequest request, String mlid) {
		int result = 0;
		
		OrdDAO ordDAO = OrdDAO.getInstance();
		Connection conn = getConnection();
		ordDAO.setConnection(conn);
		result = ordDAO.cartDel(request, mlid);
		if (result > 0)		commit(conn);	// delete �ÿ��� commit, rollback �ʿ�
		else				rollback(conn);
		close(conn);
		
		return result;
	}
	
	// �����ڰ� ������ �ɼ����� �����ϴ� �޼ҵ�
		public int cartUp(HttpServletRequest request, String mlid) {
			int result = 0;
			
			OrdDAO ordDAO = OrdDAO.getInstance();
			Connection conn = getConnection();
			ordDAO.setConnection(conn);
			result = ordDAO.cartUp(request, mlid);
			if (result == 1)	commit(conn);
			else				rollback(conn);
			close(conn);
			
			return result;
		}
	
	// ��ٱ����� ��ǰ����� ArrayList�� �����ϴ� �޼ҵ�
	public ArrayList<CartInfo> getCartList(String mlid) {
		ArrayList<CartInfo> cartList = null;
		OrdDAO ordDAO = OrdDAO.getInstance();
		Connection conn = getConnection();
		ordDAO.setConnection(conn);
		cartList = ordDAO.getCartList(mlid);
		close(conn);
		
		return cartList;
	}
	
	// ��ǰ �ٷα��� �޼ҵ�
	public ArrayList<CartInfo> getPreOrdList(HttpServletRequest request, String mlid) {
		System.out.println("OrdService�� ��ǰ �ٷ� ����");
		ArrayList<CartInfo> preOrdList = null;
		OrdDAO ordDAO = OrdDAO.getInstance();
		Connection conn = getConnection();
		ordDAO.setConnection(conn);
		preOrdList = ordDAO.getPreOrdList(request, mlid);
		close(conn);
		return preOrdList;
	}
		
	// ��ǰ ���Ÿ� ó���ϴ� �޼ҵ�
	public int ordProcess(HttpServletRequest request, String mlid) {
		int result = 0;

		OrdDAO ordDAO = OrdDAO.getInstance();
		Connection conn = getConnection();
		ordDAO.setConnection(conn);
		result = ordDAO.ordProcess(request, mlid);
		if (result >= 1)	commit(conn);
		else 				rollback(conn);
		close(conn);
		return result;
	}
	
	// �ֹ������ ���ڵ� ������ �����ϴ� �޼ҵ�
	public int getOrdListCount(String mlid) {
		int rcount = 0;
		OrdDAO ordDAO = OrdDAO.getInstance();
		Connection conn = getConnection();
		ordDAO.setConnection(conn);
		rcount = ordDAO.getOrdListCount(mlid);
		close(conn);
		
		return rcount;
	}
	
	// ȸ���� ������ ��ǰ(��)�� ����� ArrayList������ �����ϴ� �޼ҵ�
	public ArrayList<OrdInfo> getOrdList(
		HttpServletRequest request, String mlid, int cpage, int limit) {
		ArrayList<OrdInfo> ordList = null;
		OrdDAO ordDAO = OrdDAO.getInstance();
		Connection conn = getConnection();
		ordDAO.setConnection(conn);
		ordList = ordDAO.getOrdList(request, mlid, cpage, limit);
		close(conn);
		return ordList;
	}
	
	// ȸ���� ������ Ư�� �ֹ��� �������� OrdInfo������ �����ϴ� �޼ҵ�
	public OrdInfo getOrdInfo(HttpServletRequest request, String olid, String mlid) {
		System.out.println("OrdService�� getOrdInfo �޼ҵ� ����");
		OrdInfo ordInfo = null;
		OrdDAO ordDAO = OrdDAO.getInstance();
		Connection conn = getConnection();
		ordDAO.setConnection(conn);
		ordInfo = ordDAO.getOrdInfo(request, olid, mlid);
		close(conn);
		
		return ordInfo;
	}
	
	// ������ ����� ArrayList������ �����ϴ� �޼ҵ�
	public ArrayList<MemberCouponInfo> getCouponList(HttpServletRequest request, String mlid) {
		ArrayList<MemberCouponInfo> memberCoupon = null;
		OrdDAO ordDAO = OrdDAO.getInstance();
		Connection conn = getConnection();
		ordDAO.setConnection(conn);
		memberCoupon = ordDAO.getCouponList(request, mlid);
		close(conn);
		return memberCoupon;
	}
	
	public ArrayList<MemberPointInfo> getPointList(HttpServletRequest request, String mlid) {
		ArrayList<MemberPointInfo> memberPoint = null;
		OrdDAO ordDAO = OrdDAO.getInstance();
		Connection conn = getConnection();
		ordDAO.setConnection(conn);
		memberPoint = ordDAO.getPointList(request, mlid);
		close(conn);
		return memberPoint;
	}
	
	// �ֹ� ��Ҹ� ó���ϴ� �޼ҵ�
	public int orderCancel(HttpServletRequest request, String olid, String olbuyer) {
		int result = 0;

		OrdDAO ordDAO = OrdDAO.getInstance();
		Connection conn = getConnection();
		ordDAO.setConnection(conn);
		result = ordDAO.orderCancel(request, olid, olbuyer);
		if (result >= 1)	commit(conn);
		else 				rollback(conn);
		close(conn);
		return result;
	}
}
