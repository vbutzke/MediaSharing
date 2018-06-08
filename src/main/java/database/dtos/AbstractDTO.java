package database.dtos;

import app.entities.AbstractEntity;

public abstract class AbstractDTO {
	
	public abstract AbstractEntity toEntity();
	
}