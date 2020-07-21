package controller;

import java.io.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import svc.JoinService;


@WebServlet("/join")
// ȸ������ ó���� ���� Ŭ����
public class JoinServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public JoinServlet() {
        super();
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		JoinService joinService = new JoinService();
		// JoinService�� �ν��Ͻ� joinService ���� �� ����
		// ���� �۾��� ó���� Ŭ������ ȣ��
		
		int result = joinService.setMemberJoin(request);
		// joinService�ν��Ͻ� �ȿ� �ִ� setMemberJoin() �޼ҵ带 ȣ��
		// �Ű������� request��ü�� ����(request��ü ���� �Ķ���͵鵵 ���� ����)
		if (result == 1) {	// ������ ������ ���� ���ڵ尡 1�̸�(ȸ�������� ����������, �� ���ڵ尡 �߰���)
			response.sendRedirect("member/loginForm.jsp");
		} else {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('ȸ�����Կ� �����߽��ϴ�.')");
			out.println("history.back();");
			out.println("</script>");
		}
		
		String uid = request.getParameter("uid").toLowerCase().trim();
		String pwd = request.getParameter("pwd1").toLowerCase().trim();
		String uname = request.getParameter("uname").trim();
		String by = request.getParameter("by");
		String bm = request.getParameter("bm");
		String bd = request.getParameter("bd");
		String p1 = request.getParameter("p1");
		String p2 = request.getParameter("p2");
		String p3 = request.getParameter("p3");
		String e1 = request.getParameter("e1").trim();
		String e2 = request.getParameter("e2");
		
		String birth = by + "-" + bm + "-" + bd;
		String phone = p1 + "-" + p2 + "-" + p3;
		String email = e1 + "@" + e2;
		
		
		
	}
}
