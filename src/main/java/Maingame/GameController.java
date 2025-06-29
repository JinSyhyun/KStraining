package Maingame;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DataClass.GameResult;

@WebServlet("/GameController")
public class GameController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private GameService service = new GameService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String id = (session != null) ? (String) session.getAttribute("id") : null;

		if (id == null) {
			response.sendRedirect("login.jsp");
			return;
		}

		System.out.println("로그인한 ID: " + id);

		service.initializeGame(id);
		
		String errorMsg = null;
		List<GameResult> resultList = service.getTodayGameHistory(id);
		int point = service.loadPointInfo(id);

		if (point != 0 && resultList != null && !resultList.isEmpty()) {
		    request.setAttribute("point", point);
		    request.setAttribute("resultList", resultList);
		} else if (point != 0 && (resultList == null || resultList.isEmpty())) {
		    request.setAttribute("point", point);
		    errorMsg = "今日ゲーム履歴がありません。";
		    System.out.println("게임 이력이 없습니다.");
		} else {
			errorMsg = "ポイント情報が存在しません。";
		    System.out.println("포인트 정보가 없습니다.");
		}
		boolean disableInput = service.endGame(id);
		request.setAttribute("disableInput", disableInput);
		
		if (errorMsg != null) {
			request.setAttribute("errorMsg", errorMsg);
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher("maingame.jsp");
		dispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String id = (session != null) ? (String) session.getAttribute("id") : null;
		
		
		
		String a100 = request.getParameter("answer_100");
	    String a10 = request.getParameter("answer_10");
	    String a1 = request.getParameter("answer_1");
	    String errorMsg = null;

	    if (a100 == null || a10 == null || a1 == null ||
	        a100.isBlank() || a10.isBlank() || a1.isBlank()) {
	        errorMsg = "３桁の数字を入力してください。";
	    } else if (a100.equals(a10) || a100.equals(a1) || a10.equals(a1)) {
	        errorMsg = "重複しない数を入力してください。";
	    } else {
	        String answer = a100 + a10 + a1;
	        service.submitAnswer(id, answer);
	        
	        List<GameResult> resultList = service.getTodayGameHistory(id);
	        int point = service.loadPointInfo(id);
	        request.setAttribute("point", point);
	        request.setAttribute("resultList", resultList);
	        
	    }
	    
	    boolean disableInput = service.endGame(id);
	    boolean isCorrect = service.isCorrectAnswer();
	    int earnedPoint = service.calculatePoint();
	    request.setAttribute("disableInput", disableInput);
	    request.getSession().setAttribute("isCorrect", isCorrect);
	    request.getSession().setAttribute("earnedPoint", earnedPoint);
	    
	    if (errorMsg != null) {
	        request.setAttribute("errorMsg", errorMsg);
	    }
	    request.getRequestDispatcher("/maingame.jsp").forward(request, response);
	}	
}