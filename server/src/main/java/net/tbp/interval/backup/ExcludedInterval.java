package net.tbp.interval.backup;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Class for representing an Excluded Interval object.
 *
 * @author Jade Webb
 *
 */
@Entity
public class ExcludedInterval {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	@Column(name = "id")
	private Integer id;

	// date-time format: 2011-12-03T10:15:30+01:00
	private LocalDateTime startTime;

	// date-time format: 2011-12-03T10:15:30+01:00
	private LocalDateTime endTime;

	public ExcludedInterval() {
		this(0, null, null);
	}

	/**
	 * All arg constructor.
	 *
	 * @param id
	 * @param startTime
	 * @param endTime
	 */
	public ExcludedInterval(Integer id, LocalDateTime startTime, LocalDateTime endTime) {
		this.id = id;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	/**
	 * @return excluded interval id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id - id to be set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return excluded interval startTime
	 */
	public LocalDateTime getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime - startTime to be set
	 */
	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return excluded interval endTime
	 */
	public LocalDateTime getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime - endTime to be set
	 */
	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	@Override
	public int hashCode() {
		return Objects.hash(endTime, id, startTime);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExcludedInterval other = (ExcludedInterval) obj;
		return Objects.equals(endTime, other.endTime) && Objects.equals(id, other.id)
				&& Objects.equals(startTime, other.startTime);
	}
}