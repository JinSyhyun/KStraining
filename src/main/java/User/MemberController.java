package User;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/MemberController")
public class MemberController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private MemberService memberService = new MemberService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String id = request.getParameter("id");
        String pwd = request.getParameter("pwd");

        if (id == null|| pwd == null) {
        	request.setAttribute("errorMsg", "IDを6桁、PWを8桁入力してください。");
        	RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
        	dispatcher.forward(request, response);
            return;
        }

        boolean loginSuccess = memberService.login(id, pwd, request);

        if (loginSuccess) {
            response.sendRedirect("GameController");
        } else {
        	request.setAttribute("errorMsg", "IDとパスワードが違います。");
        	RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
        	dispatcher.forward(request, response);
        }
    }

    private void printMessage(HttpServletResponse response, String msg, String location) throws IOException {
        response.getWriter().println("<script>alert('" + msg + "'); location.href='" + location + "';</script>");
    }
}