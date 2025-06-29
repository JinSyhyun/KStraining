package DataClass;

import java.util.Date;

public class GameResult {
	private String _id;
	private int _gameNo;
	private String _hiddenNum;
	private String _answerNum;
	private String _resultContent;
	private boolean _gameActFlg;
	private Date _recUpdateDate;

	public GameResult(String id, int gameNo, String hiddenNum, String answerNum, String resultContent, boolean gameActFlg, Date recUpdateDate) {
		_id = id;
		_gameNo = gameNo;
		_hiddenNum = hiddenNum;
		_answerNum = answerNum;
		_resultContent = resultContent;
		_gameActFlg = gameActFlg;
		_recUpdateDate = recUpdateDate;
	}
	
	public String getId() {
		return _id;
	}

	public int getGameNo() {
		return _gameNo;
	}
	
	public String getHiddenNum() {
		return _hiddenNum;
	}
	
	public String getAnswerNum() {
		return _answerNum;
	}
	
	public String getResultContent() {
		return _resultContent;
	}
	
	public boolean getGameActFlg() {
		return _gameActFlg;
	}
	public Date getUpdateDate()
	{
		return _recUpdateDate;
	}
}
