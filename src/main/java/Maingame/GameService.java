package Maingame;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import DataClass.GamePoint;
import DataClass.GameResult;
import DataClass.MemberInfo;
import User.MemberDAO;

public class GameService {
	private MemberDAO _memberDAO = new MemberDAO();
	private PointDAO _pointDAO = new PointDAO();
	private ResultDAO _resultDAO = new ResultDAO();

	// 当日ゲーム終了有無
	private boolean isCreateAfterUpdate(String id) {
		MemberInfo memberInfo = _memberDAO.findById(id);

		LocalDate create = memberInfo.getCreateDate();
		LocalDate update = memberInfo.getUpdateDate();
		System.out.println("作成日時: " + create + "更新日時: " + update);

		if (create.compareTo(update) == 0) {
			System.out.println("以前のゲーム情報を取得します。");
			return true;
		}
		System.out.println("初期化を進行します。");
		return false;
	}
	private int _gameNo;
	private String _hiddenNum;
	private String _resultContent;
	private boolean _gameActFlg;
	
	// 初期画面設定
	public void initializeGame(String id) {
		if (isCreateAfterUpdate(id) == false || getTodayGameHistory(id).isEmpty()) {
			_resultDAO.resetResult(id);
			_gameNo = 0;
			_hiddenNum = newHiddenNum();
			_gameActFlg = false;
		}
		getTodayGameHistory(id);
		GameResult gameresult = _resultDAO.getRecentGame(id);
		_gameNo = gameresult.getGameNo();
		_hiddenNum = gameresult.getHiddenNum();
		_resultContent = gameresult.getResultContent();
		_gameActFlg = gameresult.getGameActFlg();

		return;
	}

	public boolean endGame(String id) {
	    GameResult gameResult = _resultDAO.getRecentGame(id);
	    
	    if (gameResult == null) {
	        return false;
	    }
	    return gameResult.getGameActFlg();
	}

	public List<GameResult> getTodayGameHistory(String id) {
		return _resultDAO.findById(id);
	}

	public int loadPointInfo(String id) {
		GamePoint gamePoint = _pointDAO.findById(id);

		if (gamePoint != null) {
			return gamePoint.getPoint();
		} else {
			System.out.println("ポイント情報がありません。");
			return 0;
		}
	}

	// 数字提出
	public void submitAnswer(String id, String answer) {
		_gameNo++;
		int gameNo = _gameNo;
		String hiddenNum = _hiddenNum;
		String resultContent = resultContent(answer);
		boolean gameActFlg = gameActFlg();

		GameResult result = new GameResult(id, gameNo, hiddenNum, answer, resultContent, gameActFlg, new Date());

		_resultDAO.insertResult(result);
		getTodayGameHistory(id);

		if (isCorrectAnswer()) {
			int reward = calculatePoint();
			int point = loadPointInfo(id);
			point += reward;
			_pointDAO.updatePoint(id, point);
		}
	}

	// 隠れ数字生成
	private String newHiddenNum() {
		List<Integer> digits = new ArrayList<>();
		for (int i = 1; i <= 9; i++) {
			digits.add(i);
		}

		Collections.shuffle(digits);

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 3; i++) {
			sb.append(digits.get(i));
		}

		return _hiddenNum = sb.toString();
	}

	// 正解判定結果
	private String resultContent(String answer) {
		int b = 0;
		int s = 0;
		String resultContent;

		for (int i = 0; i < 3; i++) {
			if (answer.charAt(i) == _hiddenNum.charAt(i)) {
				s++;
			} else if (_hiddenNum.contains(String.valueOf(answer.charAt(i)))) {
				b++;
			}
		}

		if (s == 3) {
			resultContent = "当たり";
		} else if (b > 0 || s > 0) {
			resultContent = b + "B" + s + "S";
		} else {
			resultContent = "はずれ";
		}
		
		_resultContent=resultContent;
		return resultContent;
	}
	
	public boolean isCorrectAnswer() {
		return "当たり".equals(_resultContent);
	}

	// 当日ゲーム終了有無更新
	public boolean gameActFlg() {
	    _gameActFlg = isCorrectAnswer() || _gameNo >= 10;
	    return _gameActFlg;
	}

	public int calculatePoint() {
		int reward = 0;

		if (_gameNo < 6) {
			reward = 1000;
		} else if (_gameNo < 8) {
			reward = 500;
		} else
			reward = 200;
		
		return reward;
	}

}
