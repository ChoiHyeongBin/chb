package dao;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;

import javax.servlet.http.*;
import javax.servlet.ServletException;
import static db.JdbcUtil.*;
import vo.*;

public class NoticeDAO {
	private static NoticeDAO noticeDAO;
	private Connection conn;
	
	private NoticeDAO() {}
	
	public static NoticeDAO getInstance() {
		if (noticeDAO == null) {
			noticeDAO = new NoticeDAO();
		}
		
		return noticeDAO;
	}
	
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	
	// �������� �Խù��� �� �˻��Ǵ� ��ü ������ �����ϴ� �޼ҵ�
	public int getListCount(String where) {
		int rcount = 0;
		Statement stmt = null;	// SQL���� �����ͺ��̽��� ���������� ��ü
		ResultSet rs = null;	// SQL ���ǿ� ���� ������ ���̺��� �����ϴ� ��ü
		
		try {
			String sql = "select count(*) from t_notice_list where 1=1 " + where;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			rs.next();	// if���� ������� �ʴ� ���� count()����̹Ƿ� ������ ������� �����ϹǷ�
			rcount = rs.getInt(1);
		} catch (Exception e) {
			System.out.println("getListCount() �޼ҵ忡�� ���� �߻�");
		} finally {
			close(rs);
			close(stmt);
		}
		
		return rcount;
	}
	
	// ���������� ����� ArrayList���·� �����ϴ� �޼ҵ�
	public ArrayList<NoticeInfo> getNoticeList(String where, int cpage, int limit) {
		ArrayList<NoticeInfo> noticeList = new ArrayList<NoticeInfo>();
		Statement stmt = null;
		ResultSet rs = null;
		NoticeInfo noticeInfo = null;
		
		try {
			int start = (cpage - 1) * limit;
			String sql = "select * from t_notice_list where 1=1 " + where;
			sql += " order by nl_num desc limit " + start + ", " + limit;
			System.out.println(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				noticeInfo = new NoticeInfo();
				// �ϳ��� �Խñ� �����͸� ��� ���� NoticeInfo �ν��Ͻ� ����
				
				noticeInfo.setNl_num(rs.getInt("nl_num"));
				noticeInfo.setNl_kind(rs.getString("nl_kind"));
				noticeInfo.setNl_title(rs.getString("nl_title"));
				noticeInfo.setNl_content(rs.getString("nl_content"));
				noticeInfo.setNl_read(rs.getInt("nl_read"));
				noticeInfo.setNl_date(rs.getString("nl_date"));
				noticeInfo.setAl_num(rs.getInt("al_num"));
				noticeInfo.setNl_isview(rs.getString("nl_isview"));
				noticeInfo.setAl_name(rs.getString("al_name"));
				// ������ �ν��Ͻ��� ������ ä���
				noticeList.add(noticeInfo);
				// �������� ����� ���� ArrayList�� ������ �ν��Ͻ��� ����
			}
			
		} catch (Exception e) {
			System.out.println("getNoticeList() �޼ҵ忡�� ���� �߻�");
		} finally {
			close(rs);
			close(stmt);
		}
		
		return noticeList;
	}
	
	// �� ���������� ��Ͻ�Ű�� �޼ҵ�
	public String insertNotice(HttpServletRequest request) {
		int result = 0, nlNum = 1;
		String nlResult = "";
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "select max(nl_num) + 1 from t_notice_list";
			System.out.println(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())	nlNum = rs.getInt(1);
			
			String title = request.getParameter("title").trim().replaceAll("'", "''");
			// trim() : ���ڿ� �¿��� ������ �����ϴ� �Լ�
			// replaceAll() : ex) "'"�� ã�Ƽ� "''"�� �ٲ���, abc�� ���� a or b or c�� �� ���ڿ� ��ü�� �ٲ���
			System.out.println("title : " + title);
			String content = request.getParameter("content").trim().replaceAll("'", "''");
			
			sql = "insert into t_notice_list (nl_num, nl_title, nl_content, al_num) values (" + nlNum + ", '";
			sql += title + "', '" + content + "', 0)";
			System.out.println(sql);
			result = stmt.executeUpdate(sql);
			nlResult = result + ":" + nlNum;
			
		} catch(Exception e) {
			System.out.println("insertNotice() �޼ҵ忡�� ���� �߻�");
		} finally {
			close(rs);
			close(stmt);
		}
		
		return nlResult;
	}
	
	// ���������� �����ϴ� �޼ҵ�
	public int deleteNotice(HttpServletRequest request) {
		int result = 0;
		Statement stmt = null;
		
		try {
			String num = request.getParameter("num");
			
			String sql = "delete from t_notice_list where nl_num = " + num;
			System.out.println(sql);
			
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
			
		} catch (Exception e) {
			System.out.println("deleteNotice() �޼ҵ忡�� ���� �߻�");
		} finally {
			close(stmt);
		}
		
		return result;
	}
	
	// �������� �Խñ��� ��ȸ���� ������Ű�� �޼ҵ�
	public int updateRead(int num) {
		int result = 0;
		Statement stmt = null;
		
		try {
			String sql = "update t_notice_list set ";
			sql += "nl_read = nl_read + 1 where nl_num = " + num;
			
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
		} catch (Exception e) {
			System.out.println("updateRead() �޼ҵ忡�� ���� �߻�");
		} finally {
			close(stmt);
		}
		
		return result;
	}
	
	// Ư�� �������� �� �ϳ��� �����ϴ� �޼ҵ�
	public NoticeInfo getNotice(int num) {
		NoticeInfo noticeInfo = null;
		// ������ �������� �� �����͸� �����ϱ� ���� �ν��Ͻ�
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "select * from t_notice_list where nl_num = " + num;
			System.out.println(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if (rs.next()) {
				noticeInfo = new NoticeInfo();
				
				noticeInfo.setNl_num(rs.getInt("nl_num"));
				noticeInfo.setNl_kind(rs.getString("nl_kind"));
				noticeInfo.setNl_title(rs.getString("nl_title"));
				noticeInfo.setNl_content(rs.getString("nl_content"));
				noticeInfo.setNl_read(rs.getInt("nl_read"));
				noticeInfo.setNl_date(rs.getString("nl_date"));
				noticeInfo.setAl_num(rs.getInt("al_num"));
				noticeInfo.setNl_isview(rs.getString("nl_isview"));
				noticeInfo.setAl_name(rs.getString("al_name"));
			}
			
		} catch (Exception e) {
			System.out.println("getNotice() �޼ҵ忡�� ���� �߻�");
		} finally {
			close(rs);
			close(stmt);
		}
		
		return noticeInfo;
	}
	
	// ���������� �����ϴ� �޼ҵ�
	public int updateNotice(HttpServletRequest request) {
		int result = 0;
		Statement stmt = null;
		
		try {
			String num = request.getParameter("num");
			String title = request.getParameter("title").trim().replaceAll("'", "''");
			System.out.println("title : " + title);
			String content = request.getParameter("content").trim().replaceAll("'", "''");
			System.out.println("content : " + content);
			// ' �� '' �� �ٲ�� �ν��� ��
			
			String sql = "update t_notice_list set ";
			sql += "nl_title = '" + title + "', ";
			sql += "nl_content = '" + content + "' ";
			sql += "where nl_num = " + num;
			System.out.println(sql);
			
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
			
		} catch (Exception e) {
			System.out.println("updateNotice() �޼ҵ忡�� ���� �߻�");
		} finally {
			close(stmt);
		}
		
		return result;
	}
	
	// ���� �� ���� �޼ҵ�
	public String getPrevTitle(int num) {
		Statement stmt = null;
		ResultSet rs = null;
		String prev = "";
		
		try {
			String sql = "select nl_title from t_notice_list where nl_num = " + (num - 1);
			System.out.println(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if (rs.next())	prev = rs.getString(1);
			// ResultSet�� ����� Select�� ���� ����� ������� 1�྿ �Ѱܼ� ���࿡ ���� ���� ������ true, ���� ���� ������ false�� ��ȯ�ϴ� �Լ� 
			// while(rs.next())�� �ϰԵǸ� �� ������ ���ư� �� ���� 1�྿ �Ѱ��ִٰ� ���̻� ���� ������ while���� ������ ��
			// getString() �Լ��� �ش� ������ �����ִ� �����͸� String������ �޾ƿ�
			// ex) rs.getString(2)�� �ϰԵǸ� 2��° �����ִ� �����͸� �������� ��

		} catch (Exception e) {
			System.out.println("getPrevTitle() �޼ҵ忡�� ���� �߻�");
		} finally {
			close(rs);
			close(stmt);
		}
		
		return prev;
	}
	
	// ���� �� ���� �޼ҵ�
	public String getNextTitle(int num) {
		Statement stmt = null;
		ResultSet rs = null;
		String next = "";
		
		try {
			String sql = "select nl_title from t_notice_list where nl_num = " + (num + 1);
			System.out.println(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if (rs.next())	next = rs.getString(1);
			
		} catch (Exception e) {
			System.out.println("getNextTitle() �޼ҵ忡�� ���� �߻�");
		} finally {
			close(rs);
			close(stmt);
		}
		
		return next;
	}
}
