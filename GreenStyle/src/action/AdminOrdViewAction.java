package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.util.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;

public class AdminOrdViewAction implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String olid = request.getParameter("olid");
		int num = Integer.parseInt(request.getParameter("num"));
		System.out.println("AdminOrdViewAction�� num : " + num);
		String where = " ol_id = '" + olid + "' ";
		int cpage = 1;	// ���� ������ ��ȣ�� ������ ����
		int limit = 10;	// �� ���������� ������ �������� ����, ������ ũ��
		
		AdminOrdService adminOrdService = new AdminOrdService();
		OrdInfo ordInfo = adminOrdService.getOrd(num);
		request.setAttribute("ordInfo", ordInfo);	// set �ʼ�
		
		ArrayList<OrdInfo> getOrdList = adminOrdService.getOrdList2(where, cpage, limit);	// ����
		request.setAttribute("getOrdList", getOrdList);		// set �ʼ�
		
		if (ordInfo == null) {	// �Խñ� ������ ������
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
		request.setAttribute("olid", olid);
		request.setAttribute("ordInfo", ordInfo);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/admin/ord/ordView2.jsp");
		
		return forward;
	}
}
