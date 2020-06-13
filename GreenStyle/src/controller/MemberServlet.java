package controller;

import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import svc.*;
import vo.MemberInfo;


@WebServlet("/member")
// ȸ������ �۾�(����, Ż�� ��)�� ��û�� �޴� ����
public class MemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public MemberServlet() {super();}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		MemberService memberService = new MemberService();
		String kind = request.getParameter("kind");
		int result = 0;
		
		if (kind.equals("up")) {	// ȸ������ ������
			result = memberService.memberUpdate(request);
			// ������ ȸ���������� ���� request��ü�� �μ����Ͽ� setMemberUpdate()�޼ҵ� ȣ��
			// ȸ������ ���� �� ������ ���ڵ� ������ ������ result�� ����
		} else if (kind.equals("del")) { 	// ȸ�� Ż���
			result = memberService.memberDelete(request);
			HttpSession session = request.getSession();
			session.invalidate();
		}
		
		
		// ������ ȸ���������� ���� request��ü�� �μ����Ͽ� setMemberUpdate() �޼ҵ� ȣ��
		// ȸ�� ���� ���� �� ������ ���ڵ� ������ ������ result�� ����
		if (result == 1) {	
			response.sendRedirect("main");
		} else {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('ȸ������ ������ �����߽��ϴ�.')");
			out.println("history.back();");
			out.println("</script>");
		}
		
	}

}
