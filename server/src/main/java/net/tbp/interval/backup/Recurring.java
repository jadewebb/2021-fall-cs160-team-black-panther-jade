package net.tbp.interval.backup;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Class for representing an Recurring object.
 *
 * @author Jade Webb
 *
 */
@Entity
public class Recurring {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	@Column(name = "id")
	private Integer id;

	@Column(name = "frequency")
	private Integer frequency;

	@Column(name = "range")
	private Integer range;

	// date-time format: 2011-12-03T10:15:30+01:00
	private LocalDateTime endTime;

	public Recurring() {
		this(0, 0, 0, null);
	}

	/**
	 * All arg constructor.
	 *
	 * @param id
	 * @param frequency
	 * @param range
	 * @param endTime
	 */
	public Recurring(Integer id, Integer frequency, Integer range, LocalDateTime endTime) {
		this.id = id;
		this.frequency = frequency;
		this.range = range;
		this.endTime = endTime;
	}

	/**
	 * @return recurring id
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
	 * @return recurring frequency
	 */
	public Integer getFrequency() {
		return frequency;
	}

	/**
	 * @param frequency - frequency to be set
	 */
	public void setFrequency(Integer frequency) { this.frequency = frequency; }

	/**
	 * @return recurring range
	 */
	public Integer getRange() {
		return range;
	}

	/**
	 * @param range - range to be set
	 */
	public void setRange(Integer range) {
		this.range = range;
	}

	/**
	 * @return recurring endTime
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
		return Objects.hash(endTime, range, frequency, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Recurring other = (Recurring) obj;
		return Objects.equals(endTime, other.endTime) && Objects.equals(range, other.range)
				&& Objects.equals(frequency, other.frequency) && Objects.equals(id, other.id);
	}
}