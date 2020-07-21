package action;

import java.io.*;
import java.util.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;

public class FaqViewAction implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		int num = Integer.parseInt(request.getParameter("num"));
		
		FaqService faqService = new FaqService();
		
		int result = faqService.updateRead(num);
		
		FaqInfo faqInfo = faqService.getFaq(num);
		// �۹�ȣ�� �ش��ϴ� ���� �����͸� FaqInfo�� �ν��Ͻ��� �޾ƿ�
		
		int maxFaq = faqService.getListCount("");
		// ���� �ִ� ������ ������
		
		if (faqInfo == null) { // �Խñ� ������ ������
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
		request.setAttribute("maxFaq", maxFaq);
		request.setAttribute("faqInfo", faqInfo);
		// �ʿ��� �����͵��� request��ü�� �Ӽ����� ��Ƽ� ������
		// ��, Dispatcher����� ��쿡�� ��밡��
		
		ActionForward forward = new ActionForward();
		forward.setPath("/board/faqView.jsp");

		return forward;
	}
}
