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
		
		int result = 0, nlnum = 0;
		String lnk = "", nlResult = "";
		NoticeService noticeService = new NoticeService();
		
		if (wtype.equals("in")) {	// �������� ����̸�
			nlResult = noticeService.insertNotice(request);
			result = Integer.parseInt(nlResult.substring(0, nlResult.indexOf(':')));
			nlnum = Integer.parseInt(nlResult.substring(nlResult.indexOf(':') + 1));
			lnk = "view.notice?cpage=1&num=" + nlnum;
			
		} else if (wtype.equals("up")) {	// �������� �����̸�
			result = noticeService.updateNotice(request);
			
			String num = request.getParameter("num");
			String cpage = request.getParameter("cpage");	 
			String schType = request.getParameter("schType");
			String keyword = request.getParameter("keyword");

			String args = "?num=" + num + "&cpage=" + cpage + "&schType=" + schType + "&keyword=" + keyword;
			lnk = "view.notice" + args;
			
		} else if (wtype.equals("del"))	{	// �������� �����̸�
			result = noticeService.deleteNotice(request);
			
			lnk = "list.notice";
		}
		
		ActionForward forward = new ActionForward();
		if (result == 1) {
			forward.setRedirect(true);	// sendRedirect ������� �̵���Ŵ
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