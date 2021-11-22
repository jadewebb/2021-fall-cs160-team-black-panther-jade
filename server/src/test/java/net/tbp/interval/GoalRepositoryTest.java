package net.tbp.interval;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import net.tbp.interval.backup.Goal;
import net.tbp.interval.backup.UserProfile;
import net.tbp.interval.database.GoalRepository;

/**
 * Class for testing Goal Repo
 *
 * @author Jade Webb
 *
 */

@DataJpaTest

public class GoalRepositoryTest {

	@Autowired

	private GoalRepository goalRepo;

	private UserProfile testProfile1, testProfile2;

	private Goal testGoal1, testGoal2, testGoal3;

	@BeforeEach

	void setUp() {

		testProfile1 = new UserProfile(1, "John", "Doe", "jd123", "r53S2?2H8", "jd123@gmail.com", "0001234567");

		testProfile1 = new UserProfile(2, "Mary", "Jane", "mj441", "k*nT%84SW", "mj441@gmail.com", "0009876543");

		testGoal1 = new Goal(1, "Walk 2 miles", "walk in neighborhood", LocalDateTime.of(2021, 11, 30, 23, 59), 4);

		testGoal2 = new Goal(2, "Eat a salad", "make and eat a healthy salad", LocalDateTime.of(2021, 11, 26, 23, 59), 6);

		testGoal3 = new Goal(3, "Bike to work", "ride bike to work, make sure to leave early", LocalDateTime.of(2021, 11, 22, 23, 59), 3);
	}

	@Test

	void tryAddTestGoals() {

		// add the 3 goals

		goalRepo.addNewCurrentGoal(testProfile1.getId(), testGoal1);
		goalRepo.addNewUserCurrentGoal(testProfile1.getId(), testGoal1);

		goalRepo.addNewCurrentGoal(testProfile1.getId(), testGoal2);
		goalRepo.addNewUserCurrentGoal(testProfile1.getId(), testGoal2);

		goalRepo.addNewCurrentGoal(testProfile2.getId(), testGoal3);
		goalRepo.addNewUserCurrentGoal(testProfile2.getId(), testGoal3);

		// fetch the goals

		List<Goal> currentGoalsForT1 = goalRepo.findAllCurrentGoals(testProfile1.getId());

		List<Goal> currentGoalsForT2 = goalRepo.findAllCurrentGoals(testProfile2.getId());

		// check if all goals are added

		assertEquals(currentGoalsForT1.size(), 2);

		assertEquals(currentGoalsForT2.size(), 1);

		assertEquals(currentGoalsForT1.get(0), testGoal1);

		assertEquals(currentGoalsForT1.get(1), testGoal2);

		assertEquals(currentGoalsForT2.get(2), testGoal3);
	}


	@Test

	void updateOneGoal() {

		Goal testGoal4 = new Goal(3, "Bike to work", "Bike to and from work, make sure to leave early", LocalDateTime.of(2021, 11, 22, 23, 59), 4);

		goalRepo.updateCurrentGoal(testProfile2.getId(), testGoal3.getId(), testGoal4);

		Optional<Goal> targetCheck = goalRepo.findById(3);

		assertTrue(targetCheck.isPresent());

		assertEquals(targetCheck.get().getName(), testGoal4.getName());

	}



	@Test

	void tryDeleteGoal() {

		goalRepo.deleteCurrentGoal(testProfile1.getId(), 2);
		goalRepo.deleteUserCurrentGoal(testProfile1.getId(), 2);

		List<Goal> currentGoalsForT1 = goalRepo.findAllCurrentGoals(testProfile1.getId());

		assertEquals(currentGoalsForT1.size(), 1);

		assertFalse(currentGoalsForT1.contains(testGoal2));

	}



	@AfterEach

	void tearDown() {

		goalRepo.deleteAll();

	}

}