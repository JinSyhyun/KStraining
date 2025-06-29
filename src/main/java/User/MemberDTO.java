package User;

import java.sql.ResultSet;

import DataClass.MemberInfo;

public class MemberDTO {
    public MemberInfo getMemberInfo(ResultSet rs) {
    	try {
    		var memberInfo = new MemberInfo(
    				rs.getString("member_id"),
    				rs.getString("member_password"),
    				rs.getString("member_name"),
    				rs.getDate("rec_create_date").toLocalDate(),
    				rs.getDate("rec_update_date").toLocalDate()
    		);
    		return memberInfo;
    	}
    	catch(Exception e) {
    		return null;
    	}
    }
}