package net.tbp.interval.database;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import net.tbp.interval.backup.Event;


/**
 * Class for representing an Event Repository.
 *
 * @author Hugo Wong
 * @author Jade Webb
 *
 */
public interface EventRepository extends JpaRepository<Event, Integer> {

	// SELECT
	@Query(value = "SELECT * FROM currentevent WHERE eventID IN (SELECT eventID FROM userhascurrentevent WHERE userID = :uid)", nativeQuery = true)
	List<Event> findAllCurrentEvents(@Param("uid") Integer uid);

	// INSERT
	@Query(value = "INSERT INTO currentevent(eventID, name, description, location, category, alarm, start, end) "
			+ "VALUES(:newEvent.getID(), :newEvent.getName(), :newEvent.getDescription(), :newEvent.getLocation(), :newEvent.getCategory(), :newEvent.getAlarm(), :newEvent.getStartTime(), :newEvent.getEndTime())", nativeQuery = true);
	void addNewCurrentEvent(@Param("uid") Integer uid, @Param("newEvent") Event newEvent);

	@Query(value = "INSERT INTO userhascurrentevent VALUES (:uid, :newEvent.getID())", nativeQuery = true)
	void addNewUserCurrentEvent(@Param("uid") Integer uid, @Param("newEvent") Event newEvent);

	// UPDATE
	@Query(value = "UPDATE currentevent "
			+ "SET name = :newEvent.getName(), description = :newEvent.getDescription(), location = :newEvent.getLocation(), category = :newEvent.getCategory(), alarm = :newEvent.getAlarm(), start = :newEvent.getStartTime(), end = :newEvent.getEndTime() "
			+ "WHERE eventID = :evid", nativeQuery = true)
	void updateCurrentEvent(@Param("uid") Integer uid, @Param("evid") Integer evid, @Param("newEvent") Event newEvent);

	// DELETE
	@Query(value = "DELETE FROM currentevent WHERE eventID = :evid", nativeQuery = true)
	void deleteCurrentEvent(@Param("uid") Integer uid, @Param("evid") Integer evid);

	@Query(value = "DELETE FROM userhascurrentevent WHERE userID = :uid AND eventID = :evid", nativeQuery = true)
	void deleteUserCurrentEvent(@Param("uid") Integer uid, @Param("evid") Integer evid);
	
}
