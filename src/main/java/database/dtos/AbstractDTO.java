package database.dtos;

import java.io.IOException;

import app.entities.AbstractEntity;

public abstract class AbstractDTO {
	
	public abstract AbstractEntity toEntity() throws IOException;
	
}