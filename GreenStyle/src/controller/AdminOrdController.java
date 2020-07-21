package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.*;
import vo.ActionForward;
import vo.*;

@WebServlet("*.ord2")
public class AdminOrdController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public AdminOrdController() {
        super();
    }
    
    protected void doProc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("utf-8");
    	
    	/*HttpSession session = request.getSession();
		AdminInfo admin = (AdminInfo)session.getAttribute("adminInfo");
		String alid = admin.getAl_id();
		if (alid == null || alid.equals("")) {
			PrintWriter out = response.getWriter();
			response.setContentType("html/text; charset=utf-8");
			out.println("<script>");
			out.println("alert('�α��� �Ŀ� ����Ͻ� �� �ֽ��ϴ�.');");
			out.println("location.href='loginForm.jsp';");
			out.println("</script>");
		}*/
    	
		String RequestURI = request.getRequestURI(); 
		String contextPath = request.getContextPath(); 
		String command = RequestURI.substring(contextPath.length()); 
		
		Action action = null;
		ActionForward forward = null;
		System.out.println(command);
		if (command.equals("/list.ord2")) {	// ���ȭ���̸�
    		action = new AdminOrdAction(command);
    		try {
    			forward = action.execute(request, response);
    		} catch (Exception e) { e.printStackTrace(); }
    	
		} else if (command.equals("/ordView.ord2")) {	// �����۾��̸�
    		action = new AdminOrdViewAction();
    		try {
    			forward = action.execute(request, response);
    		} catch (Exception e) { e.printStackTrace(); }
    	
		} else if (command.equals("/ordUp.ord2")) {	// �����۾��̸�
			System.out.println("AdminOrdController�� ordUp ����");
    		action = new AdminOrdViewAction();
    		try {
    			forward = action.execute(request, response);
    			forward.setPath("/admin/ord/ordForm2.jsp?wtype=up");
    		} catch (Exception e) { e.printStackTrace(); }
    	
		} else if (command.equals("/proc.ord2")) {	// ó���۾��̸�
    	// �������� ���, ����, ���� ���� �۾��� ó��
    		action = new AdminOrdProcAction();	// action ������ NoticeProcAction ���Ϸ� �̵�
    		try {
    			forward = action.execute(request, response);
    		} catch (Exception e) { e.printStackTrace(); }
    	
		}
		
		if (forward != null) {
			if (forward.isRedirect()) {
				response.sendRedirect(forward.getPath());
			} else {
				RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());	
				dispatcher.forward(request, response);
			}
		}
	}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProc(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProc(request, response);
	}
}
