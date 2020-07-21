package action;

import java.io.*;
import java.util.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;

public class FaqProcAction implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String wtype = request.getParameter("wtype");
		
		int result = 0, nlnum = 0;
		String lnk = "", flResult = "";
		FaqService faqService = new FaqService();
		
		if (wtype.equals("in")) {	// �������� ����̸�
			flResult = faqService.insertFaq(request);
			result = Integer.parseInt(flResult.substring(0, flResult.indexOf(':')));
			nlnum = Integer.parseInt(flResult.substring(flResult.indexOf(':') + 1));
			lnk = "view.faq?cpage=1&num=" + nlnum;
			
		} else if (wtype.equals("up")) {	// �������� �����̸�
			result = faqService.updateFaq(request);
			
			String num = request.getParameter("num");
			String cpage = request.getParameter("cpage");	 
			String schType = request.getParameter("schType");
			String keyword = request.getParameter("keyword");

			String args = "?num=" + num + "&cpage=" + cpage + "&schType=" + schType + "&keyword=" + keyword;
			lnk = "view.faq" + args;
			
		} else if (wtype.equals("del"))	{	// �������� �����̸�
			result = faqService.deleteFaq(request);
			
			lnk = "list.faq";
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