package dao;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.ArrayList;
import javax.servlet.http.*;
import javax.servlet.ServletException;
import static db.JdbcUtil.*;
import vo.*;

public class AdminOrdDAO {
	private static AdminOrdDAO adminOrdDAO;
	private Connection conn;
	
	private AdminOrdDAO() {}	
	
	public static AdminOrdDAO getInstance() {
		if (adminOrdDAO == null) {	
			adminOrdDAO = new AdminOrdDAO();
		}
		
		return adminOrdDAO;
	}
	
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	
	public int getListCount(String where) {
		int rcount = 0;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "select count(*) from t_order_list where 1=1 " + where;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			rs.next();	// if���� ������� �ʴ� ���� count()����̹Ƿ� ������ ������� �����ϹǷ�
			rcount = rs.getInt(1);
			System.out.println(sql);
		} catch (Exception e) {
			System.out.println("getListCount(admin) �޼ҵ忡�� ���� �߻�");
		} finally {
			close(rs);
			close(stmt);
		}
		
		return rcount;
	} 
	
	public ArrayList<OrdInfo> getOrdList(String where, int cpage, int limit) {
		ArrayList<OrdInfo> ordList = new ArrayList<OrdInfo>();
		Statement stmt = null;
		ResultSet rs = null;
		OrdInfo oi = null;
		Statement stmt2 = null;
		ResultSet rs2 = null;	
		OrdDetailInfo odi = null;	
		try {
			int start = (cpage - 1) * limit;
			String sql = "select * from t_order_list where 1=1 " + where;
			sql += " order by ol_num desc limit " + start + ", " + limit;
			System.out.println(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				oi = new OrdInfo();
				oi.setOl_num(rs.getInt("ol_num"));
				oi.setOl_id(rs.getString("ol_id"));
				oi.setOl_ismem(rs.getString("ol_ismem"));
				oi.setOl_buyer(rs.getString("ol_buyer"));
				oi.setOl_phone(rs.getString("ol_phone"));
				oi.setOl_email(rs.getString("ol_email"));
				oi.setOl_rname(rs.getString("ol_rname"));
				oi.setOl_rphone(rs.getString("ol_rphone"));
				oi.setOl_rzip(rs.getString("ol_rzip"));
				oi.setOl_raddr1(rs.getString("ol_raddr1"));
				oi.setOl_raddr2(rs.getString("ol_raddr2"));
				oi.setOl_comment(rs.getString("ol_comment"));
				oi.setOl_point(rs.getInt("ol_point"));
				oi.setOl_payment(rs.getString("ol_payment"));
				oi.setOl_pay(rs.getInt("ol_pay"));
				oi.setOl_status(rs.getString("ol_status"));
				// oi.setOl_refundbank(rs.getString("ol_refundbank"));
				// oi.setOl_refundaccount(rs.getString("ol_refundaccount"));
				oi.setOl_date(rs.getString("ol_date"));
				oi.setStatus(getStatusTxt(rs.getString("ol_status")));
				oi.setPayStatus(getPayStatusTxt(rs.getString("ol_payment")));
				   
				/// �ֹ������� ��ǰ�� ��� ���� ����
				sql = "select d.*, p.pl_name, p.pl_img1, cs.cs_id " + 
						"from t_order_detail d, t_product_list p, t_category_small cs " + 
						"where d.pl_id = p.pl_id and ol_id = '" + rs.getString("ol_id") + "' and cs.cs_id = p.cs_id";
						System.out.println(sql);
						stmt2 = conn.createStatement();
						rs2 = stmt2.executeQuery(sql);	// rs2�� ��������� �ȵ�
						ArrayList<OrdDetailInfo> OrdDetailList = new ArrayList<OrdDetailInfo>();
						if (rs2.next()) {	// �ֹ� ��ǰ�� ������
							do {
								odi = new OrdDetailInfo();
								// �ֹ��� ��ǰ������ ���� �ν��Ͻ�
								
								odi.setOd_num(rs2.getInt("od_num"));
								odi.setOl_id(rs2.getString("ol_id"));
								odi.setPl_id(rs2.getString("pl_id"));
								odi.setOd_optsize(rs2.getString("od_optsize"));
								odi.setOd_optcolor(rs2.getString("od_optcolor"));
								odi.setOd_price(rs2.getInt("od_price"));
								odi.setOd_amount(rs2.getInt("od_amount"));
								odi.setPl_name(rs2.getString("pl_name"));
								odi.setPl_img1(rs2.getString("pl_img1"));
								odi.setCs_id(rs2.getString("cs_id"));
								
								OrdDetailList.add(odi);
								
								oi.setCs_id(rs2.getString("cs_id"));
							} while (rs2.next());
							// ������ ��ǰ����� oi�ν��Ͻ��� ����
							
						} else {	// �ֹ� ��ǰ�� ������(�ֹ��������� ��ǰ������ ������)
							return ordList;
						}
						
						oi.setOrdPdtList(OrdDetailList);
						ordList.add(oi);
			}
		} catch (Exception e) {
			System.out.println("getOrdList(admin) �޼ҵ忡�� ���� �߻�");
		} finally {
			close(rs2);
			close(stmt2);
			close(rs);
			close(stmt);
		}
		
		return ordList;
	}
	
	public ArrayList<OrdInfo> getOrdList2(String where, int cpage, int limit) {
		System.out.println("DAO�� getOrdList2 �޼ҵ� ����");
		ArrayList<OrdInfo> ordList = new ArrayList<OrdInfo>();
		System.out.println("where : " + where);
		Statement stmt = null;
		ResultSet rs = null;
		OrdInfo oi = null;
		Statement stmt2 = null;
		ResultSet rs2 = null;	
		OrdDetailInfo odi = null;	
		try {
			int start = (cpage - 1) * limit;
			String sql = "select * from t_order_list where " + where;	// �߰�
			sql += " order by ol_num desc limit " + start + ", " + limit;
			System.out.println("��������Ʈ : " + sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				oi = new OrdInfo();
				oi.setOl_num(rs.getInt("ol_num"));
				oi.setOl_id(rs.getString("ol_id"));
				oi.setOl_ismem(rs.getString("ol_ismem"));
				oi.setOl_buyer(rs.getString("ol_buyer"));
				oi.setOl_phone(rs.getString("ol_phone"));
				oi.setOl_email(rs.getString("ol_email"));
				oi.setOl_rname(rs.getString("ol_rname"));
				oi.setOl_rphone(rs.getString("ol_rphone"));
				oi.setOl_rzip(rs.getString("ol_rzip"));
				oi.setOl_raddr1(rs.getString("ol_raddr1"));
				oi.setOl_raddr2(rs.getString("ol_raddr2"));
				oi.setOl_comment(rs.getString("ol_comment"));
				oi.setOl_point(rs.getInt("ol_point"));
				oi.setOl_payment(rs.getString("ol_payment"));
				oi.setOl_pay(rs.getInt("ol_pay"));
				oi.setOl_status(rs.getString("ol_status"));
				// oi.setOl_refundbank(rs.getString("ol_refundbank"));
				// oi.setOl_refundaccount(rs.getString("ol_refundaccount"));
				oi.setOl_date(rs.getString("ol_date"));
				oi.setStatus(getStatusTxt(rs.getString("ol_status")));
				oi.setPayStatus(getPayStatusTxt(rs.getString("ol_payment")));
				oi.setMc_num(rs.getInt("mc_num"));
				   
				/// �ֹ������� ��ǰ�� ��� ���� ����
				sql = "select d.*, p.pl_name, p.pl_img1, cs.cs_id " + 
						"from t_order_detail d, t_product_list p, t_category_small cs " + 
						"where d.pl_id = p.pl_id and ol_id = '" + rs.getString("ol_id") + "' and cs.cs_id = p.cs_id";
						System.out.println("�ֹ� �� �� ��ǰ ��� : " + sql);
						stmt2 = conn.createStatement();
						rs2 = stmt2.executeQuery(sql);	// rs2�� ��������� �ȵ�
						ArrayList<OrdDetailInfo> OrdDetailList = new ArrayList<OrdDetailInfo>();
						if (rs2.next()) {	// �ֹ� ��ǰ�� ������
							do {
								odi = new OrdDetailInfo();
								// �ֹ��� ��ǰ������ ���� �ν��Ͻ�
								
								odi.setOd_num(rs2.getInt("od_num"));
								odi.setOl_id(rs2.getString("ol_id"));
								odi.setPl_id(rs2.getString("pl_id"));
								odi.setOd_optsize(rs2.getString("od_optsize"));
								odi.setOd_optcolor(rs2.getString("od_optcolor"));
								odi.setOd_price(rs2.getInt("od_price"));
								odi.setOd_amount(rs2.getInt("od_amount"));
								odi.setPl_name(rs2.getString("pl_name"));
								odi.setPl_img1(rs2.getString("pl_img1"));
								odi.setCs_id(rs2.getString("cs_id"));
								
								OrdDetailList.add(odi);
								
								oi.setCs_id(rs2.getString("cs_id"));
							} while (rs2.next());
							// ������ ��ǰ����� oi�ν��Ͻ��� ����
							
						} else {	// �ֹ� ��ǰ�� ������(�ֹ��������� ��ǰ������ ������)
							return ordList;
						}
						
						oi.setOrdPdtList(OrdDetailList);
						ordList.add(oi);
			}
		} catch (Exception e) {
			System.out.println("getOrdList2(admin) �޼ҵ忡�� ���� �߻�");
		} finally {
			close(rs2);
			close(stmt2);
			close(rs);
			close(stmt);
		}
		
		return ordList;
	}
	
	public OrdInfo getOrd(int num) {
		OrdInfo ordInfo = null;
		// ������ �������� �� �����͸� �����ϱ� ���� �ν��Ͻ�
		Statement stmt = null;
		// DB�� ������ �����ִ� ��ü
		ResultSet rs = null;
		// �޾ƿ� ������ ��� ��ü
		
		try {
			String sql = "select * from t_order_list where ol_num = " + num;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);	// ResultSet ����
			if (rs.next()) {
				ordInfo = new OrdInfo();
				// �ϳ��� �Խñ� �����͸� ��� ���� OrdInfo �ν��Ͻ� ����
				
				ordInfo.setOl_num(rs.getInt("ol_num"));
				ordInfo.setOl_num(rs.getInt("ol_num"));
				ordInfo.setOl_id(rs.getString("ol_id"));
				ordInfo.setOl_ismem(rs.getString("ol_ismem"));
				ordInfo.setOl_buyer(rs.getString("ol_buyer"));
				ordInfo.setOl_phone(rs.getString("ol_phone"));
				ordInfo.setOl_email(rs.getString("ol_email"));
				ordInfo.setOl_rname(rs.getString("ol_rname"));
				ordInfo.setOl_rphone(rs.getString("ol_rphone"));
				ordInfo.setOl_rzip(rs.getString("ol_rzip"));
				ordInfo.setOl_raddr1(rs.getString("ol_raddr1"));
				ordInfo.setOl_raddr2(rs.getString("ol_raddr2"));
				ordInfo.setOl_comment(rs.getString("ol_comment"));
				ordInfo.setOl_point(rs.getInt("ol_point"));
				ordInfo.setOl_payment(rs.getString("ol_payment"));
				ordInfo.setOl_pay(rs.getInt("ol_pay"));
				ordInfo.setOl_status(rs.getString("ol_status"));
				// ordInfo.setOl_refundbank(rs.getString("ol_refundbank"));
				// ordInfo.setOl_refundaccount(rs.getString("ol_refundaccount"));
				ordInfo.setOl_date(rs.getString("ol_date"));
				ordInfo.setStatus(getStatusTxt(rs.getString("ol_status")));
				ordInfo.setPayStatus(getPayStatusTxt(rs.getString("ol_payment")));
			}
		} catch(Exception e) {
			System.out.println("getOrd(admin) �޼ҵ忡�� ���� �߻�");
		} finally {
			close(rs);
			close(stmt);
		}
		
		return ordInfo;
	}
	
	public int ordUpdate(HttpServletRequest request) {
		int result = 0;	
		Statement stmt = null;
	  
		try {
			request.setCharacterEncoding("utf-8");
    	
		// request��ü�� ��뿡�� ����ó���� �ʿ��ϹǷ� try�� �ȿ��� �۾�
		// ȸ������ ������ ������Ʈ������ where���� �������� ����ϱ� ���� id
		String ordStatus       = request.getParameter("ordStatus");
		String optsize		   = request.getParameter("optsize");
		String optcolor		   = request.getParameter("optcolor");
		String olid			   = request.getParameter("olid");

		String sql = "update t_order_list set ";
        sql += "ol_status = '" 		+ ordStatus 	+ "' ";
        // sql += "od_optsize = '" 	+ optsize 		+ "', ";
        // sql += "od_optcolor = '" 	+ optcolor 		+ "' ";
        sql += "where ol_id = " + olid;
        System.out.println(sql);

        stmt = conn.createStatement();
        result = stmt.executeUpdate(sql);
        
        // �������� ���� ����
		sql = "update t_order_detail set od_optsize = '" + optsize + "', od_optcolor = '" + optcolor + "' where ol_id = " + olid;
		System.out.println("������ ���� : " + sql);
		result = stmt.executeUpdate(sql);

		} catch (Exception e) {
        System.out.println("ordUpdate(admin) �޼ҵ� ����");
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
	
	// ���°��� ���� ���ڿ� �� ����(�ֹ�����)
	public String getStatusTxt(String status) {
		String statusTxt = "";
		switch (status.toLowerCase()) {
			case "a" : statusTxt = "�Աݴ��"; 			break;
			case "b" : statusTxt = "�����Ϸ�"; 			break;
			case "c" : statusTxt = "��ǰ�غ�";	 		break;
			case "d" : statusTxt = "����غ�"; 			break;
			case "e" : statusTxt = "�����"; 			break;
			case "f" : statusTxt = "��ۿϷ�"; 			break;
			case "g" : statusTxt = "��ǰ��û"; 			break;
			case "h" : statusTxt = "��ǰ�Ϸ�"; 			break;
			case "i" : statusTxt = "ȯ�ҿ�û"; 			break;
			case "j" : statusTxt = "ȯ�ҿϷ�"; 			break;
			case "k" : statusTxt = "��ȯ��û"; 			break;
			case "l" : statusTxt = "��ȯ�Ϸ�"; 			break;
		}
		
		return statusTxt;
	}
	
	// ���°��� ���� ���ڿ� �� ����(��������)
	public String getPayStatusTxt(String payStatus) {
		String statusTxt = "";
		switch (payStatus.toLowerCase()) {
			case "a" : statusTxt = "�ſ�/üũī��"; 	break;
			case "b" : statusTxt = "�������Ա�"; 		break;
			case "c" : statusTxt = "�޴�������"; 		break;
		}
		
		return statusTxt;
	}
	
	// ���� ���ȭ��
	public ArrayList<OrdInfo> getMainOrdList() {
		ArrayList<OrdInfo> ordList = new ArrayList<OrdInfo>();
		Statement stmt = null;
		ResultSet rs = null;
		OrdInfo oi = null;
		Statement stmt2 = null;
		ResultSet rs2 = null;	
		OrdDetailInfo odi = null;	
		try {
			String sql = "select * from t_order_list where 1=1 ";
			sql += " order by ol_num desc ";
			System.out.println(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				oi = new OrdInfo();
				oi.setOl_num(rs.getInt("ol_num"));
				oi.setOl_id(rs.getString("ol_id"));
				oi.setOl_ismem(rs.getString("ol_ismem"));
				oi.setOl_buyer(rs.getString("ol_buyer"));
				oi.setOl_phone(rs.getString("ol_phone"));
				oi.setOl_email(rs.getString("ol_email"));
				oi.setOl_rname(rs.getString("ol_rname"));
				oi.setOl_rphone(rs.getString("ol_rphone"));
				oi.setOl_rzip(rs.getString("ol_rzip"));
				oi.setOl_raddr1(rs.getString("ol_raddr1"));
				oi.setOl_raddr2(rs.getString("ol_raddr2"));
				oi.setOl_comment(rs.getString("ol_comment"));
				oi.setOl_point(rs.getInt("ol_point"));
				oi.setOl_payment(rs.getString("ol_payment"));
				oi.setOl_pay(rs.getInt("ol_pay"));
				oi.setOl_status(rs.getString("ol_status"));
				// oi.setOl_refundbank(rs.getString("ol_refundbank"));
				// oi.setOl_refundaccount(rs.getString("ol_refundaccount"));
				oi.setOl_date(rs.getString("ol_date"));
				oi.setStatus(getStatusTxt(rs.getString("ol_status")));
				oi.setPayStatus(getPayStatusTxt(rs.getString("ol_payment")));
				   
				/// �ֹ������� ��ǰ�� ��� ���� ����
				sql = "select d.*, p.pl_name, p.pl_img1, cs.cs_id " + 
						"from t_order_detail d, t_product_list p, t_category_small cs " + 
						"where d.pl_id = p.pl_id and ol_id = '" + rs.getString("ol_id") + "' and cs.cs_id = p.cs_id";
						System.out.println(sql);
						stmt2 = conn.createStatement();
						rs2 = stmt2.executeQuery(sql);	// rs2�� ��������� �ȵ�
						ArrayList<OrdDetailInfo> OrdDetailList = new ArrayList<OrdDetailInfo>();
						if (rs2.next()) {	// �ֹ� ��ǰ�� ������
							do {
								odi = new OrdDetailInfo();
								// �ֹ��� ��ǰ������ ���� �ν��Ͻ�
								
								odi.setOd_num(rs2.getInt("od_num"));
								odi.setOl_id(rs2.getString("ol_id"));
								odi.setPl_id(rs2.getString("pl_id"));
								odi.setOd_optsize(rs2.getString("od_optsize"));
								odi.setOd_optcolor(rs2.getString("od_optcolor"));
								odi.setOd_price(rs2.getInt("od_price"));
								odi.setOd_amount(rs2.getInt("od_amount"));
								odi.setPl_name(rs2.getString("pl_name"));
								odi.setPl_img1(rs2.getString("pl_img1"));
								odi.setCs_id(rs2.getString("cs_id"));
								
								OrdDetailList.add(odi);
								
								oi.setCs_id(rs2.getString("cs_id"));
							} while (rs2.next());
							// ������ ��ǰ����� oi�ν��Ͻ��� ����
							
						} else {	// �ֹ� ��ǰ�� ������(�ֹ��������� ��ǰ������ ������)
							return ordList;
						}
						
						oi.setOrdPdtList(OrdDetailList);
						ordList.add(oi);
			}
		} catch (Exception e) {
			System.out.println("getMainOrdList(admin) �޼ҵ忡�� ���� �߻�");
		} finally {
			close(rs2);
			close(stmt2);
			close(rs);
			close(stmt);
		}
		
		return ordList;
	}
}
