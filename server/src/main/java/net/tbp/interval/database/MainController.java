package net.tbp.interval.database;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.tbp.interval.backup.Event;
import net.tbp.interval.backup.UserProfile;

/**
 * Contains all core HTTP call handlers.
 *
 * @author Hugo Wong
 * @author Jade Webb
 *
 */
@Controller
@RequestMapping(path = "/user")
public class MainController {
	@Autowired
	private EventRepository currentEventRepo;

	@Autowired
	private EventRepository currentGoalRepo;

	@Autowired
	private EventRepository excludedIntervalRepo;

	@Autowired
	private EventRepository recurringEventRepo;

	@Autowired
	private UserRepository userRepo;

	// Basic User Management

	@GetMapping(path = "/{username}")
	public @ResponseBody Integer getUID(@PathVariable String username) {
		for (UserProfile p : userRepo.findAll()) {
			if (p.getUserName().equals(username)) {
				return p.getId();
			}
		}
		return null;
	}

	@PostMapping(path = "/{username}")
	public @ResponseBody Integer allocateUIDToUser(@PathVariable String username) {
		UserProfile newProfile = new UserProfile();
		newProfile.setUserName(username);
		userRepo.save(newProfile);
		return userRepo.findProfileByName(username).getId();
	}

	@PutMapping(path = "/{uid}")
	public @ResponseBody String updateUserName(@PathVariable Integer uid, @RequestBody String newUserName) {
		Optional<UserProfile> profile = userRepo.findById(uid);
		if (profile.isPresent()) {
			profile.get().setUserName(newUserName);
			return newUserName;
		} else {
			return null;
		}

	}

	@DeleteMapping(path = "/{uid}")
	public @ResponseBody void addNewUser(@PathVariable Integer uid) {
		userRepo.deleteById(uid);
	}

	// Current Events

	@GetMapping(path = "/{uid}/currentevent")
	public @ResponseBody Iterable<Event> getAllCurrentEvents(@PathVariable Integer uid) {
		return currentEventRepo.findAllCurrentEvents(uid);
	}

	@PostMapping(path = "/{uid}/currentevent")
	public @ResponseBody Event addNewCurrentEvent(@RequestBody Event newEvent, @PathVariable Integer uid) {
		currentEventRepo.addNewCurrentEvent(uid, newEvent);
		currentEventRepo.addNewUserCurrentEvent(uid, newEvent);
		return newEvent;
	}

	@PutMapping(path = "/{uid}/currentevent/{eventid}")
	public @ResponseBody Event updateCurrentEvent(@RequestBody Event newEvent, @PathVariable Integer uid,
												  @PathVariable Integer eventid) {
		// perhaps slower than overwriting attributes?
		currentEventRepo.updateCurrentEvent(uid, eventid, newEvent);
		return newEvent;
	}

	@DeleteMapping(path = "/{uid}/currentevent/{eventid}")
	public @ResponseBody void deleteCurrentEvent(@PathVariable Integer uid, @PathVariable Integer eventid) {
		currentEventRepo.deleteCurrentEvent(uid, eventid);
		currentEventRepo.deleteUserCurrentEvent(uid, eventid);
	}

	// Current Goals

	@GetMapping(path = "/{uid}/currentgoal")
	public @ResponseBody Iterable<Goal> getAllCurrentGoals(@PathVariable Integer uid) {
		return currentGoalRepo.findAllCurrentGoals(uid);
	}

	@PostMapping(path = "/{uid}/currentgoal")
	public @ResponseBody Goal addNewCurrentGoal(@RequestBody Goal newGoal, @PathVariable Integer uid) {
		currentGoalRepo.addNewCurrentGoal(uid, newGoal);
		currentGoalRepo.addNewUserCurrentGoal(uid, newGoal);
		return newGoal;
	}

	@PutMapping(path = "/{uid}/currentgoal/{goalid}")
	public @ResponseBody Goal updateCurrentGoal(@RequestBody Goal newGoal, @PathVariable Integer uid,
												  @PathVariable Integer goalid) {
		currentGoalRepo.updateCurrentGoal(uid, goalid, newGoal);
		return newGoal;
	}

	@DeleteMapping(path = "/{uid}/currentgoal/{goalid}")
	public @ResponseBody void deleteCurrentGoal(@PathVariable Integer uid, @PathVariable Integer goalid) {
		currentGoalRepo.deleteCurrentGoal(uid, goalid);
		currentGoalRepo.deleteUserCurrentGoal(uid, goalid);
	}

	// Excluded Intervals

	@GetMapping(path = "/{uid}/excludedinterval")
	public @ResponseBody Iterable<ExcludedInterval> getAllExcludedIntervals(@PathVariable Integer uid) {
		return excludedIntervalRepo.findAllExcludedIntervals(uid);
	}

	@PostMapping(path = "/{uid}/excludedinterval")
	public @ResponseBody ExcludedInterval addNewExcludedInterval(@RequestBody ExcludedInterval newExcludedInterval, @PathVariable Integer uid) {
		excludedIntervalRepo.addNewExcludedInterval(uid, newExcludedInterval);
		excludedIntervalRepo.addNewUserExcludedInterval(uid, newExcludedInterval);
		return newExcludedInterval;
	}

	@PutMapping(path = "/{uid}/excludedinterval/{excludedintervalid}")
	public @ResponseBody ExcludedInterval updateExcludedInterval(@RequestBody ExcludedInterval newExcludedInterval, @PathVariable Integer uid,
												@PathVariable Integer excludedintervalid) {
		excludedIntervalRepo.updateExcludedInterval(uid, excludedintervalid, newExcludedInterval);
		return newExcludedInterval;
	}

	@DeleteMapping(path = "/{uid}/excludedinterval/{excludedintervalid}")
	public @ResponseBody void deleteExcludedInterval(@PathVariable Integer uid, @PathVariable Integer excludedintervalid) {
		excludedIntervalRepo.deleteExcludedInterval(uid, excludedintervalid);
		excludedIntervalRepo.deleteUserExcludedInterval(uid, excludedintervalid);
	}

	// Recurring Events

	@GetMapping(path = "/{uid}/recurring")
	public @ResponseBody Iterable<Recurring> getAllRecurringEvents(@PathVariable Integer eventid) {
		return recurringEventRepo.findAllRecurringEvents(eventid);
	}

	@PostMapping(path = "/{uid}/recurring")
	public @ResponseBody Recurring addNewRecurringEvent(@RequestBody Recurring newRecurringEvent, @PathVariable Integer eventid) {
		recurringRepo.addNewRecurringEvent(eventid, newRecurringEvent);
		recurringRepo.addNewEventRecurringEvent(eventid, newRecurringEvent);
		return newRecurringEvent;
	}

	@PutMapping(path = "/{uid}/recurring/{recurringid}")
	public @ResponseBody Recurring updateRecurringEvent(@RequestBody Recurring newRecurringEvent, @PathVariable Integer eventid,
																 @PathVariable Integer recurringid) {
		recurringRepo.updateRecurringEvent(eventid, recurringid, newRecurringEvent);
		return newRecurringEvent;
	}

	@DeleteMapping(path = "/{uid}/recurring/{recurringid}")
	public @ResponseBody void deleteRecurringEvent(@PathVariable Integer eventid, @PathVariable Integer recurringid) {
		recurringRepo.deleteRecurringEvent(eventid, recurringid);
		recurringRepo.deleteEventRecurringEvent(eventid, recurringid);
	}
}
