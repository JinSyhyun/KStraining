package DataClass;


public class GamePoint {
	private String _id;
	private int _point;

	public GamePoint(String id, int point) {
		_id = id;
		_point = point;
	}
	
	public String getId() {
		return _id;
	}
	
	public int getPoint() {
		return _point;
	}
	
}