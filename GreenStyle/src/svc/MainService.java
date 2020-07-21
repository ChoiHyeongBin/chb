package svc;

import static db.JdbcUtil.*;
import java.sql.Connection;
import java.util.*; 
import javax.servlet.http.*;
import dao.*;
import svc.*;
import vo.*;

public class MainService {
	// ��ǰ ����� ArrayList�� �޾� �����ϴ� �޼ҵ�
	public ArrayList<PdtInfo> getPdtList(String where) {
		ArrayList<PdtInfo> pdtList = new ArrayList<PdtInfo>();
		MainDAO mainDAO = MainDAO.getInstance();
		Connection conn = getConnection();
		mainDAO.setConnection(conn);
		pdtList = mainDAO.getPdtList();
		close(conn);
		return pdtList;
	}
	
	// �ϳ��� ��ǰ ������ PdtInfo�� �ν��Ͻ��� ���Ϲ޴� �޼ҵ�
	public PdtInfo getPdtInfo(String plid) {
		PdtInfo pdtInfo = new PdtInfo();
		MainDAO mainDAO = MainDAO.getInstance();
		Connection conn = getConnection();
		mainDAO.setConnection(conn);
		pdtInfo = mainDAO.getPdtInfo(plid);
		close(conn);
		return pdtInfo;
	}
}
