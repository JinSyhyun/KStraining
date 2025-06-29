package User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import DataClass.MemberInfo;

public class MemberDAO{
	private MemberDTO _memberDto = new MemberDTO();
	private DBConnectionMgr pool;

	public MemberDAO(){
		try{
			pool = DBConnectionMgr.getInstance();
			System.out.println("DB연결 성공");
		}catch(Exception e){
			System.out.println("Error : Ŀ�ؼ� ���� ����");			
		}
	}
	
	public DBConnectionMgr getPool() {
        return pool;
    }
	
	// ID,PW 일치확인
	public boolean checkLogin(String id, String pwd) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean loginCon = false;
        try {
            con = pool.getConnection();
			String query = "select count(*) from MEMBERINFO where member_id = ? and member_password = ?";
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, id);
            pstmt.setString(2, pwd);
            
            rs = pstmt.executeQuery();
            if(rs.next()&&rs.getInt(1)>0) 
            	loginCon =true;
        }catch(Exception ex) {
            System.out.println("Exception" + ex);
            System.out.println("캐치");
        }finally{
             pool.freeConnection(con,pstmt,rs);
        }
        return loginCon;
    }
	
	// MEMBERINFO 데이터 불러오기
	public MemberInfo findById(String id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = pool.getConnection();
            String sql = "SELECT * FROM MEMBERINFO WHERE member_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
            	MemberInfo memberInfo = _memberDto.getMemberInfo(rs);
                return memberInfo;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.freeConnection(conn, pstmt, rs);
        }
        return null;
    }
	
	// 작성일자 업데이트
	public void updateRecCreateDate(String id) {
	    Connection con = null;
	    PreparedStatement pstmt1 = null;
	    PreparedStatement pstmt2 = null;

	    String sql1 = "UPDATE MEMBERINFO SET rec_create_date = NOW() WHERE member_id = ?";
	    String sql2 = "UPDATE POINTINFO SET rec_create_date = NOW() WHERE member_id = ?";

	    try {
	        con = pool.getConnection();

	        // MEMBERINFO 업데이트
	        pstmt1 = con.prepareStatement(sql1);
	        pstmt1.setString(1, id);
	        int cnt1 = pstmt1.executeUpdate();
	        System.out.println("MEMBERINFO 업데이트된 행 수: " + cnt1);

	        // POINTINFO 업데이트
	        pstmt2 = con.prepareStatement(sql2);
	        pstmt2.setString(1, id);
	        int cnt2 = pstmt2.executeUpdate();
	        System.out.println("POINTINFO 업데이트된 행 수: " + cnt2);

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        pool.freeConnection(con, pstmt1);
	        pool.freeConnection(null, pstmt2); 
	    }
	}
}