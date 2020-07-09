package svc;

import static db.JdbcUtil.*;
import java.sql.Connection;
import java.util.*;
import javax.servlet.http.*;
import dao.*;
import svc.*;
import vo.*;

public class NoticeService {
	// ���������� ��� ������ �����ϴ� �޼ҵ�
	public int getListCount(String where) {
		int rcount = 0;
		NoticeDAO noticeDAO = NoticeDAO.getInstance();	// getInstance() : �̱��������̸�, �ϳ��� �ν��Ͻ��� ������ �����ؼ� ����
		Connection conn = getConnection();
		noticeDAO.setConnection(conn);
		rcount = noticeDAO.getListCount(where);
		close(conn);
		
		return rcount;
	}
	
	// ���������� ����� ArrayList���·� �����ϴ� �޼ҵ�
	public ArrayList<NoticeInfo> getNoticeList(String where, int cpage, int limit) {
		ArrayList<NoticeInfo> noticeList = new ArrayList<NoticeInfo>();
		NoticeDAO noticeDAO = NoticeDAO.getInstance();
		Connection conn = getConnection();
		noticeDAO.setConnection(conn);
		noticeList = noticeDAO.getNoticeList(where, cpage, limit);
		close(conn);
		
		return noticeList;
	}
	
	// �������� ��ϰ���� �۹�ȣ�� String������ �����ϴ� �޼ҵ�
	public String insertNotice(HttpServletRequest request) {
		NoticeDAO noticeDAO = NoticeDAO.getInstance();
		Connection conn = getConnection();
		noticeDAO.setConnection(conn);
		String nlResult = noticeDAO.insertNotice(request);
		int result = Integer.parseInt(nlResult.substring(0, nlResult.indexOf(':')));
		
		if (result == 1) { commit(conn); } 
		else { rollback(conn); }
		close(conn);
		
		return nlResult;
	}
	
	// �������� �Խñۿ� ���� ��������� int������ �����ϴ� �޼ҵ� 
	public int updateNotice(HttpServletRequest request) {
		NoticeDAO noticeDAO = NoticeDAO.getInstance();
		Connection conn = getConnection();
		noticeDAO.setConnection(conn);
		int result = noticeDAO.updateNotice(request);
		
		if (result == 1) { commit(conn); } 
		else { rollback(conn); }
		close(conn);
		
		return result;
	}
	
	// �������� �Խñ��� ������ �� ����� int������ �����ϴ� �޼ҵ� 
	public int deleteNotice(HttpServletRequest request) {
		NoticeDAO noticeDAO = NoticeDAO.getInstance();
		Connection conn = getConnection();
		noticeDAO.setConnection(conn);
		int result = noticeDAO.deleteNotice(request);
		
		if (result == 1) { commit(conn); } 
		else { rollback(conn); }
		close(conn);
		
		return result;
	}
		
	
	// Ư�� �������� �� �ϳ��� �����ϴ� �޼ҵ�
	public NoticeInfo getNotice(int num) {
		NoticeInfo noticeInfo = null;
		NoticeDAO noticeDAO = NoticeDAO.getInstance();
		Connection conn = getConnection();
		noticeDAO.setConnection(conn);
		noticeInfo = noticeDAO.getNotice(num);
		close(conn);
		
		return noticeInfo;
	}
	
	// �������� �Խñ��� ��ȸ���� ������Ų �� ����� int������ �����ϴ� �޼ҵ�
	public int updateRead(int num) {
		NoticeDAO noticeDAO = NoticeDAO.getInstance();
		Connection conn = getConnection();
		noticeDAO.setConnection(conn);
		int result = noticeDAO.updateRead(num);
		
		if (result == 1) { commit(conn); } 
		else { rollback(conn); }
		close(conn);
		
		return result;
	}
	
	public String getPrevTitle(int num) {
		NoticeDAO noticeDAO = NoticeDAO.getInstance();
		Connection conn = getConnection();
		noticeDAO.setConnection(conn);
		String prev = noticeDAO.getPrevTitle(num);
		close(conn);
		return prev;
	}
	
	public String getNextTitle(int num) {
		NoticeDAO noticeDAO = NoticeDAO.getInstance();
		Connection conn = getConnection();
		noticeDAO.setConnection(conn);
		String next = noticeDAO.getNextTitle(num);
		close(conn);
		return next;
	}
}