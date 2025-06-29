package DataClass;

import java.time.LocalDate;

public class MemberInfo {
	private String _id;
	private String _pwd;
	private String _name;
	private LocalDate _recCreateDate;
	private LocalDate _recUpdateDate;

	public MemberInfo(String id, String pwd, String name, LocalDate recCreateDate, LocalDate recUpdateDate) {
		_id = id;
		_pwd = pwd;
		_name = name;
		_recCreateDate = recCreateDate;
		_recUpdateDate = recUpdateDate;
	}
	
	public String getId() {
		return _id;
	}

	public String getPwd() {
		return _pwd;
	}
	
	public String getName() {
		return _name;
	}
	
	public LocalDate getCreateDate() {
		return _recCreateDate;
	}
	
	public LocalDate getUpdateDate(){
		return _recUpdateDate;
	}
}
