package svc;

import static db.JdbcUtil.*;
import java.sql.Connection;
import java.util.*; 
import javax.servlet.http.*;
import dao.*;
import svc.*;
import vo.*;

public class PdtService {
	// ��ǰ ����� ArrayList�� �޾� �����ϴ� �޼ҵ�
	public ArrayList<PdtInfo> getPdtList(String where, int cpage, int limit) {
		ArrayList<PdtInfo> pdtList = new ArrayList<PdtInfo>();
		PdtDAO pdtDAO = PdtDAO.getInstance();
		Connection conn = getConnection();
		pdtDAO.setConnection(conn);
		pdtList = pdtDAO.getPdtList(where, cpage, limit);
		close(conn);
		return pdtList;
	}
	
	// �ϳ��� ��ǰ ������ PdtInfo�� �ν��Ͻ��� ���Ϲ޴� �޼ҵ�
	public PdtInfo getPdtInfo(String plid) {
		PdtInfo pdtInfo = new PdtInfo();
		PdtDAO pdtDAO = PdtDAO.getInstance();
		Connection conn = getConnection();
		pdtDAO.setConnection(conn);
		pdtInfo = pdtDAO.getPdtInfo(plid);
		close(conn);
		return pdtInfo;
	}
	
	// ��ǰ�� ��� ������ �����ϴ� �޼ҵ�
		public int getPdtCount(String where) {
			int rcount = 0;
			PdtDAO pdtDAO = PdtDAO.getInstance();
			Connection conn = getConnection();
			pdtDAO.setConnection(conn);
			rcount = pdtDAO.getPdtCount(where);
			close(conn);
			return rcount;
		}
}
