package com.jym.events.services;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.jym.events.models.Comment;
import com.jym.events.models.Event;
import com.jym.events.models.User;
import com.jym.events.repositories.CommentRepository;
import com.jym.events.repositories.EventRepository;
import com.jym.events.repositories.UserRepository;

@Service
public class EventService {
	private final UserRepository uRepo;
	private final EventRepository eRepo;
	private final CommentRepository cRepo;
	
	public EventService(UserRepository uRepo, EventRepository eRepo, CommentRepository cRepo) {
		this.cRepo = cRepo;
		this.uRepo = uRepo;
		this.eRepo = eRepo;
	}
	public List<Event> allEvents(){
		return eRepo.findAll();
	}
	public List<Comment> allComments(){
		return cRepo.findAll();
	}
	public List<User> allUsers(){
		return uRepo.findAll();
	}
	public Event addEvent(Event event) {
		return eRepo.save(event);
	}
	public Comment addComment(Comment comment) {
		return cRepo.save(comment);
	}
	public User addUser(User user) {
		return uRepo.save(user);
	}
	public void addCommentToEvent(Comment comment, Event event) {
		comment.setEvent(event);
		cRepo.save(comment);
	}
	public Event findEvent(Long id) {
		Optional<Event> optionalEvent = eRepo.findById(id);
		if(optionalEvent.isPresent()) {
			return optionalEvent.get();
		}else {
			return null;
		}
	}
	public List<Event> notIn(String state){
		return eRepo.findByStateNot(state);
	}
	public List<Event> isIn(String state){
		return eRepo.findByState(state);
	}
	public Event createEvent(Event event) {
		return eRepo.save(event);
	}
	public Comment createComment(Comment comment) {
		return cRepo.save(comment);
	}
	
	 // register user and hash their password
    public User registerUser(User user) {
        String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashed);
        return uRepo.save(user);
    }
    
    // find user by email
    public User findByEmail(String email) {
        return uRepo.findByEmail(email);
    }
    
    // find user by id
    public User findUserById(Long id) {
    	Optional<User> u = uRepo.findById(id);
    	
    	if(u.isPresent()) {
            return u.get();
    	} else {
    	    return null;
    	}
    }
    
    // authenticate user
    public boolean authenticateUser(String email, String password) {
        // first find the user by email
        User user = uRepo.findByEmail(email);
        // if we can't find it by email, return false
        if(user == null) {
            return false;
        } else {
            // if the passwords match, return true, else, return false
            if(BCrypt.checkpw(password, user.getPassword())) {
                return true;
            } else {
                return false;
            }
        }
    }
	public void update(Event event) {
		System.out.println("service");
		eRepo.save(event);
		
	}
	public void deleteEvent(Long id) {
		eRepo.deleteById(id);;
		
	}
//	private String loggedIn(HttpSession session) {
//		 if(session.getAttribute("user") == null) {
//			 return "redirect:/";
//		 }else {
//			 return null;
//		 }
//	 }

}
