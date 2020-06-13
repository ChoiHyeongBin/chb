package dao;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.ArrayList;
import javax.servlet.http.*;
import javax.servlet.ServletException;
import static db.JdbcUtil.*;
import vo.*;

public class NologOrdDAO {
	private static NologOrdDAO nologordDAO;
	private Connection conn;
	
	private NologOrdDAO() {}	
		
	public static NologOrdDAO getInstance() {
		if (nologordDAO == null) {	
			nologordDAO = new NologOrdDAO();
		}
		
		return nologordDAO;
	}
	
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	// �ٷα���
	public ArrayList<CartInfo> getnologOrdList(HttpServletRequest request) {
		ArrayList<CartInfo> NologOrdList = new ArrayList<CartInfo>();
		Statement stmt = null;
		ResultSet rs = null;
		CartInfo ci = null;	// ci�� �����͸� ���� �ν��Ͻ�
		
		try {
			request.setCharacterEncoding("utf-8");
			String sql = "";
			stmt = conn.createStatement();
			String plid = request.getParameter("plid");
			String optsize = request.getParameter("optsize");
			String optcolor = request.getParameter("optcolor");
			int amount = Integer.parseInt(request.getParameter("amount"));
			String csid = request.getParameter("csid");
			
			sql = "select p.pl_name, p.pl_price, p.pl_img1, p.cs_id, c.cs_id from t_product_list p, t_category_small c " +
					" where pl_isview = 'y' and p.cs_id = c.cs_id and pl_id = '" + plid + "' ";
			rs = stmt.executeQuery(sql);
			
			if (rs.next()) {
				ci = new CartInfo();
				// ArrayList�� preOrdList�� ��� ���� CartInfo�� �ν��Ͻ��� ������
				ci.setPl_id(plid);
				ci.setPl_name(rs.getString("pl_name"));
				ci.setPl_price(rs.getInt("pl_price"));
				ci.setPl_img1(rs.getString("pl_img1"));
				ci.setOc_optsize(optsize);
				ci.setOc_optcolor(optcolor);
				ci.setOc_amount(amount);
				ci.setCs_id(rs.getString("cs_id"));
				
				NologOrdList.add(ci);
			}
		}	catch (Exception e) {
			System.out.println("getPreOrdList() �޼ҵ忡�� ���� �߻�");
		} finally {
			close(rs);
			close(stmt);
		}
		
		return NologOrdList;
	}
	
	// ��ǰ ���Ÿ� ó���ϴ� �޼ҵ�
		public int nologordProcess(HttpServletRequest request) {
			int result = 0;
			Statement stmt = null;
			ResultSet rs = null;
			
			try {
				request.setCharacterEncoding("utf-8");
				
				// ����� ����
				String orderName = request.getParameter("orderName");
				String rname = request.getParameter("rname");
				String pp1 = request.getParameter("pp1");
				String pp2 = request.getParameter("pp2");
				String pp3 = request.getParameter("pp3");
				String phone = pp1 + "-" + pp2 + "-" + pp3;
				String p1 = request.getParameter("p1");
				String p2 = request.getParameter("p2");
				String p3 = request.getParameter("p3");
				String rphone = p1 + "-" + p2 + "-" + p3;
				String call1 = request.getParameter("call1");
				String call2 = request.getParameter("call2");
				String call3 = request.getParameter("call3");
				String rcall = call1 + "-" + call2 + "-" + call3;
				String rzip = request.getParameter("rzip");
				String raddr1 = request.getParameter("raddr1");
				String raddr2 = request.getParameter("raddr2");
				String comment = request.getParameter("comment");
				String ordE1 = request.getParameter("ordE1");
				String ordE2 = request.getParameter("ordE2");
				String email = ordE1 + "@" + ordE2;
				
				// ��ǰ����
				String kind = request.getParameter("kind");
				String[] ocnums = request.getParameterValues("ocnums");
				String[] plids = request.getParameterValues("plids");
				String[] sizes = request.getParameterValues("sizes");
				String[] colors = request.getParameterValues("colors");
				String[] prices = request.getParameterValues("prices");
				String[] amounts = request.getParameterValues("amounts");
				
				// ��������
				String payment = request.getParameter("payment");
				int total = Integer.parseInt(request.getParameter("total"));
				
				stmt = conn.createStatement();
				
				// �ֹ�ID ���� ����
				Calendar today = Calendar.getInstance();	// 'today'�� ���ó�¥�� �ð��� ���� ��
				int y = today.get(Calendar.YEAR);
				int m = today.get(Calendar.MONTH) + 1;
				int d = today.get(Calendar.DAY_OF_MONTH);
				String mm = (m < 10 ? "0" + m : "" + m);
				String dd = (d < 10 ? "0" + d : "" + d);
				String olid = y + mm + dd + "1110001";
				// ���� ��¥�� 20200519 ������ ���ڿ��� ������ �� �ֹ�ID ����
				
				String sql = "select ol_id from t_order_list where date(ol_date) = date(now()) " + 
						"order by ol_id desc limit 1";
				rs = stmt.executeQuery(sql);
				System.out.println(sql);
				if (rs.next()) {
				// ���� �ֹ��� ������ ������(���� �ֱ� �ֹ��� �ֹ�ID�� ����)
					long lng = Long.parseLong(rs.getString("ol_id")) + 1;
					// ������ �ֹ�ID�� ������ ��ȯ �� 1���� ��Ŵ
					olid = lng + "";	// ���ο� �ֹ�ID�� ����
				}
				
				// t_order_list ���̺� insert
				sql = "insert into t_order_list (ol_id, ol_ismem, ol_buyer, ol_phone, ol_email, ol_rname, ol_rphone, ol_call, " + 
				"ol_rzip, ol_raddr1, ol_raddr2, ol_comment, ol_payment, ol_pay, ol_status) " + 
				"values ('" + olid + "', 'n', '" + orderName + "', '" + phone + "', '" + email + "', '" + rname + "', '" + rphone + "', " + 
				"'" + rcall + "', '" + rzip + "', '" + raddr1 + "' , '" + raddr2 + "', '" + comment + "', '" + payment + "', '" + total + "', 'a' )";
					System.out.println(sql);
					result = stmt.executeUpdate(sql);
					if (result < 1) return result; // insert���н� �ٷ� return�Ͽ� �޼ҵ带 �����Ű��, ������ rollback��
					
					// t_order_detail ���̺� insert �� t_product_list ���̺� update
					for (int i = 0; i < plids.length; i++) {
						// �ֹ� �� ���̺��� ��ǰ �߰� ����
						sql = "insert into t_order_detail (ol_id, pl_id, od_optsize, od_optcolor, od_price, od_amount) " +
								"value ('" + olid + "', '" + plids[i] + "', '" + sizes[i] +  "', '" + colors[i] + "', '" + prices[i] + "', '" + amounts[i] + "')";
						System.out.println(sql);
						result = stmt.executeUpdate(sql);
						if (result < 1) return result;
						
						// ��ǰ ���̺��� ��� ���� ����
						sql = "update t_product_list set pl_stock = pl_stock - " + amounts[i] + " where pl_stock > 0 and pl_id = '" + plids[i] + "'";
						System.out.println(sql);
						result = stmt.executeUpdate(sql);
						// t_cart_list ���̺��� ����
						
						// ��ǰ ���̺��� �Ǹŷ� ���� ����
						sql = "update t_product_list set pl_salecnt = pl_salecnt + " + amounts[i] + " where pl_id = '" + plids[i] + "'";
						System.out.println(sql);
						result = stmt.executeUpdate(sql);
						if (result < 1) return result;
					}	
			} catch (Exception e) {
				System.out.println("ordProcess() �޼ҵ忡�� ���� �߻�");
			} finally {
				close(stmt);
			}
			
			return result;
		}
		
		// �ֹ������ ���ڵ� ������ �����ϴ� �޼ҵ�
		public int getnologOrdListCount(String olbuyer) {
			int rcount = 0;
			Statement stmt = null;
			ResultSet rs = null;
			try {
				String sql = "select count(*) from t_order_list where ol_buyer = '" + olbuyer + "' ";
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				rs.next();	// count()�Լ� ���� ���� ���� ������� �����Ƿ� �˻���� next()�� ������
				rcount = rs.getInt(1);
				// Ư�� ȸ��(olbuyer)�� ������ ����� ���ڵ� ������ ����
			} catch (Exception e) {
				System.out.println("getOrdListCount() �޼ҵ忡�� ���� �߻�");
				e.printStackTrace();
			} finally {
				close(rs);
				close(stmt);
			}
			
			return rcount;
		}
		
		// ȸ���� ������ ��ǰ(��)�� ����� ArrayList������ �����ϴ� �޼ҵ�
		public ArrayList<OrdInfo> getOrdList(HttpServletRequest request, String olbuyer, int cpage, int limit) {
			ArrayList<OrdInfo> ordList = new ArrayList<OrdInfo>();
			Statement stmt = null;
			Statement stmt2 = null;
			ResultSet rs = null;
			ResultSet rs2 = null;
			OrdInfo oi = null;			// �ֹ������ �ν��Ͻ�
			OrdDetailInfo odi = null;	// �ֹ��� ��ǰ���� ����� �ν��Ͻ�
			
			try {
				request.setCharacterEncoding("utf-8");
				
				int start = (cpage - 1) * limit;
				String sql = "select * from t_order_list o, t_category_small c, t_product_list p where p.cs_id = c.cs_id and  o.ol_buyer = '" + olbuyer + "' " + 
				" order by o.ol_id desc limit 0, 1";
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				while (rs.next()) {	// �ֹ������� ������
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
					oi.setOl_payment(rs.getString("ol_payment"));
					oi.setOl_pay(rs.getInt("ol_pay"));
					oi.setOl_status(rs.getString("ol_status"));
					oi.setOl_date(rs.getString("ol_date"));
					oi.setStatus(getStatusTxt(rs.getString("ol_status")));
					oi.setCs_id(rs.getString("cs_id"));
					
					// �ֹ������� ��ǰ�� ��� ���� ����
					sql = "select d.*, p.pl_name, p.pl_img1, p.cs_id " + 
							"from t_order_detail d, t_product_list p " + 
							"where d.pl_id = p.pl_id and ol_id = '" + rs.getString("ol_id") + "'";
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
								} while (rs2.next());
								// ������ ��ǰ����� oi�ν��Ͻ��� ����
								
							} else {	// �ֹ� ��ǰ�� ������(�ֹ��������� ��ǰ������ ������)
								return ordList;
							}
							
							oi.setOrdPdtList(OrdDetailList);
							ordList.add(oi);
				}
			} catch (Exception e) {
				System.out.println("getOrdList() �޼ҵ忡�� ���� �߻�");
			} finally {
				close(rs2);
				close(stmt2);
				close(rs);
				close(stmt);
			}
			
			return ordList;
		}
		
		// ȸ���� ������ Ư�� �ֹ��� �������� OrdInfo������ �����ϴ� �޼ҵ�
		public OrdInfo getOrdInfo(HttpServletRequest request, String olid) {
			OrdInfo ordInfo = new OrdInfo();
			Statement stmt = null;
			Statement stmt2 = null;
			ResultSet rs = null;
			ResultSet rs2 = null;
			OrdInfo oi = null;			// �ֹ������ �ν��Ͻ�
			OrdDetailInfo odi = null;	// �ֹ��� ��ǰ���� ����� �ν��Ͻ�
			
			try {
				String sql = "select * from t_order_list where ol_id = '" + olid + "' ";
				System.out.println(sql);
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				if (rs.next()) {	// �ֹ������� ������
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
					oi.setOl_payment(rs.getString("ol_payment"));
					oi.setOl_pay(rs.getInt("ol_pay"));
					oi.setOl_status(rs.getString("ol_status"));
					oi.setOl_date(rs.getString("ol_date"));
					oi.setStatus(getStatusTxt(rs.getString("ol_status")));
					oi.setPayStatus(getPayStatusTxt(rs.getString("ol_payment")));
					
					// �ֹ������� ��ǰ�� ��� ���� ����
					sql = "select d.*, p.pl_name, p.pl_img1, p.cs_id, c.cs_id " + 
							"from t_order_detail d, t_product_list p, t_category_small c " + 
							"where d.pl_id = p.pl_id and ol_id = '" + olid + "' and p.cs_id = c.cs_id ";
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
								} while (rs2.next());
								// ������ ��ǰ����� oi�ν��Ͻ��� ����
								
							} else {	// �ֹ� ��ǰ�� ������(�ֹ��������� ��ǰ������ ������)
								return oi;
							}
							
							oi.setOrdPdtList(OrdDetailList);
				}
			} catch (Exception e) {
				System.out.println("getOrdInfo() �޼ҵ忡�� ���� �߻�");
				e.printStackTrace();
			} finally {
				close(rs2);
				close(stmt2);
				close(rs);
				close(stmt);
			}
			
			return oi;
		}
	
		// ���°��� ���� ���ڿ� �� ����
		public String getStatusTxt(String status) {
			String statusTxt = "";
			switch (status.toLowerCase()) {
				case "a" : statusTxt = "�Աݴ��"; 			break;
				case "b" : statusTxt = "�����Ϸ�"; 			break;
				case "c" : statusTxt = "��ǰ�غ�";	 			break;
				case "d" : statusTxt = "����غ�"; 			break;
				case "e" : statusTxt = "�����"; 				break;
				case "f" : statusTxt = "��ۿϷ�"; 			break;
				case "g" : statusTxt = "��ȯ��û"; 			break;
				case "h" : statusTxt = "��ǰ�䫊"; 			break;
				case "i" : statusTxt = "ȯ�ҿ�û"; 			break;
				case "j" : statusTxt = "��ȯ�Ϸ�"; 			break;
				case "k" : statusTxt = "��ǰ�Ϸ�"; 			break;
				case "l" : statusTxt = "ȯ�ҿϷ�"; 			break;
			}
			
			return statusTxt;
		}
		
		// ���°��� ���� ���ڿ� �� ����(��������)
	    public String getPayStatusTxt(String payStatus) {
	       String statusTxt = "";
	       switch (payStatus.toLowerCase()) {
	          case "a" : statusTxt = "�ſ�/üũī��";    break;
	          case "b" : statusTxt = "�������Ա�";       break;
	          case "c" : statusTxt = "�޴�������";       break;
	       }
	      
	       return statusTxt;
	    }
		
		
		public ArrayList<OrdInfo> getNologOrdList(HttpServletRequest request, String olbuyer, String olids) {
			ArrayList<OrdInfo> nologordList = new ArrayList<OrdInfo>();
			Statement stmt = null;
			Statement stmt2 = null;
			ResultSet rs = null;
			ResultSet rs2 = null;
			OrdInfo oi = null;			// �ֹ������ �ν��Ͻ�
			OrdDetailInfo odi = null;	// �ֹ��� ��ǰ���� ����� �ν��Ͻ�
			System.out.println(olbuyer + " // " + olids);
			
			try {
				request.setCharacterEncoding("utf-8");
				
				String sql = "select * from t_order_list where ol_buyer = '" + olbuyer + "' and ol_id = '" + olids + "' and ol_ismem = 'n' order by ol_id desc";
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				while (rs.next()) {	// �ֹ������� ������
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
					oi.setOl_payment(rs.getString("ol_payment"));
					oi.setOl_pay(rs.getInt("ol_pay"));
					oi.setOl_status(rs.getString("ol_status"));
					oi.setOl_date(rs.getString("ol_date"));
					oi.setStatus(getStatusTxt(rs.getString("ol_status")));
					
					// �ֹ������� ��ǰ�� ��� ���� ����
					sql = "select d.*, p.pl_name, p.pl_img1, p.cs_id, c.cs_id " + 
							"from t_order_detail d, t_product_list p, t_category_small c " + 
							"where d.pl_id = p.pl_id and ol_id = '" + rs.getString("ol_id") + "' and p.cs_id = c.cs_id";
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
								} while (rs2.next());
								// ������ ��ǰ����� oi�ν��Ͻ��� ����
								
							} else {	// �ֹ� ��ǰ�� ������(�ֹ��������� ��ǰ������ ������)
								return nologordList;
							}
							
							oi.setOrdPdtList(OrdDetailList);
							nologordList.add(oi);
				}
			} catch (Exception e) {
				System.out.println("getnologOrdList() �޼ҵ忡�� ���� �߻�");
			} finally {
				close(rs2);
				close(stmt2);
				close(rs);
				close(stmt);
			}
			
			return nologordList;
		}
		
		public int orderCancel(HttpServletRequest request) {	// ��ǰ���� ��Ҹ� �ϴ� �޼ҵ�
			int result = 0;
			Statement stmt = null;
			
			try {
				request.setCharacterEncoding("utf-8");
				
				String buyer = request.getParameter("olbuyer");
				String olids = request.getParameter("olids");
				System.out.println(buyer + " // " + olids);
				
				stmt = conn.createStatement();
				
				String sql = "delete from t_order_detail where ol_id = '" + olids + "' ";
				System.out.println("detail" + sql);
			    result = stmt.executeUpdate(sql);
			    
			    sql = "delete from t_order_list where ol_id = '" + olids + "' and ol_ismem = 'n' and ol_buyer = '" + buyer + "' ";
			    System.out.println("detail" + sql);
			    result = stmt.executeUpdate(sql);
			    
			} catch (Exception e) {
		         System.out.println("nologordProcess() �޼ҵ� ����");
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
