package com.jym.events.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jym.events.models.Comment;
import com.jym.events.models.Event;
import com.jym.events.models.User;
import com.jym.events.services.EventService;
import com.jym.events.validator.UserValidator;

@Controller
public class UserController {
	 private final EventService eServ;
	 private final UserValidator userValidator;
    
	 public UserController(EventService eServ, UserValidator userValidator) {
		 this.eServ = eServ;
		 this.userValidator = userValidator;
	 }
	 
	 @RequestMapping("/")
	 public String home(@ModelAttribute("users") User user) {
		 
		 return "index.jsp";
	 }
	 @RequestMapping(value="/registration", method=RequestMethod.POST)
	    public String registerUser(@Valid @ModelAttribute("users") User user, BindingResult result, HttpSession session) {
	    	userValidator.validate(user, result); 
	        if(result.hasErrors()) {
	        	return "index.jsp";
	        }
	        User u = eServ.registerUser(user);
	        session.setAttribute("user", u); //this used to be user ID stuff. Don't save as a full user object, it causes issues later. Always save as userId
	        return"redirect:/events";
	    }
	    
	    @RequestMapping(value="/login", method=RequestMethod.POST)
	    public String loginUser(@RequestParam("email") String email, @RequestParam("password") String password, @ModelAttribute("users") User user, Model model, HttpSession session) {
	        boolean isAuthenticated = eServ.authenticateUser(email, password);
	        if(isAuthenticated) {
	        	User u = eServ.findByEmail(email);
	        	session.setAttribute("user", u); //see above
	        	return "redirect:/events";
	        } else {
	        	model.addAttribute("error", "Invalid Credentials. Please try again.");
	        	return "index.jsp";
	        }
	        
	    }

//		*************
	    
//		ALL OF THIS IS THE APPROPRIATE WAY TO SAVE THE USERID IN SESSION FOR LOGIN AND REGISTRATION
	    
//	    @RequestMapping(value="/registration", method=RequestMethod.POST)
//	    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result, HttpSession session) {
//	    	userValidator.validate(user, result);
//	        if(result.hasErrors()) {
//	        	return "registrationPage.jsp";
//	        }
//	        User u = userService.registerUser(user);
//	        session.setAttribute("userId", u.getId());
//	        return"redirect:/home";
//	    }
//	    
//	    @RequestMapping(value="/login", method=RequestMethod.POST)
//	    public String loginUser(@RequestParam("email") String email, @RequestParam("password") String password, Model model, HttpSession session) {
//	        boolean isAuthenticated = userService.authenticateUser(email, password);
//	        if(isAuthenticated) {
//	        	User u = userService.findByEmail(email);
//	        	session.setAttribute("userId", u.getId());
//	        	return "redirect:/home";
//	        } else {
//	        	model.addAttribute("error", "Invalid Credentials. Please try again.");
//	        	return "loginPage.jsp";
//	        }
//	        
//	    }
//	    
//	    @RequestMapping("/home")
//	    public String home(HttpSession session, Model model) {
//	       Long userId = (Long) session.getAttribute("userId");
//	       User u = userService.findUserById(userId);
//	       model.addAttribute("user", u);
//	       return "homePage.jsp";
//	    }
	    
	    
	    
	    
	    
	    @RequestMapping("/events")
	    public String allEvents(@Valid @ModelAttribute("events") Event event, BindingResult result, HttpSession session, Model model) {
	    	if(session.getAttribute("user") == null) {
				 return "redirect:/";
			 }
//	    	List<Event> stateEvent = eServ.isIn((String) ((Event) session.getAttribute("user")).getState());
//	    	String s = ((User) session.getAttribute("user")).getState();
//	    	List<Event> stateEvent = eServ.isIn(s);
	    	User u = (User) session.getAttribute("user");
	    	String s = u.getState();
	    	Long uId = u.getId();
	    	
	        List<Event> stateEvent = eServ.isIn(s);
	        List<Event> osEvent = eServ.notIn(s);
	       
	        model.addAttribute("stateEvent", stateEvent);
	        model.addAttribute("osEvent", osEvent);
	        model.addAttribute("uId", uId);
	        model.addAttribute("user", eServ.findUserById(uId));
	        
	        
//	       System.out.println(event.getEvents().contains(uId));
	    	return "all.jsp";
	    }
	    @PostMapping("/add_events")
	    public String addEvent(@Valid @ModelAttribute("events") Event event, BindingResult result, HttpSession session ) {
	    	if(result.hasErrors()) {
	    		System.out.println("errors");
	    		return "all.jsp";
	    	}else {
	    	User u = (User) session.getAttribute("user");
	    	event.setHost(u);
	    	eServ.createEvent(event);
	    	return "redirect:/events";
	    	}
	    }
	    
	    @RequestMapping("/logout")
	    public String logout(HttpSession session) {
	        session.invalidate();
	        return "redirect:/";
	    }
	    @RequestMapping("/events/{id}")
	    public String display(@PathVariable("id") Long id, @ModelAttribute("events") Event event, @ModelAttribute("comments") Comment comment, BindingResult result, HttpSession session, Model model) {
	    	if(session.getAttribute("user") == null) {
				 return "redirect:/";
			 }
	    	event = eServ.findEvent(id);
	    	model.addAttribute("event", event);
	    	return "show.jsp";
	    }
	    @RequestMapping("/join/{id}")
	    public String joinEvent(@PathVariable("id") Long id, @ModelAttribute("events") Event event, HttpSession session) {
	    	User u = (User) session.getAttribute("user");
	    	Event upEvent = eServ.findEvent(id);
			upEvent.getAttendees().add(u);
			eServ.update(upEvent);
	    	return "redirect:/events";
	    }
	    @RequestMapping("/cancel/{ev_id}")
	    public String cancelJoin(@PathVariable("ev_id") Long id, HttpSession session) {
	    	User u = (User) session.getAttribute("user");
	    	User u2 = eServ.findUserById(u.getId());
	    	System.out.println(u);
	    	System.out.println(u2);
	    	Event outE = eServ.findEvent(id);
	    	System.out.println(outE.getAttendees().contains(u));
	    	outE.removeAttendees(u2);
	    	System.out.println(outE.getAttendees());
	    	System.out.println("controller");
	    	eServ.update(outE);
	    	return "redirect:/events";
	    }
	    @PostMapping("/comment/{event_id}")
	    public String addComment(@PathVariable("event_id") Long event_id, @ModelAttribute("events") Event event, @ModelAttribute("comments") Comment comment, BindingResult result, HttpSession session) {
	    	Event cEvent = eServ.findEvent(event_id);
	    	User u = (User) session.getAttribute("user");
	    	System.out.println(cEvent);
	    	cEvent.getComments().add(comment);
	    	System.out.println(comment);
	    	eServ.update(cEvent);
	    	eServ.createComment(comment);
	    	return "redirect:/events/"+cEvent.getId();
	    }
	    @RequestMapping("/delete/{id}")
	    public String destroy(@PathVariable("id") Long id) {
	    	eServ.deleteEvent(id);
	    
	    
	    return "redirect:/events";
	    }
}
