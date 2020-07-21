package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import action.*;
import vo.*;

@WebServlet("*.notice2")
public class NoticeController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public NoticeController() { super(); }
    // super() �޼ҵ�� �θ� Ŭ������ �����ڸ� ȣ���� �� ���

	protected void doProc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	// ��û��Ŀ� ������� �ϰ������� ��û�� ó���ϱ� ���� �޼ҵ�
		request.setCharacterEncoding("utf-8");
		String RequestURI = request.getRequestURI();
		// ������Ʈ �н� ����(������Ʈ�� ���ϰ�α��� ����)
		String contextPath = request.getContextPath();
		// ������Ʈ �н� ����
		String command = RequestURI.substring(contextPath.length());
		System.out.println("command : " + command);
		// command���� '/list.notice'�� ���� ��
		
		Action action = null;
		ActionForward forward = null;
		
		if (command.equals("/list.notice2")) {	// ��� ȭ���̸�
			action = new NoticeListAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				System.out.println("/list.notice2���� ���� �߻�");
				e.printStackTrace();
				// �޼ҵ尡 ���������� ���� ����� ȭ�鿡 ���
			}
		} else if (command.equals("/in.notice2")) {	// �� ���ȭ���̸�
			forward = new ActionForward();
			forward.setPath("/board/noticeForm.jsp?wtype=in");
			
		} else if (command.equals("/proc.notice2")) {	// ó�� �۾��̸�
		// �������� ���, ����, ���� ���� �۾��� ó��
			action = new NoticeProcAction();
			try {
				forward = action.execute(request, response);
			} catch(Exception e) { e.printStackTrace(); }
			
		} else if (command.equals("/view.notice2")) {	// ���� �۾��̸�
			action = new NoticeViewAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} else if (command.equals("/up.notice2")) {		// ���� �۾��̸�
			action = new NoticeViewAction();
			try {
				forward = action.execute(request, response);
				forward.setPath("/board/noticeForm.jsp?wtype=up");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// ��� ���� �� �̵��� �������� ���� if��
		if (forward != null) {
			if (forward.isRedirect()) {
				response.sendRedirect(forward.getPath());
				// history�� �׿� '�ڷ� ����'�� ������ �̵�
			} else {
				RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
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
