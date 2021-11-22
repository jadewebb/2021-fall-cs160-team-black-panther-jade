package net.tbp.interval.database;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import net.tbp.interval.backup.Recurring;


/**
 * Class for representing a Recurring Event Repository.
 *
 * @author Jade Webb
 *
 */
public interface RecurringRepository extends JpaRepository<Event, Integer> {

	// SELECT
	@Query(value = "SELECT * FROM recurring WHERE recurringID IN (SELECT recurringID FROM currenteventhasrecurring WHERE eventID = :evid)", nativeQuery = true)
	List<Recurring> findAllRecurringEvents(@Param("evid") Integer evid);

	// INSERT
	@Query(value = "INSERT INTO recurring(recurringID, frequency, range, end) "
			+ "VALUES(:newRecurringEvent.getID(), :newRecurringEvent.getFrequency(), :newRecurringEvent.getRange(), :newRecurringEvent.getEndTime())", nativeQuery = true);
	void addNewRecurringEvent(@Param("evid") Integer evid, @Param("newRecurringEvent") Recurring newRecurringEvent);

	@Query(value = "INSERT INTO currenteventhasrecurring VALUES (:evid, :newRecurringEvent.getID())", nativeQuery = true)
	void addNewEventRecurringEvent(@Param("evid") Integer evid, @Param("newRecurringEvent") Recurring newRecurringEvent);

	// UPDATE
	@Query(value = "UPDATE recurring "
			+ "SET frequency = :newRecurringEvent.getFrequency(), range = :newRecurringEvent.getRange(), end = :newRecurringEvent.getEndTime() "
			+ "WHERE recurringID = :rid", nativeQuery = true)
	void updateRecurringEvent(@Param("evid") Integer evid, @Param("rid") Integer rid, @Param("newRecurringEvent") Recurring newRecurringEvent);

	// DELETE
	@Query(value = "DELETE FROM recurring WHERE recurringID = :rid", nativeQuery = true)
	void deleteRecurringEvent(@Param("evid") Integer evid, @Param("rid") Integer rid);

	@Query(value = "DELETE FROM currenteventhasrecurring WHERE eventID = :evid AND recurringID = :rid", nativeQuery = true)
	void deleteEventRecurringEvent(@Param("evid") Integer evid, @Param("rid") Integer rid);
	
}
