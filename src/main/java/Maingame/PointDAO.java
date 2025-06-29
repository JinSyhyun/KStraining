package Maingame;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import DataClass.GamePoint;
import User.DBConnectionMgr;

public class PointDAO {

	private DBConnectionMgr pool;
	private PointDTO _PointDTO = new PointDTO();

	public PointDAO() {
		try {
			pool = DBConnectionMgr.getInstance();
			System.out.println("DB연결 성공");
		} catch (Exception e) {
			System.out.println("Error : Ŀ�ؼ� ���� ����");
		}
	}

	public DBConnectionMgr getPool() {
		return pool;
	}

	// POINTINFO 데이터 불러오기
	public GamePoint findById(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = pool.getConnection();
			String sql = "SELECT * FROM POINTINFO WHERE member_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				GamePoint gamePoint = _PointDTO.getGamePoint(rs);
				return gamePoint;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt, rs);
		}
		return null;
	}

	public GamePoint updatePoint(String id, int point) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = pool.getConnection();
			String sql = "UPDATE POINTINFO SET point = ? WHERE member_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, point);
			pstmt.setString(2, id);
			pstmt.executeUpdate();

			return findById(id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt);
		}
		return null;
	}

}