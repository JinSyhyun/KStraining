package Maingame;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import DataClass.GameResult;
import User.DBConnectionMgr;

public class ResultDAO {

	private DBConnectionMgr pool;
	private ResultDTO _resultDTO = new ResultDTO();

	public ResultDAO() {
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

	// RESULTINFO 데이터 불러오기
	public List<GameResult> findById(String id) {
		List<GameResult> gameResult = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = pool.getConnection();
			String sql = "SELECT * FROM RESULTINFO WHERE member_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
	            GameResult result = _resultDTO.getGameResult(rs);
	            if (result != null) {
	                gameResult.add(result);
	            }
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt, rs);
		}
		return gameResult;
	}

	// 게임 종료 유무 불러오기
	public boolean findGameActFlg(String id) {
		boolean gameActFlg = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
	        conn = pool.getConnection();
	        String sql = "SELECT game_act_flg FROM RESULTINFO WHERE member_id = ? ORDER BY rec_update_date DESC LIMIT 1";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, id);
	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	            GameResult gameResult = _resultDTO.getGameResult(rs);
	            if (gameResult != null) {
	                gameActFlg = gameResult.getGameActFlg();
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        pool.freeConnection(conn, pstmt, rs);
	    }

	    return gameActFlg;
	}
	
	// 최근 게임 불러오기
	public GameResult getRecentGame(String id) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    try {
	        conn = pool.getConnection();
	        String sql = "SELECT * FROM RESULTINFO WHERE member_id = ? ORDER BY game_no DESC LIMIT 1";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, id);
	        rs = pstmt.executeQuery();

	        if (rs.next()) {
            	GameResult gameResult = _resultDTO.getGameResult(rs);
                return gameResult;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(conn, pstmt, rs);
        }
	    return null;
	}
	
	
	// 테이블 초기화
	public void resetResult(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = pool.getConnection();
			String sql = "DELETE FROM RESULTINFO WHERE member_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, pstmt);
		}
	}

	// 게임 결과 업데이트
	public void insertResult(GameResult result) {
		Connection conn = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		
		String sql1 = "INSERT INTO RESULTINFO (member_id, game_no, hidden_number, answer_number, result_content, game_act_flg) "
				+ "VALUES (?, ?, ?, ?, ?, ?)";
		String sql2 = "UPDATE MEMBERINFO SET rec_update_date = now() WHERE member_id = ?";
		
		try {
			conn = pool.getConnection();
			

			pstmt1 = conn.prepareStatement(sql1);
			pstmt1.setString(1, result.getId());
			pstmt1.setInt(2, result.getGameNo());
			pstmt1.setString(3, result.getHiddenNum());
			pstmt1.setString(4, result.getAnswerNum());
			pstmt1.setString(5, result.getResultContent());
			pstmt1.setInt(6, result.getGameActFlg() ? 1 : 0);
			pstmt1.executeUpdate();
			
			pstmt2 = conn.prepareStatement(sql2);
			pstmt2.setString(1, result.getId());
			pstmt2.executeUpdate();
			
			System.out.println("DB INSERT 성공");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error : Ŀ�ؼ� ���� ����");
		} finally {
			pool.freeConnection(conn, pstmt1);
	        pool.freeConnection(null, pstmt2); 
		}
	}
}