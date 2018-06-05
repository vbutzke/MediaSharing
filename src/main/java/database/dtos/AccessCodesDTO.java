package database.dtos;

import java.util.LinkedList;

public class AccessCodesDTO {
	
	LinkedList<String> accessCodes;
	
	public int getAccessCodeIndex(String accessCode) {
		return accessCodes.indexOf(accessCode);
	}
	
	public String getAccessCodeByIndex(int index) {
		return accessCodes.get(index);
	}
	
	public void addAccessCode(String accessCode) {
		accessCodes.add(accessCode);
	}
	
}
