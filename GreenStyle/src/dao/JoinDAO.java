package dao;

import java.sql.*;
import javax.servlet.http.HttpServletRequest;
import static db.JdbcUtil.*;

public class JoinDAO {
   private static JoinDAO joinDAO;
   private Connection conn;
   
   private JoinDAO() {}
   
   public static JoinDAO getInstance() {
	// JoinDAO�� �ν��Ͻ��� ���� �� �������� �ʰ� �ϳ��� �����ϰ� �ϱ� ���� �޼ҵ�
	   // �̱��� ������� �޸��� ���� ���� �۾�
      if (joinDAO == null) {      
    // JoindAO�� �ν��Ͻ��� ������
         joinDAO = new JoinDAO();
    // JoinDAO�� �ν��Ͻ��� ���Ӱ� ����
      } 
      // ������ �����ϴ� joinDAO �ν��Ͻ��� ������ �׳� �ִ� �ν��Ͻ��� ����
      
      return joinDAO;
   }
   
   public void setConnection(Connection conn) {
	// JoinDAO Ŭ�������� �ϳ��� Connection���� ����ϱ� ���� ������� �޼ҵ�
      this.conn = conn;
   }
   
   public int setMemberJoin(HttpServletRequest request) {
      // ȸ������ ó���� ���� DB�۾��� �ϴ� �޼ҵ�
	  // ���Խ� �߿��� �����͵��� ��� ���� request��ü�� �Ű������� �޾� ��
      int result = 0;	
      // DB�۾� �� ������ ���� ���ڵ� ������ ������ ����
      Statement stmt = null;	
      
      try {
    	  // request��ü�� ��뿡�� ����ó���� �ʿ��ϹǷ� try�� �ȿ��� �۾�
         request.setCharacterEncoding("utf-8");
         
         String uid       = request.getParameter("uid").toLowerCase().trim();
         String pwd       = request.getParameter("pwd1").toLowerCase().trim();
         String uname    = request.getParameter("uname").trim();
         String by       = request.getParameter("by");
         String bm       = request.getParameter("bm");
         String bd       = request.getParameter("bd");
         String p1       = request.getParameter("p1");
         String p2       = request.getParameter("p2");
         String p3       = request.getParameter("p3");
         String e1       = request.getParameter("e1").trim();
         String e2       = request.getParameter("e2");
         String zip       = request.getParameter("zip");
         String addr1       = request.getParameter("addr1");
         String addr2       = request.getParameter("addr2");
         // request�� �޴� �����͵� �� ����ڰ� ���� �Է��ϴ� �����͵��� �ݵ��
         // trim()�޼ҵ�� ���ʿ��� ������ ������ �� �޾ƾ� ��
         // ���̵� ���� ������ ��ҹ��� ������ ���� �ʴ� ��� �ҹ��ڷ� ��ȯ�Ͽ� ����

         String birth   = by + "-" + bm + "-" + bd;
         String phone    = p1 + "-" + p2 + "-" + p3;
         String email   = e1 + "@" + e2;
         
         String sql = "insert into t_member_list (ml_id, ml_pwd, ml_name, ml_birth, ml_phone, ml_email, ml_point) values ";
         sql+= "('" + uid + "', '" + pwd + "', '" + uname +  "', '" + birth + "', '" + phone + "', '" + email + "', " + 1000 + ")";
         stmt = conn.createStatement();
         result = stmt.executeUpdate(sql);
         
         result = 0;
         sql = "insert into t_member_point (ml_id, mp_use, mp_point, mp_content, mp_balance) values ";
         sql += "('" + uid + "', '" + "s" + "', " + 1000 +  " , '" + "�������ϱ�" + "', " + 1000 + ")";
         result = stmt.executeUpdate(sql);
         System.out.println(sql);
         
         
         if (zip != null && !zip.equals("")) {
        	 result = 0;
        	 sql = "insert into t_member_addr (ma_isbasic, ml_id, ma_zip, ma_addr1, ma_addr2) values ";
	         sql+= "('" + "y" + "', '" + uid + "', '" + zip +  "', '" + addr1 + "', '" + addr2 + "')";
	         result = stmt.executeUpdate(sql);
	         System.out.println(sql);
         }
         
         
         // insert���̹Ƿ� executeUpdate()�޼ҵ�� �����
         // ����� insert�� ���ڵ��� ������ �Ϲ������� 1�� ���;� ����
                  
      } catch (Exception e) {
         System.out.println("setMemberJoin() �޼ҵ� ����");
         e.printStackTrace();
      } finally {
         try {
            close(stmt);
         } catch (Exception e) {
            e.printStackTrace();
         } 
      }
      
      return result;
   }

}