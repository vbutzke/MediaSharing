package database.dtos;

import app.entities.AbstractEntity;
import app.entities.Institution;

public class InstitutionDTO extends AbstractDTO {
	
	private String name;
	private String CNPJ;
	private String emailProvider;
		
	public InstitutionDTO(String name, String CNPJ, String emailProvider) {
		this.name = name;
		this.CNPJ = CNPJ;
		this.emailProvider = emailProvider;
	}
		
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCNPJ() {
		return CNPJ;
	}
	
	public void setCNPJ(String CNPJ) {
		this.CNPJ = CNPJ;
	}

	public String getEmailProvider() {
		return emailProvider;
	}

	public void setEmailProvider(String emailProvider) {
		this.emailProvider = emailProvider;
	}
	
	@Override
	public AbstractEntity toEntity() {
		return new Institution(name, CNPJ, emailProvider);
	}
	
}