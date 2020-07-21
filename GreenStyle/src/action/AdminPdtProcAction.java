package action;

import java.io.*;
import java.util.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;


public class AdminPdtProcAction implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String wtype = request.getParameter("wtype");
		System.out.println("AdminPdtProcAction����");
		System.out.println("wtype : " + wtype);
		int result = 0, plnum = 0;
		String lnk = "", plResult = "";
		AdminPdtService adminpdtService = new AdminPdtService();
		String plid = request.getParameter("plid");
		
		
		if (wtype.equals("in")) {	// ����̸�
			System.out.println("�۵��");
			plResult = adminpdtService.insertPdt(request);
			result = Integer.parseInt(plResult.substring(0, plResult.indexOf(':')));
			plnum = Integer.parseInt(plResult.substring(plResult.indexOf(':') + 1));
			lnk = "view.adminpdt?cpage=1&plid=" + plid;

		} else if (wtype.equals("up")) {	// �����̸�
			System.out.println("�ۼ���");
			result = adminpdtService.updatePdt(request);
			lnk = "view.adminpdt?cpage=1&plid=" + plid;
			
		} else if (wtype.equals("del")) {	// �����̸�
			PdtInfo pdtInfo = adminpdtService.getPdt(request.getParameter("plid"));
			request.setAttribute("pdtInfo", pdtInfo);
			result = adminpdtService.deletePdt(request);
			// ������ ���ڵ� ������ �޾� ��
			lnk = "list.adminpdt";
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
