package User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import DataClass.MemberInfo;

public class MemberService {

    private MemberDAO memberDAO = new MemberDAO();

    public boolean login(String id, String pwd, HttpServletRequest request) {
        boolean isValid = memberDAO.checkLogin(id, pwd);

        if (isValid) {
            // 로그인 성공: 사용자 정보 조회
            MemberInfo member = memberDAO.findById(id);
            // 작성일자 업데이트
            memberDAO.updateRecCreateDate(id);

            // 세션에 정보 저장
            HttpSession session = request.getSession();
            session.setAttribute("id", member.getId());
            session.setAttribute("name", member.getName());
            session.setAttribute("createDate", member.getCreateDate());
            session.setAttribute("updateDate", member.getUpdateDate());
        }

        return isValid;
    }
    
    
}