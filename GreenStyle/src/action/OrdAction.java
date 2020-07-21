package action;

import java.io.PrintWriter;
import java.util.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;

public class OrdAction implements Action {
	private String command;
	
	public OrdAction(String command) { this.command = command; }
	
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession();
		MemberInfo mem = (MemberInfo)session.getAttribute("memberInfo");
		String mlid = mem.getMl_id();
		
		String lnk = "";
		OrdService ordService = new OrdService();
		ActionForward forward = new ActionForward();
		
		int result = 1;
		if (command.equals("/cartIn.ord")) {		// ��ٱ��� �߰��̸�
			lnk = "cart.ord";
			result = ordService.cartIn(request);
			forward.setRedirect(true);
			// dispatcher����� �ƴ�  sendRedirect������� �̵��ϰ� ��
		
		} else if (command.equals("/cart.ord")) {	// ��ٱ��� ȭ���̸�
			lnk = "ord/cart.jsp";
			ArrayList<CartInfo> cartList = ordService.getCartList(mlid);
			request.setAttribute("cartList", cartList);
			
		} else if (command.equals("/cartDel.ord")) {// ��ٱ��� �����̸�
			lnk = "cart.ord";
			result = ordService.cartDel(request, mlid);
			forward.setRedirect(true);
			
		} else if (command.equals("/cartUp.ord")) {// ��ٱ��� �����̸�
			lnk = "cart.ord";
			result = ordService.cartUp(request, mlid);
			forward.setRedirect(true);
			
		} else if (command.equals("/form.ord")) {	// ������ ȭ���̸�
			System.out.println("OrdAction�� ������ ȭ��");
			lnk = "/ord/ordForm.jsp";
			ArrayList<CartInfo> preOrdList = ordService.getPreOrdList(request, mlid);
			ArrayList<MemberCouponInfo> memberCoupon = ordService.getCouponList(request, mlid);
			request.setAttribute("preOrdList", preOrdList);
			request.setAttribute("memberCoupon", memberCoupon);
			// ������ ��ǰ(��)�� ����� ArrayList������ �޾� ��
			
		} else if (command.equals("/proc.ord")) {	// ����ó�� ����̸�
			System.out.println("OrdAction�� ����ó�� ����۵�");
			lnk = "list.ord";
			result = ordService.ordProcess(request, mlid);
			forward.setRedirect(true);
			// dispatcher����� �ƴ� sendRedirect������� �̵��ϰ� ��
		
		} else if (command.equals("/list.ord")) {	// ���Ÿ�� ȭ���̸�
			System.out.println("OrdAction�� ���Ÿ�� ȭ��");
			System.out.println("proc������ ����� ����");
			lnk = "/ord/ordList.jsp";	
			int cpage = 1;	// ���� ������ ��ȣ�� ������ ����
			int limit = 1;	// �� ���������� ������ �������� ����, ������ ũ��
			if (request.getParameter("cpage") != null) {
			// �޾ƿ� ������ ��ȣ�� ������ String���� int�� �����ͷ� ����
				cpage = Integer.parseInt(request.getParameter("cpage"));
			}
			
			int rcount = ordService.getOrdListCount(mlid);
			// Ư�� ȸ��(mlid)�� ������ ����� ���ڵ� ������ ����
			ArrayList<OrdInfo> ordList = ordService.getOrdList(request, mlid, cpage, limit);
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
		
		} else if (command.equals("/view.ord")) {	// ���Ż󼼺��� ȭ���̸�
			System.out.println("/view.ord ����");
			lnk = "/ord/ordView.jsp";
			int cpage = Integer.parseInt(request.getParameter("cpage"));
			// Ư�� ȸ��(mlid)�� ������ ����� ���ڵ� ������ ����
			String olid = request.getParameter("olid");
			
			OrdInfo ordInfo = ordService.getOrdInfo(request, olid, mlid);	// request �߰�
			request.setAttribute("ordInfo", ordInfo);	// �߰�
		
		} else if (command.equals("/coupon.ord")) {		// �����˾� ȭ���̸�
			lnk ="/ord/findCoupon.jsp?visited=y";
			ArrayList<MemberCouponInfo> memberCoupon = ordService.getCouponList(request, mlid);
			request.setAttribute("MemberCoupon", memberCoupon);
		} else if (command.equals("/ordcancel.ord")) {	// �ֹ���� ����̸�
			System.out.println("/ordcancel.ord �׼� ����");
			lnk = "main";
			String olid = request.getParameter("olid");
			String olbuyer = request.getParameter("olbuyer");
			
			result = ordService.orderCancel(request, olid, olbuyer);
			forward.setRedirect(true);
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
