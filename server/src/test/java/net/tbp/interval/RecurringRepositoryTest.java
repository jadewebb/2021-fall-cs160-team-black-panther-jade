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
import net.tbp.interval.backup.Recurring;
import net.tbp.interval.backup.Event;
import net.tbp.interval.database.RecurringRepository;

/**
 * Class for testing Recurring Repo
 *
 * @author Jade Webb
 *
 */

@DataJpaTest

public class RecurringRepositoryTest {

	@Autowired

	private RecurringRepository recurRepo;

	private UserProfile testEvent1, testEvent2, testEvent3;

	private Recurring testRecur1, testRecur2, testRecur3;

	@BeforeEach

	void setUp() {

		testEvent1 = new Event(1, "Dishes", "Do the dishes.", "Home", "Housework", "none",

				LocalDateTime.of(2021, 7, 24, 5, 2), LocalDateTime.of(2021, 7, 24, 6, 2));

		testEvent2 = new Event(2, "Homework", "Do my homework.", "Home", "Schoolwork", "none",

				LocalDateTime.of(2021, 7, 24, 6, 2), LocalDateTime.of(2021, 7, 24, 7, 2));

		testEvent3 = new Event(3, "Meeting", "Team meeting stuff.", "Home", "Schoolwork", "none",

				LocalDateTime.of(2021, 7, 24, 7, 2), LocalDateTime.of(2021, 7, 24, 8, 2));

		testRecur1 = new Recurring(1, 2, 3, LocalDateTime.of(2021, 12, 31, 23, 59));

		testRecur2 = new Recurring(2, 4, 2, LocalDateTime.of(2021, 12, 31, 23, 59));

		testRecur3 = new Recurring(3, 1, 3, LocalDateTime.of(2021, 12, 31, 23, 59));
	}

	@Test

	void tryAddTestRecurrings() {

		// add the 3 recurrings

		recurRepo.addNewRecurringEvent(testEvent1.getId(), testRecur1);
		recurRepo.addNewEventRecurringEvent(testEvent1.getId(), testRecur1);

		recurRepo.addNewRecurringEvent(testEvent2.getId(), testRecur2);
		recurRepo.addNewEventRecurringEvent(testEvent2.getId(), testRecur2);

		recurRepo.addNewRecurringEvent(testEvent3.getId(), testRecur3);
		recurRepo.addNewEventRecurringEvent(testEvent3.getId(), testRecur3);

		// fetch the recurrings

		List<Recurring> currentRecurringForT1 = recurRepo.findAllRecurringEvents(testEvent1.getId());

		List<Recurring> currentRecurringForT2 = recurRepo.findAllRecurringEvents(testEvent2.getId());

		List<Recurring> currentRecurringForT3 = recurRepo.findAllRecurringEvents(testEvent3.getId());

		// check if all recurrings are added

		assertEquals(currentRecurringForT1.size(), 1);

		assertEquals(currentRecurringForT2.size(), 1);

		assertEquals(currentRecurringForT3.size(), 1);

		assertEquals(currentRecurringForT1.get(0), testRecur1);

		assertEquals(currentRecurringForT2.get(1), testRecur2);

		assertEquals(currentRecurringForT3.get(2), testRecur3);
	}


	@Test

	void updateOneRecurring() {

		Recurring testRecur4 = new Recurring(3, 3, 3, LocalDateTime.of(2022, 1, 20, 23, 59));

		recurRepo.updateCurrentRecurring(testEvent2.getId(), testRecur3.getId(), testRecur4);

		Optional<Recurring> targetCheck = recurRepo.findById(3);

		assertTrue(targetCheck.isPresent());

		assertEquals(targetCheck.get().getName(), testRecurring4.getName());

	}



	@Test

	void tryDeleteRecurring() {

		recurRepo.deleteRecurringEvent(testEvent2.getId(), 2);
		recurRepo.deleteEventRecurringEvent(testEvent2.getId(), 2);

		List<Recurring> currentRecurringForT2 = recurRepo.findAllRecurringEvents(testEvent2.getId());

		assertEquals(currentRecurringForT2.size(), 0);

		assertFalse(currentRecurringForT2.contains(testRecur2));

	}



	@AfterEach

	void tearDown() {

		recurRepo.deleteAll();

	}

}