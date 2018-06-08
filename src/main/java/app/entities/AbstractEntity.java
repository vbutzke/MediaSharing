package app.entities;

import database.dtos.AbstractDTO;

public abstract class AbstractEntity {

	public abstract AbstractDTO convertToDTO();
	
}