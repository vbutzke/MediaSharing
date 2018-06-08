package app.controllers;

import app.entities.Author;
import app.entities.Institution;
import app.entities.Media;
import app.entities.User;
import app.singletons.Exceptions;
import app.singletons.MediaType;
import app.singletons.UserType;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import javax.mail.MessagingException;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class AppController {

	AuthenticationController authenticationController = new AuthenticationController();
	MediaSharingController mediaSharingController; 
	boolean isLoggedIn = false;
	ObjectMapper mapper = new ObjectMapper();
	
	@RequestMapping("/createInstitution")
	public void createInstitution(@RequestParam(value="institution") String institution, HttpServletResponse response) {
		if(isLoggedIn) {
			User user = mediaSharingController.accessProfile().getUser();

			if(user.getUserType().equals(UserType.ADMINISTRATOR)){
				try {
					Institution instObj = mapper.readValue(institution, Institution.class);
					mediaSharingController.createInstitution(instObj);
					response.setStatus( HttpServletResponse.SC_OK );
				} catch (JsonParseException e) {
					e.printStackTrace();
					response.setStatus( HttpServletResponse.SC_INTERNAL_SERVER_ERROR  );
					System.out.println("Error parsing JSON - accessProfile");
				} catch (JsonMappingException e) {
					e.printStackTrace();
					response.setStatus( HttpServletResponse.SC_INTERNAL_SERVER_ERROR  );
					System.out.println("Error mapping JSON - accessProfile");
				} catch (IOException e) {
					e.printStackTrace();
					response.setStatus( HttpServletResponse.SC_INTERNAL_SERVER_ERROR  );
					System.out.println("Error - accessProfile");
				}
			} else {
				response = sendError(response, HttpServletResponse.SC_UNAUTHORIZED, Exceptions.PERMISSION_DENIED.getMessage());
			}
		} else {
			response.setStatus( HttpServletResponse.SC_UNAUTHORIZED );
		}
	}
	
	@RequestMapping("/deleteInstitution")
	public void deleteInstitution(@RequestParam(value="institutionId") int id, HttpServletResponse response) {	
		if(isLoggedIn) {
			User user = mediaSharingController.accessProfile().getUser();

			if(user.getUserType().equals(UserType.ADMINISTRATOR)){
				mediaSharingController.deleteInstitution(id);
				response.setStatus( HttpServletResponse.SC_OK );
			} else {
				response = sendError(response, HttpServletResponse.SC_UNAUTHORIZED, Exceptions.PERMISSION_DENIED.getMessage());
			}
		} else {
			response.setStatus( HttpServletResponse.SC_UNAUTHORIZED );
		}
	}
	
	@RequestMapping("/login")
	public void login(@RequestParam(value="email") String email, @RequestParam(value="password") String password, HttpServletResponse response) {
		
		try {
			mediaSharingController = new MediaSharingController(authenticationController.login(email, password), authenticationController);
			isLoggedIn = true;
			response.setStatus( HttpServletResponse.SC_OK );
		} catch(LoginException e) {
			response = sendError(response, HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
		} catch (JsonParseException e) {
			response = sendError(response, HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
		} catch (JsonMappingException e) {
			response = sendError(response, HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
		} catch (IOException e) {
			response = sendError(response, HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
		}
		
	}
	
	@RequestMapping("/createUser")
	public void createUser(@RequestParam(value="name") String name, @RequestParam(value="lastName") String lastName, @RequestParam(value="email") String email, @RequestParam(value="password") String password, @RequestParam(value="confirmation") String confirmation, @RequestParam(value="accessCode") String accessCode, HttpServletResponse response) {
		try {
			authenticationController.createUser(name, lastName, email, password, confirmation, accessCode);
			response.setStatus( HttpServletResponse.SC_CREATED );
		} catch(InvalidParameterException e) {
			response = sendError(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
		} catch (JsonParseException e) {
			response = sendError(response, HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
		} catch (JsonMappingException e) {
			response = sendError(response, HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
		} catch (JsonProcessingException e) {
			response = sendError(response, HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
		} catch (IOException e) {
			response = sendError(response, HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
		}
	}
	
	@RequestMapping("/resetPassword")
	public void resetPassword(@RequestParam(value="email") String email, HttpServletResponse response) {
		try {
			authenticationController.resetPassword();
			response.setStatus( HttpServletResponse.SC_CREATED );	
		} catch(MessagingException e) {
			response = sendError(response, HttpServletResponse.SC_CONFLICT, Exceptions.EMAIL_SERVICE_ERROR.getMessage()+"\n "+e.getMessage());
		}
	}
	
	@RequestMapping("/profile")
	public String accessProfile(HttpServletResponse response) {
		return getJSON(mediaSharingController.accessProfile(), response);
	}
		
	@RequestMapping("/changePassword")
	public void changePassword(@RequestParam(value="password") String password, @RequestParam(value="confirmation") String confirmation, HttpServletResponse response) {
		if(isLoggedIn) {
			try {
				mediaSharingController.changePassword(password, confirmation);
				response.setStatus( HttpServletResponse.SC_OK );
			} catch(InputMismatchException e) {
				response = sendError(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
			} catch(MessagingException m) {
				response = sendError(response, HttpServletResponse.SC_BAD_REQUEST, Exceptions.EMAIL_SERVICE_ERROR+"\n "+m.getMessage());
			}
			
		} else {
			response.setStatus( HttpServletResponse.SC_UNAUTHORIZED );
		}
	}
	
	@RequestMapping("/addEmail")
	public void addEmail(@RequestParam(value="email") String email, HttpServletResponse response) {
		if(isLoggedIn) {
			try {
				mediaSharingController.addEmail(email);
			} catch (MessagingException e) {
				response = sendError(response, HttpServletResponse.SC_BAD_REQUEST, Exceptions.EMAIL_SERVICE_ERROR+"\n "+e.getMessage());
			}
			response.setStatus( HttpServletResponse.SC_OK  );
		} else {
			response.setStatus( HttpServletResponse.SC_UNAUTHORIZED );
		}
	}
	
	@RequestMapping("/removeEmail") //
	public void removeEmail(@RequestParam(value="emailId") int id, HttpServletResponse response) {
		if(isLoggedIn) {
			mediaSharingController.removeEmail(id);
			response.setStatus( HttpServletResponse.SC_OK  );
		} else {
			response.setStatus( HttpServletResponse.SC_UNAUTHORIZED );
		}
	}
	
	@RequestMapping("/deleteAccount")
	public void deleteAccount(HttpServletResponse response) {
		if(isLoggedIn) {
			authenticationController.logout();
			mediaSharingController.deleteAccount();
			response.setStatus( HttpServletResponse.SC_OK  );
		} else {
			response.setStatus( HttpServletResponse.SC_UNAUTHORIZED );
		}
	}
	
	@RequestMapping("/generateAccessCode")
	public String generateAccessCode(HttpServletResponse response) {
		if(isLoggedIn) {
			try {
				return mediaSharingController.generateAccessCode();
			} catch(SecurityException e) {
				response = sendError(response, HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
			} catch (IOException e) {
				response = sendError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			}
			response.setStatus( HttpServletResponse.SC_OK  );
		} else {
			response.setStatus( HttpServletResponse.SC_UNAUTHORIZED );
		}
		return "";
	}
	
	@RequestMapping("/changeName")
	public void changeName(@RequestParam(value="name") String name, HttpServletResponse response) {
		if(isLoggedIn) {
			mediaSharingController.changeName(name);
			response.setStatus( HttpServletResponse.SC_OK  );
		} else {
			response.setStatus( HttpServletResponse.SC_UNAUTHORIZED );
		}
	}
	
	@RequestMapping("/addInstitution")
	public void addInstitution(@RequestParam(value="institution") String institution, HttpServletResponse response) {
		if(isLoggedIn) {
			try {
				Institution instObj = mapper.readValue(institution, Institution.class);
				mediaSharingController.addInstitution(instObj);
				response.setStatus( HttpServletResponse.SC_OK );
			} catch (JsonParseException e) {
				e.printStackTrace();
				response.setStatus( HttpServletResponse.SC_INTERNAL_SERVER_ERROR  );
				System.out.println("Error parsing JSON - accessProfile");
			} catch (JsonMappingException e) {
				e.printStackTrace();
				response.setStatus( HttpServletResponse.SC_INTERNAL_SERVER_ERROR  );
				System.out.println("Error mapping JSON - accessProfile");
			} catch (IOException e) {
				e.printStackTrace();
				response.setStatus( HttpServletResponse.SC_INTERNAL_SERVER_ERROR  );
				System.out.println("Error - accessProfile");
			}
		} else {
			response.setStatus( HttpServletResponse.SC_UNAUTHORIZED );
		}
	}
	
	@RequestMapping("/removeInstitution")
	public void removeInstitution(@RequestParam(value="institutionId") int id, HttpServletResponse response) {
		mediaSharingController.removeInstitution(id);
		response.setStatus( HttpServletResponse.SC_OK );
	}
	
	@RequestMapping("/dashboard")
	public String accessDashboard(HttpServletResponse response) {
		return getJSON(mediaSharingController.accessDashboard(), response);
	}
	
	@RequestMapping("/searchAll")
	public String searchAll(@RequestParam("name") String name, @RequestParam("description") String description, @RequestParam("authors") LinkedList<Author> authors, @RequestParam("mediaType") MediaType mediaType, HttpServletResponse response) {
		return getJSON(mediaSharingController.searchInAllMedias(name, description, authors, mediaType), response);
	}
	
	@RequestMapping("/addMedia")
	public void addMedia(@RequestParam(value="media") String media, HttpServletResponse response) {
		
		if(isLoggedIn) {
			try {
				Media mediaObj = mapper.readValue(media, Media.class);
				mediaSharingController.addMedia(mediaObj);
				response.setStatus( HttpServletResponse.SC_OK );
			} catch (JsonParseException e) {
				e.printStackTrace();
				response.setStatus( HttpServletResponse.SC_INTERNAL_SERVER_ERROR  );
				System.out.println("Error parsing JSON - accessProfile");
			} catch (JsonMappingException e) {
				e.printStackTrace();
				response.setStatus( HttpServletResponse.SC_INTERNAL_SERVER_ERROR  );
				System.out.println("Error mapping JSON - accessProfile");
			} catch (IOException e) {
				e.printStackTrace();
				response.setStatus( HttpServletResponse.SC_INTERNAL_SERVER_ERROR  );
				System.out.println("Error - accessProfile");
			}
		} else {
			response.setStatus( HttpServletResponse.SC_UNAUTHORIZED );
		}

	}
	
	@RequestMapping("/myMedias")
	public String accessMyMedias(HttpServletResponse response) {
		return getJSON(mediaSharingController.accessMyMedia(), response);
	}
	
	@RequestMapping("/searchMyMedias")
	public String searchMyMedias(@RequestParam("name") String name, @RequestParam("description") String description, @RequestParam("authors") LinkedList<Author> authors, @RequestParam("mediaType") MediaType mediaType, HttpServletResponse response) {
		return getJSON(mediaSharingController.searchOnMyMedias(name, description, authors, mediaType), response);
	}
	
	@RequestMapping("/favorites")
	public String accessFavorites(HttpServletResponse response) {
		try {
			return getJSON(mediaSharingController.accessFavorites(), response);
		} catch (JsonProcessingException e) {
			response = sendError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return "";
	}
	
	@RequestMapping("/searchFavorites")
	public String searchFavorites(@RequestParam("name") String name, @RequestParam("description") String description, @RequestParam("authors") LinkedList<Author> authors, @RequestParam("mediaType") MediaType mediaType, HttpServletResponse response) {
		try {
			return getJSON(mediaSharingController.searchOnFavorites(name, description, authors, mediaType), response);
		} catch (JsonProcessingException e) {
			response = sendError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
		
		return "";
	}
	
	@RequestMapping("/getMediaInfo")
	public String clickOnMedia(@RequestParam(value="mediaId") int id, HttpServletResponse response) {
		return getJSON(mediaSharingController.clickOnMedia(id), response);
	}
	
	@RequestMapping("/deleteMedia")
	public void deleteMedia(@RequestParam(value="mediaId") int id, HttpServletResponse response) {
		
		if(isLoggedIn) {
			User user = mediaSharingController.accessProfile().getUser();

			if(user.getUserType().equals(UserType.ADMINISTRATOR) || mediaSharingController.accessAllMedias().getMedia(id).getAuthors().contains(user)){
				mediaSharingController.accessAllMedias().removeMedia(id);
				response.setStatus( HttpServletResponse.SC_OK );
			} else {
				response = sendError(response, HttpServletResponse.SC_UNAUTHORIZED, Exceptions.PERMISSION_DENIED.getMessage());
			}
		} else {
			response.setStatus( HttpServletResponse.SC_UNAUTHORIZED );
		}
		
	}
	
	@RequestMapping("/addToFavorites")
	public void addToFavorites(@RequestParam(value="mediaId") int id, HttpServletResponse response) {
		if(isLoggedIn) {
			try {
				mediaSharingController.accessFavorites().addMedia(mediaSharingController.accessAllMedias().getMedia(id));
			} catch (JsonProcessingException e) {
				response = sendError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());;
			}
			response.setStatus( HttpServletResponse.SC_OK );
		} else {
			response.setStatus( HttpServletResponse.SC_UNAUTHORIZED );
		}
	}
		
	@RequestMapping("/removeFromFavorites")
	public void removeFromFavorites(@RequestParam(value="mediaId") int id, HttpServletResponse response) {
		if(isLoggedIn) {
			try {
				mediaSharingController.accessFavorites().removeMedia(id);
			} catch (JsonProcessingException e) {
				response = sendError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			}
			response.setStatus( HttpServletResponse.SC_OK );
		} else {
			response.setStatus( HttpServletResponse.SC_UNAUTHORIZED );
		}
	}
		
	@RequestMapping("/editMedia")
	public void editMedia(@RequestParam(value="mediaId") int id, @RequestParam(value="newMedia") String newMedia, HttpServletResponse response) {
		
		if(isLoggedIn) {
			User user = mediaSharingController.accessProfile().getUser();

			if(user.getUserType().equals(UserType.ADMINISTRATOR) || mediaSharingController.accessAllMedias().getMedia(id).getAuthors().contains(user)){
				try {
					Media mediaObj = mapper.readValue(newMedia, Media.class);
					mediaSharingController.editMedia(id, mediaObj);
					response.setStatus( HttpServletResponse.SC_OK );
				} catch (JsonParseException e) {
					e.printStackTrace();
					response.setStatus( HttpServletResponse.SC_INTERNAL_SERVER_ERROR  );
					System.out.println("Error parsing JSON - accessProfile");
				} catch (JsonMappingException e) {
					e.printStackTrace();
					response.setStatus( HttpServletResponse.SC_INTERNAL_SERVER_ERROR  );
					System.out.println("Error mapping JSON - accessProfile");
				} catch (IOException e) {
					e.printStackTrace();
					response.setStatus( HttpServletResponse.SC_INTERNAL_SERVER_ERROR  );
					System.out.println("Error - accessProfile");
				}
			} else {
				response = sendError(response, HttpServletResponse.SC_UNAUTHORIZED, Exceptions.PERMISSION_DENIED.getMessage());
			}
		} else {
			response.setStatus( HttpServletResponse.SC_UNAUTHORIZED );
		}
	}
		
	@RequestMapping("/logout")
	public void logout(HttpServletResponse response) {
		if(isLoggedIn) {
			mediaSharingController.logout();
			mediaSharingController = null;
			authenticationController = null;
			isLoggedIn = false;
		}
		response.setStatus( HttpServletResponse.SC_OK );
	}

	private HttpServletResponse sendError(HttpServletResponse response, int sc, String message) {
		response.setStatus( sc );
		try {
			response.sendError(sc, message);		
		} catch(IOException i) {
			i.printStackTrace();
		}
		
		return response;
	}
	
	private String getJSON(Object value, HttpServletResponse response) {
		if(isLoggedIn) {
			try{
				String JSON = mapper.writeValueAsString(value);
				response.setStatus( HttpServletResponse.SC_OK  );
				return JSON;
			} catch(NoSuchElementException e){
				response.setStatus( HttpServletResponse.SC_NO_CONTENT  );
				response = sendError(response, HttpServletResponse.SC_NO_CONTENT, e.getMessage());
			} catch (JsonParseException e) {
				response.setStatus( HttpServletResponse.SC_INTERNAL_SERVER_ERROR  );
				response = sendError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			} catch (JsonMappingException e) {
				response.setStatus( HttpServletResponse.SC_INTERNAL_SERVER_ERROR  );
				response = sendError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			} catch (IOException e) {
				response.setStatus( HttpServletResponse.SC_INTERNAL_SERVER_ERROR  );
				response = sendError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
			}
		} else {
			response.setStatus( HttpServletResponse.SC_UNAUTHORIZED );
		}
		
		return "";
	}
		
}
