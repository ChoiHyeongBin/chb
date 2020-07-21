package action;

import java.io.PrintWriter;
import java.util.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;

public class NologOrdAction implements Action {
	private String command;
	public NologOrdAction(String command) { this.command = command; }
	
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		System.out.println("Nolog�׼����� ����");
		
		HttpSession session = request.getSession();
		String lnk = "";
		String olbuyer = (String)request.getAttribute("olbuyer");
		if (olbuyer == null) { olbuyer = ""; }
		
		NologOrdService nologordService = new NologOrdService();
		System.out.println("NologOrdService���� ����");
		ActionForward forward = new ActionForward();
		
		int result = 1;
		if (command.equals("/form.nologord")) {	// ������ ȭ���̸�
		lnk = "/ord/nologordForm.jsp";
		ArrayList<CartInfo> NologOrdList = nologordService.getnologOrdList(request);
		request.setAttribute("NologOrdList", NologOrdList);
		
		} else if (command.equals("/nologproc.nologord")) {	// ����ó�� ����̸�
			System.out.println("OrdAction�� ����ó�� ����۵�");
			String orderName = request.getParameter("orderName");
			System.out.println("orderName : " + orderName);
			lnk = "list.nologord";
			result = nologordService.nologordProcess(request);
			request.setAttribute("olbuyer", orderName); // ���⼭ olbuyer�� ����
		
		} else if (command.equals("/list.nologord")) {	// ���Ÿ�� ȭ���̸�
			System.out.println("OrdAction�� ���Ÿ�� ȭ��");
			System.out.println("proc������ ����� ����");
			lnk = "/ord/NologordList.jsp";	
			int cpage = 1;	// ���� ������ ��ȣ�� ������ ����
			int limit = 10;	// �� ���������� ������ �������� ����, ������ ũ��
			if (request.getParameter("cpage") != null) {
			// �޾ƿ� ������ ��ȣ�� ������ String���� int�� �����ͷ� ����
				cpage = Integer.parseInt(request.getParameter("cpage"));
			}
			int rcount = nologordService.getnologOrdListCount(olbuyer);
			// Ư�� ȸ��(mlid)�� ������ ����� ���ڵ� ������ ����
			ArrayList<OrdInfo> ordList = nologordService.getOrdList(request, olbuyer, cpage, limit);
			// Ư�� ȸ����(mlid) ������ ����� OrdInfo�� �ν��Ͻ��� ArrayList�� ����
			
			int mpage = (int)((double)rcount / limit + 0.95);
			int spage = (((int)((double)cpage / 10 + 0.9)) - 1) * 10 + 1;
			int epage = spage + 10 - 1;	
			if (epage > mpage)	epage = mpage;
			
			PageInfo pageInfo = new PageInfo();
			pageInfo.setCpage(cpage);	// ���� ������ ��ȣ
			pageInfo.setEpage(epage);	// ���� ������ ��ȣ(��Ϻ� ���� ������)
			pageInfo.setMpage(mpage);	// ��ü ������ ��
			pageInfo.setRcount(rcount);	// ��ü ���ڵ� ����
			pageInfo.setSpage(spage);	// ���� ������ ��ȣ(��Ϻ� ���� ������)
			
			request.setAttribute("ordList", ordList);
			request.setAttribute("pageInfo", pageInfo);
			request.setAttribute("olbuyer", olbuyer); // ���⼭�� olbuyer�� ����
		
		} else if (command.equals("/nologlist.nologord")) {	// �˻��̸�
			System.out.println("Nologlist�� ����");
			lnk = "/ord/nologSchList.jsp";
			olbuyer = request.getParameter("orderName");
			String olids = request.getParameter("orderId");
			int rcount = nologordService.getnologOrdListCount(olbuyer);
			System.out.println("�� ����Ʈ ã��");
			ArrayList<OrdInfo> nologordList = nologordService.getNologOrdList(request, olbuyer, olids);
			System.out.println("�� ����Ʈ ã��");
			request.setAttribute("ordList", nologordList);
			request.setAttribute("olbuyer", olbuyer); // ���⼭�� olbuyer�� ����
			request.setAttribute("olids", olids); // ���⼭�� olbuyer�� ����
		
		} else if (command.equals("/view.nologord")) {	// ���Ż󼼺��� ȭ���̸�
			System.out.println("view�� ����");
			lnk = "/ord/nologordView.jsp";
			String olid = request.getParameter("olids");
			OrdInfo ordInfo = nologordService.getOrdInfo(request, request.getParameter("olid"));
			System.out.println("olid " + olid);
			request.setAttribute("ordInfo", ordInfo);
			System.out.println(ordInfo);
			
		} else if (command.equals("/ordcancel.nologord")) {	// ���Ż��� ����̸�
			result = nologordService.orderCancel(request);
			lnk = "main";
			
			String buyer = request.getParameter("olbuyer");
			String olids = request.getParameter("olids");
			System.out.println(buyer +  " // " + olids);
			
			
		}
		
		forward.setPath(lnk);
		if (result < 1) {
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
