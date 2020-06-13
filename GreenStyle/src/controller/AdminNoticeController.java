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

@WebServlet("*.adminNotice")
public class AdminNoticeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    public AdminNoticeController() {
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
		System.out.println("AdminNotice Command : " + command);
		
		Action action = null;
		ActionForward forward = null;
	
		if (command.equals("/list.adminNotice")) { // �������� ���
    		action = new AdminNoticeAction();
    		try {
    			forward = action.execute(request, response);
    		} catch (Exception e) { e.printStackTrace(); }
    		
   		
    	} else if (command.equals("/view.adminNotice")) { // �������� �󼼺���		
    		action = new AdminNoticeViewAction();
    		try {
    			forward = action.execute(request, response);
    		} catch (Exception e) { e.printStackTrace(); }
    	
    	} else if (command.equals("/up.adminNotice")) { // �������� ���� �������� �̵�
        		action = new AdminNoticeViewAction();
        		
        		try {
        			forward = action.execute(request, response);
        		} catch (Exception e) { 
        			e.printStackTrace(); 
        		}
        		String wtype = "up";
        		request.setAttribute("wtype", wtype);
        		
        		forward.setPath("/admin/board/noticeForm.jsp");
    	
    	} else if (command.equals("/proc.adminNotice")) { // �������� ó�� �۾�
    		action = new AdminNoticeProcAction();
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
