package db;

import java.sql.*;
import javax.sql.*;
import javax.naming.*;

//DB���� ���� �۾����� ���� �޼ҵ���� ������ ���� Ŭ����
//��� �޼ҵ�� public static���� �����Ͽ� �ν��Ͻ� ���� ������ �Ѵ�.
public class JdbcUtil {
	public static Connection getConnection() {
	// Connection��ü�� �����Ͽ� �����ϴ� �޼ҵ�
		Connection conn = null;

		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context)initCtx.lookup("java:comp/env");
			DataSource ds = (DataSource)envCtx.lookup("jdbc/MySQLDB");
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			// conn�� �̿��� �����۾��� �⺻������ Ʈ������� �ɾ���
			// �����۾��� commit�̳� rollback ���� ������� �����۾��� �������ؾ� ��
			// ��, select������ �����ϰ�, insert, update, delete �������� �ɾ���
		} catch(Exception e) {
			System.out.println("DB ���ؼ� ���� ����!!");
			e.printStackTrace();
		}

		return conn;
	}

	public static void close(Connection conn) {
		try {
			conn.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void close(Statement stmt) {
		try {
			stmt.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void close(ResultSet rs) {
		try {
			rs.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void commit(Connection conn) {
		try {
			conn.commit();
			System.out.println("commit success");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void rollback(Connection conn) {
		try {
			conn.rollback();
			System.out.println("rollback success");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
