package database.dtos;

import java.util.LinkedList;

import app.entities.AbstractEntity;

public class AccessCodesDTO extends AbstractDTO {
	
	private LinkedList<String> accessCodes;
	private boolean usedCodes;
	
	public int getAccessCodeIndex(String accessCode) {
		return accessCodes.indexOf(accessCode);
	}
	
	public String getAccessCodeByIndex(int index) {
		return accessCodes.get(index);
	}
	
	public void addAccessCode(String accessCode) {
		accessCodes.add(accessCode);
	}
	
	public void removeAccessCode(String accessCode) {
		accessCodes.remove(accessCode);
	}
	
	public LinkedList<String> getAllAccessCodes() {
		return this.accessCodes;
	}
	
	public void setAccessCodes(LinkedList<String> accessCodes) {
		this.accessCodes = accessCodes;
	}

	public boolean isUsedCodes() {
		return usedCodes;
	}

	public void setUsedCodes(boolean usedCodes) {
		this.usedCodes = usedCodes;
	}

	@Override
	public AbstractEntity toEntity() {
		return null;
	}
	
}
