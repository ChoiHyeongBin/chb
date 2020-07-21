package controller;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vo.MemberInfo;
import svc.LoginService;

@WebServlet("/login2")
// �α��� ��û�� �۵��Ǵ� ��Ʈ�ѷ��� ���� �۾��� �� ��������(svc��Ű��)�� �����ϰ�,
// �۾� �� ����� ������� ��û�� ���� ������ ó����
public class LoginServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;
       
    public LoginServlet() {
        super();
    }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      request.setCharacterEncoding("utf-8");
      String uid = request.getParameter("uid");
      String pwd = request.getParameter("pwd");
      LoginService loginService = new LoginService();
      MemberInfo memberInfo = loginService.getLoginMember(uid, pwd);
      // MemberInfo ������ memberInfo �ν��Ͻ��� loginService���� ȣ��, �Ű������� (uid, pwd) : ������Ÿ�� String
      // �α��� ó�� �� ���̵�(��ü)�� �޾� ��(������ ��ü��, ���н� null�� �Ѿ��)
      int result = 0;
      
      if (memberInfo != null) {   // �α��� ������
         HttpSession session = request.getSession();
         // JSP�� �ٸ��� ���ǰ�ü�� ���� �����ؼ� ����ؾ� ��
         session.setAttribute("memberInfo", memberInfo);
         response.sendRedirect("main");
         result = loginService.memberLogin(request);
      
      } else if (uid == "") {      // ���̵� ���������
         response.setContentType("text/html; charset=utf-8");
         PrintWriter out = response.getWriter();
         out.println("<script>");
         out.println("alert('���̵� �Է����ּ���.');");
         out.println("history.back();");
         out.println("</script>");
      
      } else if (pwd == "") {      // ��й�ȣ�� ���������
         response.setContentType("text/html; charset=utf-8");
         PrintWriter out = response.getWriter();
         out.println("<script>");
         out.println("alert('��й�ȣ�� �Է����ּ���.');");
         out.println("history.back();");
         out.println("</script>");
         
      } else {   // �α��� ���н�
         response.setContentType("text/html; charset=utf-8");
         PrintWriter out = response.getWriter();
         out.println("<script>");
         out.println("alert('���̵�� ��й�ȣ�� Ȯ���ϼ���.');");
         out.println("history.back();");
         out.println("</script>");
      }
      
   }
}