package action;

import java.io.*;
import java.util.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;

public class NoticeViewAction implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		int num = Integer.parseInt(request.getParameter("num"));
		// �۹�ȣ�� int������ ��ȯ�Ͽ� ����
		
		NoticeService noticeService = new NoticeService();
		
		int result = noticeService.updateRead(num);
		// �۹�ȣ�� �ش��ϴ� ���� ��ȸ���� 1 ������Ŵ
		
		NoticeInfo noticeInfo = noticeService.getNotice(num);
		// �۹�ȣ�� �ش��ϴ� ���� �����͸� NoticeInfo�� �ν��Ͻ��� �޾ƿ�
		
		int maxNotice = noticeService.getListCount("");
		// ���� �ִ� ������ ������
		
		String prev = noticeService.getPrevTitle(num);
		String next = noticeService.getNextTitle(num);
		
		if (noticeInfo == null) { // �Խñ� ������ ������
			response.setContentType("html/text; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('�߸��� ��η� �����̽��ϴ�.');");
			out.println("history.back();");
			out.println("</script>");
		}
		
		request.setAttribute("cpage", request.getParameter("cpage"));
		request.setAttribute("schType", request.getParameter("schType"));
		request.setAttribute("keyword", request.getParameter("keyword"));
		request.setAttribute("maxNotice", maxNotice);
		request.setAttribute("prev", prev);
		request.setAttribute("next", next);
		request.setAttribute("noticeInfo", noticeInfo);
		// �ʿ��� �����͵��� request��ü�� �Ӽ����� ��Ƽ� ������
		// ��, Dispatcher����� ��쿡�� ��밡��
		
		ActionForward forward = new ActionForward();
		forward.setPath("/board/noticeView.jsp");

		return forward;
	}
}
