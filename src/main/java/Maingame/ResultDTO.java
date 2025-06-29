package Maingame;

import java.sql.ResultSet;

import DataClass.GameResult;

public class ResultDTO {
	public GameResult getGameResult(ResultSet rs) {
    	try {
    		var gameResult = new GameResult(
    				rs.getString("member_id"),
    				rs.getInt("game_no"),
    				rs.getString("hidden_number"),
    				rs.getString("answer_number"),
    				rs.getString("result_content"),
    				rs.getBoolean("game_act_flg"),
    				rs.getDate("rec_update_date")
    		);
    		return gameResult;
    	}
    	catch(Exception e) {
    		return null;
    	}
    }
}