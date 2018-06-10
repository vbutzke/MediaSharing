package app.entities;

import java.io.IOException;

import database.dtos.AbstractDTO;

public abstract class AbstractEntity {

	public abstract AbstractDTO convertToDTO() throws IOException;
	
}