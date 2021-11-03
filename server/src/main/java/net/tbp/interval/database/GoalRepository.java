package net.tbp.interval.database;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import net.tbp.interval.backup.Event;


/**
 * Class for representing a Goal Repository.
 *
 * @author Jade Webb
 *
 */
public interface GoalRepository extends JpaRepository<Event, Integer> {

	// SELECT
	@Query(value = "SELECT * FROM currentgoal WHERE goalID IN (SELECT goalID FROM userhascurrentgoal WHERE userID = :uid)", nativeQuery = true)
	List<Goal> findAllCurrentGoals(@Param("uid") Integer uid);

	// INSERT
	@Query(value = "INSERT INTO currentgoal(goalID, name, description, deadline, priority) "
			+ "VALUES(:newGoal.getID(), :newGoal.getName(), :newGoal.getDescription(), :newGoal.getDeadline(), :newGoal.getPriority())", nativeQuery = true);
	void addNewCurrentGoal(@Param("uid") Integer uid, @Param("newGoal") Goal newGoal);

	@Query(value = "INSERT INTO userhascurrentgoal VALUES (:uid, :newGoal.getID())", nativeQuery = true)
	void addNewUserCurrentGoal(@Param("uid") Integer uid, @Param("newGoal") Goal newGoal);

	// UPDATE
	@Query(value = "UPDATE currentgoal "
			+ "SET name = :newGoal.getName(), description = :newGoal.getDescription(), deadline = :newGoal.getDeadline(), priority = :newGoal.getPriority() "
			+ "WHERE goalID = :gid", nativeQuery = true)
	void updateCurrentGoal(@Param("uid") Integer uid, @Param("gid") Integer gid, @Param("newGoal") Goal newGoal);

	// DELETE
	@Query(value = "DELETE FROM currentgoal WHERE goalID = :gid", nativeQuery = true)
	void deleteCurrentGoal(@Param("uid") Integer uid, @Param("gid") Integer gid);

	@Query(value = "DELETE FROM userhascurrentgoal WHERE userID = :uid AND goalID = :gid", nativeQuery = true)
	void deleteUserCurrentGoal(@Param("uid") Integer uid, @Param("gid") Integer gid);
	
}
