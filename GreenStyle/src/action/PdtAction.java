package action;

import java.util.*; // ArrayList ����Ϸ���
import javax.servlet.http.*; // request, response ����Ϸ���
import svc.*;
import vo.*;


public class PdtAction implements Action {
	private String command;
	
	public PdtAction(String command) { this.command = command; }
	// uri�� ���ϸ� �ش��ϴ� ���ڿ��� ������� command�� �����
	
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		// �˻��� ���� request�� ���� ĳ���� ���ڵ�(�ѱ� �˻� ������)
		
		String lnk = "";
		String condition = "";
		PdtService pdtService = new PdtService();
		String where = "";
		
		
		if (command.equals("/list.product")) {			// ��ǰ��� ȭ���̸�
			ArrayList<PdtInfo> pdtList = new ArrayList<PdtInfo>();
			if (request.getParameter("kind").equals("c")) {
			// �з���� ȭ���� ���
				lnk = "/pdt/pdtListCata.jsp";
				where = " and cs_id = '" + request.getParameter("csid") + "' ";
				
			} else if (request.getParameter("kind").equals("s")) {
			// �˻���� ȭ���� ���
				lnk = "/pdt/pdtListSearch.jsp";
				where = " and pl_name like '%" + request.getParameter("keyword") + "%' "; // �˻����� �ø������� and�� �������/ �귣��� or
			
			} else if (request.getParameter("kind").equals("d")) {
				lnk = "/pdt/pdtCodiCata.jsp";
				if (request.getParameter("csid").equals("cs07")) {
						where = " and cs_id = 'cs07'";
				} else if (request.getParameter("csid").equals("cs08")) {
						where = " and cs_id = 'cs08'";
				}
			}
			
			if(request.getParameter("cond") != null) {
				if (request.getParameter("cond").equals("b")) {
					 where += " order by pl_stock ";
					 condition = "b";
				} else if (request.getParameter("cond").equals("l")) {
					where += " order by pl_price ";
					condition = "l";
				} else if (request.getParameter("cond").equals("h")) {
					where += " order by pl_price desc ";
					condition = "h";
				}
			} else {
				where += " order by pl_name ";
				condition = "";
			}
			
			int cpage = 1;	// ���� ������ ��ȣ�� ������ ����
			int limit = 9;	// �� ���������� ������ �������� ����, ������ ũ��
			
			if (request.getParameter("cpage") != null) {
			// �޾ƿ� ������ ��ȣ�� ������ String���� int�� �����ͷ� ����
				cpage = Integer.parseInt(request.getParameter("cpage"));
			}
			pdtList = pdtService.getPdtList(where, cpage, limit);
			
			int rcount = pdtService.getPdtCount(where);		
			int mpage = (int)((double)rcount / limit + 0.95);
			int spage = (((int)((double)cpage / 10 + 0.9)) - 1) * 10 + 1;
			int epage = spage + 10 - 1;
			if (epage > mpage)	epage = mpage;
			
			PageInfo pageInfo = new PageInfo();
			pageInfo.setCpage(cpage);
			pageInfo.setEpage(epage);
			pageInfo.setMpage(mpage);
			pageInfo.setRcount(rcount);
			pageInfo.setSpage(spage);
			request.setAttribute("pageInfo", pageInfo);
			request.setAttribute("pdtList", pdtList);
			
		} else if (command.equals("/view.product")) {	// ��ǰ �󼼺����̸�
			lnk = "/pdt/pdtView.jsp";
			PdtInfo pdtInfo = pdtService.getPdtInfo(request.getParameter("plid"));
			request.setAttribute("pdtInfo", pdtInfo);
		}
		
		request.setAttribute("condition", condition);
		ActionForward forward = new ActionForward();
		forward.setPath(lnk);
		
		return forward;
	}
}
