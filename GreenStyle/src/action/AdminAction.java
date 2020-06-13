package action;

import java.io.*;
import java.util.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;

public class AdminAction implements Action {
	private String command;
	
	public AdminAction(String command) { this.command = command; }
	
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("<script> alert('�����۵�'); </script>");
		System.out.println("���� �׼� ����");
		
		String lnk = "";
		AdminService adminService = new AdminService();
		ActionForward forward = new ActionForward();
		String uid = request.getParameter("uid");
		String pwd = request.getParameter("pwd");
		AdminInfo adminInfo = null;
		int result = 1;
		System.out.println("command : " + command);
		if (command.equals("/main.admin")) {		// �α����̸�		
			adminInfo = adminService.main(uid, pwd);
			if (adminInfo != null) { 
				 session = request.getSession();
		         // JSP�� �ٸ��� ���ǰ�ü�� ���� �����ؼ� ����ؾ� ��
		         session.setAttribute("adminInfo", adminInfo);
		         lnk = "/view.adminMain";
		      } else {	// �α��� ���н�
		    	  out.println("<script>");
			      out.println("alert('�۾�����');");
			      out.println("</script>");
			      lnk = "/admin/adminLoginForm.jsp";
		      }
			
		} else if(command.equals("/login.admin")) {	// �α���ȭ������ ���°Ÿ�
			lnk = "/admin/adminLoginForm.jsp";
		}
		
		if (result < 1) {
			out.println("<script>");
			out.println("alert('�۾��� �����߽��ϴ�.');");
			out.println("history.back();");
			out.println("</script>");
		}
		
		forward.setPath(lnk);
		return forward;
	}
}
