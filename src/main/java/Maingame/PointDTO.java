package Maingame;

import java.sql.ResultSet;

import DataClass.GamePoint;

public class PointDTO {
	public GamePoint getGamePoint(ResultSet rs) {
    	try {
    		var gamePoint = new GamePoint(
    				rs.getString("member_id"),
    				rs.getInt("point")
    		);
    		return gamePoint;
    	}
    	catch(Exception e) {
    		return null;
    	}
    }
}