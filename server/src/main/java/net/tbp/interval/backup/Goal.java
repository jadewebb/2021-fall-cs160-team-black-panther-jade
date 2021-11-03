package net.tbp.interval.backup;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Class for representing a Goal object.
 *
 * @author Jade Webb
 *
 */
@Entity
public class Goal {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	@Column(name = "id")
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	// date-time format: 2011-12-03T10:15:30+01:00
	private LocalDateTime deadline;

	@Column(name = "priority")
	private Integer priority;

	public Goal() {
		this(0, "ERROR", "ERROR", null, 0);
	}

	/**
	 * All arg constructor.
	 *
	 * @param id
	 * @param name
	 * @param description
	 * @param deadline
	 * @param priority
	 */
	public Goal(Integer id, String name, String description, LocalDateTime deadline, Integer priority) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.deadline = deadline;
		this.priority = priority;
	}

	/**
	 * @return goal id
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
	 * @return goal name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name - name to be set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return goal description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description - description to be set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return goal deadline
	 */
	public LocalDateTime getDeadline() {
		return deadline;
	}

	/**
	 * @param deadline - deadline to be set
	 */
	public void setDeadline(LocalDateTime deadline) {
		this.deadline = deadline;
	}

	/**
	 * @return goal priority
	 */
	public Integer getPriority() { return priority; }

	/**
	 * @param priority - priority to be set
	 */
	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, id, priority, description, deadline);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Goal other = (Goal) obj;
		return Objects.equals(name, other.name) && Objects.equals(id, other.id)
				&& Objects.equals(priority, other.priority) && Objects.equals(description, other.description)
				&& Objects.equals(deadline, other.deadline);
	}
}