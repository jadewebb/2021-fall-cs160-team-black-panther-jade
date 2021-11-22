package net.tbp.interval.database;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import net.tbp.interval.backup.ExcludedInterval;


/**
 * Class for representing an Excluded Interval Repository.
 *
 * @author Jade Webb
 *
 */
public interface ExcludedIntervalRepository extends JpaRepository<Event, Integer> {

	// SELECT
	@Query(value = "SELECT * FROM excludedinterval WHERE excludedintervalID IN (SELECT excludedintervalID FROM userhasexcludedinterval WHERE userID = :uid)", nativeQuery = true)
	List<ExcludedInterval> findAllCurrentExcludedIntervals(@Param("uid") Integer uid);

	// INSERT
	@Query(value = "INSERT INTO excludedinterval(excludedintervalID, start, end) "
			+ "VALUES(:newExcludedInterval.getID(), :newExcludedInterval.getStartTime(), :newExcludedInterval.getEndTime())", nativeQuery = true);
	void addNewExcludedInterval(@Param("uid") Integer uid, @Param("newExcludedInterval") ExcludedInterval newExcludedInterval);

	@Query(value = "INSERT INTO userhasexcludedinterval VALUES (:uid, :newExcludedInterval.getID())", nativeQuery = true)
	void addNewUserExcludedInterval(@Param("uid") Integer uid, @Param("newExcludedInterval") ExcludedInterval newExcludedInterval);

	// UPDATE
	@Query(value = "UPDATE excludedinterval "
			+ "SET start = :newExcludedInterval.getStartTime(), end = :newExcludedInterval.getEndTime() "
			+ "WHERE excludedintervalID = :exid", nativeQuery = true)
	void updateExcludedInterval(@Param("uid") Integer uid, @Param("exid") Integer exid, @Param("newExcludedInterval") ExcludedInterval newExcludedInterval);

	// DELETE
	@Query(value = "DELETE FROM excludedinterval WHERE excludedintervalID = :exid", nativeQuery = true)
	void deleteExcludedInterval(@Param("uid") Integer uid, @Param("exid") Integer exid);

	@Query(value = "DELETE FROM userhasexcludedinterval WHERE userID = :uid AND excludedintervalID = :exid", nativeQuery = true)
	void deleteUserExcludedInterval(@Param("uid") Integer uid, @Param("exid") Integer exid);
	
}
