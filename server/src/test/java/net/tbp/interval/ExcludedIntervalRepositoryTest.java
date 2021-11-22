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
import net.tbp.interval.backup.ExcludedInterval;
import net.tbp.interval.backup.UserProfile;
import net.tbp.interval.database.ExcludedIntervalRepository;

/**
 * Class for testing Excluded Interval Repo
 *
 * @author Jade Webb
 *
 */

@DataJpaTest

public class ExcludedIntervalRepositoryTest {

	@Autowired

	private ExcludedIntervalRepository exclRepo;

	private UserProfile testProfile1, testProfile2;

	private ExcludedInterval testExcl1, testExcl2, testExcl3;

	@BeforeEach

	void setUp() {

		testProfile1 = new UserProfile(1, "John", "Doe", "jd123", "r53S2?2H8", "jd123@gmail.com", "0001234567");

		testProfile1 = new UserProfile(2, "Mary", "Jane", "mj441", "k*nT%84SW", "mj441@gmail.com", "0009876543");

		testExcl1 = new ExcludedInterval(1, LocalDateTime.of(2021, 11, 25, 9, 0), LocalDateTime.of(2021, 11, 25, 20, 0));

		testExcl2 = new ExcludedInterval(2, LocalDateTime.of(2021, 11, 30, 12, 0), LocalDateTime.of(2021, 7, 30, 15, 0));

		testExcl3 = new ExcludedInterval(3, LocalDateTime.of(2021, 11, 22, 15, 0), LocalDateTime.of(2021, 11, 22, 23, 0));

	}

	@Test

	void tryAddTestExcludedIntervals() {

		// add the 3 excluded intervals

		exclRepo.addNewExcludedInterval(testProfile1.getId(), testExcl1);
		exclRepo.addNewUserExcludedInterval(testProfile1.getId(), testExcl1);

		exclRepo.addNewExcludedInterval(testProfile1.getId(), testExcl2);
		exclRepo.addNewUserExcludedInterval(testProfile1.getId(), testExcl2);

		exclRepo.addNewExcludedInterval(testProfile2.getId(), testExcl3);
		exclRepo.addNewUserExcludedInterval(testProfile2.getId(), testExcl3);

		// fetch the excluded intervals

		List<ExcludedInterval> currentExcludedIntervalsForT1 = exclRepo.findAllCurrentExcludedIntervals(testProfile1.getId());

		List<ExcludedInterval> currentExcludedIntervalsForT2 = exclRepo.findAllCurrentExcludedIntervals(testProfile2.getId());

		// check if all excluded intervals are added

		assertEquals(currentExcludedIntervalsForT1.size(), 2);

		assertEquals(currentExcludedIntervalsForT2.size(), 1);

		assertEquals(currentExcludedIntervalsForT1.get(0), testExcl1);

		assertEquals(currentExcludedIntervalsForT1.get(1), testExcl2);

		assertEquals(currentExcludedIntervalsForT2.get(2), testExcl3);
	}


	@Test

	void updateOneExcludedInterval() {

		ExcludedInterval testExcl4 = new ExcludedInterval(3, LocalDateTime.of(2021, 11, 22, 8, 0), LocalDateTime.of(2021, 11, 22, 23, 0));

		exclRepo.updateExcludedInterval(testProfile2.getId(), testExcl3.getId(), testExcl4);

		Optional<ExcludedInterval> targetCheck = exclRepo.findById(3);

		assertTrue(targetCheck.isPresent());

		assertEquals(targetCheck.get().getName(), testExcl4.getName());

	}



	@Test

	void tryDeleteExcludedInterval() {

		exclRepo.deleteExcludedInterval(testProfile1.getId(), 2);
		exclRepo.deleteUserExcludedInterval(testProfile1.getId(), 2);

		List<ExcludedInterval> currentExcludedIntervalsForT1 = exclRepo.findAllCurrentExcludedIntervals(testProfile1.getId());

		assertEquals(currentExcludedIntervalsForT1.size(), 1);

		assertFalse(currentExcludedIntervalsForT1.contains(testExcl2));

	}



	@AfterEach

	void tearDown() {

		exclRepo.deleteAll();

	}

}