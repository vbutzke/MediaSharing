package app.singletons;

import java.io.File;
import java.io.IOException;
import com.mongodb.DB;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSInputFile;

import database.DatabaseManagement;

public enum MediaStorageController {

	INSTANCE();
	
	private GridFS gfsMedia;
	
	private MediaStorageController() {
		
	}
	
	public void setGFSMedia(DatabaseManagement dm) {
		this.gfsMedia = new GridFS((DB) dm.getDatabase(), "medias");
	}
	
	public void saveMedia(File content, String referenceName) throws IOException {
		GridFSInputFile gfsFile = gfsMedia.createFile(content);
		gfsFile.setFilename(referenceName);
		gfsFile.save();
	}
	
	public void removeMedia(String referenceName) {
		gfsMedia.remove(gfsMedia.findOne(referenceName));
	}
	
	public File getMedia(String referenceName) throws IOException {
		File media = new File(referenceName);
		gfsMedia.findOne(referenceName).writeTo(media);
		return media;
	}
	
}