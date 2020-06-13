package action;

import java.util.*; // ArrayList ����Ϸ���
import javax.servlet.http.*; // request, response ����Ϸ���
import svc.*;
import vo.*;

public class AdminPdtAction implements Action {
	private String command;
	
	public AdminPdtAction(String command) { this.command = command; }
	// uri�� ���ϸ� �ش��ϴ� ���ڿ��� ������� command�� �����
	
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		System.out.println("AdminPdtAction�׼����� ����");
		
		String lnk = "";
		AdminPdtService adminpdtService = new AdminPdtService();
		System.out.println("adminpdtService���� ����");
		String where = "";
		String condition = "";
		String schType = request.getParameter("schType");	// �˻� ����
		String keyword = request.getParameter("keyword");	// �˻���
		
		if (command.equals("/list.adminpdt")) {			// ��ǰ��� ȭ���̸�
			ArrayList<PdtInfo> adminpdtList = new ArrayList<PdtInfo>();
			lnk = "/admin/pdt/adminPdtList.jsp";							

			if (schType != null && !schType.equals("") && 
					keyword != null && !keyword.equals("")) {
					if (schType.equals("id")) {	// ��ǰ���̵� �˻�
						where = " and (pl_id like '%" + keyword + "%')";
					} else if(schType.equals("name")){	// ��ǰ�̸� �˻�
						where = " and (pl_name like '%" + keyword + "%')";
					}
			}
			
		if(request.getParameter("cond") != null) {
			if (request.getParameter("cond").equals("b")) {
				 where += " order by pl_salecnt desc";
				 condition = "b";
			} 
		} else {
			where += "order by pl_id ";
			condition = "";
		}
			
			int cpage = 1;	// ���� ������ ��ȣ�� ������ ����
			int limit = 10;	// �� ���������� ������ �������� ����, ������ ũ��
			
			if (request.getParameter("cpage") != null) {
			// �޾ƿ� ������ ��ȣ�� ������ String���� int�� �����ͷ� ����
				cpage = Integer.parseInt(request.getParameter("cpage"));
			}
			adminpdtList = adminpdtService.getAdminPdtList(request, where, cpage, limit);
			String plid = request.getParameter("plid");
			int rcount = adminpdtService.getPdtCount(where);		
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
			pageInfo.setSchType(schType);
			pageInfo.setKeyword(keyword);
			request.setAttribute("pageInfo", pageInfo);
			request.setAttribute("adminpdtList", adminpdtList);
			request.setAttribute("plid", plid);
			request.setAttribute("condition", condition);
			
		} else if (command.equals("/view.adminpdt")) {	// ��ǰ �󼼺����̸�
			lnk = "admin/pdt/adminPdtView.jsp";
			PdtInfo pdtInfo = adminpdtService.getPdt(request.getParameter("plid"));
			request.setAttribute("pdtInfo", pdtInfo);
		
		} else if (command.equals("/up.pdtproc") ) {
			lnk = "/admin/pdt/AdminPdtForm.jsp?wtype=up";
			PdtInfo pdtInfo = adminpdtService.getPdt(request.getParameter("plid"));
			request.setAttribute("pdtInfo", pdtInfo);
		}
		ActionForward forward = new ActionForward();
		forward.setPath(lnk);
		
		return forward;
		}
	}
