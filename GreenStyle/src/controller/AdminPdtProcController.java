package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import action.*;
import vo.*;

@WebServlet("*.pdtproc")
public class AdminPdtProcController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AdminPdtProcController() { super(); }
    
    
    protected void doProc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// ��û��Ŀ� ������� �ϰ������� ��û�� ó���ϱ� ���� �޼ҵ�
		request.setCharacterEncoding("utf-8");
		String RequestURI = request.getRequestURI();
		// '/mvcSite/list.notice'
		String contextPath = request.getContextPath();
		// '/mvcSite'
		String command = RequestURI.substring(contextPath.length());
		// '/list.notice'

		Action action = null;
		ActionForward forward = null;

		// ��û(url)�� ���� ActionŬ���� �ν��Ͻ� ���� �� excute()�޼ҵ� ���� if��
		// ��û(url)�� ���� ��ɺ� �޼ҵ����
		if (command.equals("/in.pdtproc")) {		// �۵��ȭ���̸�
			forward = new ActionForward();
			forward.setPath("/admin/pdt/AdminPdtForm.jsp?wtype=in");

		} else if (command.equals("/proc.pdtproc")) {	// ó���۾��̸�
		// �������� ���, ����, ���� ���� �۾��� ó��
			action = new AdminPdtProcAction();
			try {
				forward = action.execute(request, response);
			} catch(Exception e) { e.printStackTrace(); }

		} else if (command.equals("/up.pdtproc")) {		// �ۼ���ȭ���̸�
			action = new AdminPdtAction(command);
			try {
				forward = action.execute(request, response);
			} catch(Exception e) { e.printStackTrace(); }
		}

		// ��� ���� �� �̵��� �������� ���� if��
		if (forward != null) {
			if (forward.isRedirect()) {
				response.sendRedirect(forward.getPath());
				// history�� �׿� '�ڷ� ����'�� ������ �̵�
			} else {
				RequestDispatcher dispatcher = 
					request.getRequestDispatcher(forward.getPath());
				dispatcher.forward(request, response);
				// history�� ������ �ʾ� '�ڷ� ����'�� �Ұ����� �̵�(url�Һ�)
				// request�� response ��ü�� �����Ͽ� ����� �� ����
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
