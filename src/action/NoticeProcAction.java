package action;

import java.io.*;
import java.util.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;

public class NoticeProcAction implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String wtype = request.getParameter("wtype");
		
		int result = 0, nlNum = 0;
		String lnk = "", nlResult = "";
		NoticeService noticeService = new NoticeService();
		
		if (wtype.equals("in")) {	// �������� ����̸�
			nlResult = noticeService.insertNotice(request);
			System.out.println("Proc�� nlResult : " + nlResult);
			result = Integer.parseInt(nlResult.substring(0, nlResult.indexOf(':')));
			// indexOf() : ������ ������(':')�� ��ġ�� ã�� �ε����� ����(������ -1)
			System.out.println("Proc�� result : " + result);
			nlNum = Integer.parseInt(nlResult.substring(nlResult.indexOf(':') + 1));
			System.out.println("Proc�� nlNum :  " + nlNum);
			lnk = "view.notice2?cpage=1&num=" + nlNum;
			
		} else if (wtype.equals("up")) {	// �������� �����̸�
			result = noticeService.updateNotice(request);
			System.out.println(result);
			String num = request.getParameter("num");
			String cpage = request.getParameter("cpage");
			String schType = request.getParameter("schType");
			String keyword = request.getParameter("keyword");
			
			String args = "?num=" + num + "&cpage=" + cpage + "&schType=" + schType + "&keyword=" + keyword;
			lnk = "view.notice2" + args;
			System.out.println(lnk);
		
		} else if (wtype.equals("del")) {	// �������� �����̸�
			result = noticeService.deleteNotice(request);
			
			lnk = "list.notice2";
		}
		
		ActionForward forward = new ActionForward();
		if (result == 1) {
			forward.setRedirect(true);	// sendRedirect ������� �̵���Ŵ
			// ���� ��û�� ���� URL1���� Ŭ���̾�Ʈ�� redirect�� URL2�� �����ϰ�, Ŭ���̾�Ʈ���� ���� ���ο� ��û�� �����Ͽ� URL2�� �ٽ� ��û�� ���� 
			// ���� ó�� ���´� ������ ��û������ ���̻� ��ȿ���� �ʰ� ��
			forward.setPath(lnk);
		} else {
			response.setContentType("html/text; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('�۾��� �����߽��ϴ�.');");
			out.println("history.back();");
			out.println("</script>");
		}
		
		return forward;
	}
}
