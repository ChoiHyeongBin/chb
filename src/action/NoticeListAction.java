package action;

import java.util.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;

public class NoticeListAction implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		String schType = request.getParameter("schType");	// �˻� ����
		String keyword = request.getParameter("keyword");	// �˻���
		String kind = request.getParameter("kind");			// �з�
		
		String where = "";	// ������ �߰��� ����
		String seltag = null;	// �������� �з�
		
		if (schType != null && !schType.equals("") && keyword != null && !keyword.equals("")) {
			if (schType.equals("tc")) {	// ���� + ���� �˻��� ���
				where = " and (nl_title like '%" + keyword + "%'";
				where += " or nl_content like '%" + keyword + "%'";
			} else if (schType.equals("writer")) {	// �۾��� �˻��� ���
				where = " and (al_name like '%" + keyword + "%')";
			} else {	// �����̳� ���� �˻��� ���
				where = " and nl_" + schType + " like '%" + keyword + "%' ";
			}
		}
		if (kind != null) {
			if (kind.equals("nta")) {	// �˸�/�ҽ� �̸�
				where = " and nl_kind = 'a' ";
				seltag = "t2";
			} else if (kind.equals("evt")) {	// �̺�Ʈ ��÷ �̸�
				where = " and nl_kind = 'b' ";
				seltag = "t3";
			}
		} else {	// ��ü �̸�
			seltag = "t1";
		}
		
		ArrayList<NoticeInfo> noticeList = new ArrayList<NoticeInfo>();
		// �������� ����� �����ϱ� ���� �÷������� NoticeInfo�� �ν��Ͻ��� �����
		int cpage = 1;	// ���� ������ ��ȣ�� ������ ����
		int limit = 15;	// �� ���������� ������ �������� ����, ������ ũ��
		
		if (request.getParameter("cpage") != null) {
		// �޾ƿ� ������ ��ȣ�� ������ String���� int�� �����ͷ� ����
			cpage = Integer.parseInt(request.getParameter("cpage"));
		}
		
		NoticeService noticeService = new NoticeService();
		int rcount = noticeService.getListCount(where);
		noticeList = noticeService.getNoticeList(where, cpage, limit);
		
		int mpage = (int)((double)rcount / limit  + 0.95);
		int spage = ( ( (int) ( (double) cpage / 10 + 0.9) ) - 1 ) * 10 + 1;
		int epage = spage + 10 - 1;
		if (epage > mpage)	epage = mpage;
				
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCpage(cpage);
		pageInfo.setEpage(epage);
		pageInfo.setMpage(mpage);
		pageInfo.setRcount(rcount);
		pageInfo.setSpage(spage);
		pageInfo.setSchType(schType);
		pageInfo.setKeyword(keyword);
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("noticeList", noticeList);
		// request��ü�� ���ο� �Ӽ��� �߰�
		// �̵��� ������ request��ü�� response��ü�� �����ϱ� ���� Dispatcher��� ���
		request.setAttribute("seltag", seltag);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/board/noticeList.jsp");
		
		return forward;
	}
}
