package net.tbp.interval.database;

import org.springframework.data.jpa.repository.JpaRepository;

import net.tbp.interval.backup.UserProfile;

/**
 * Class for representing a User Repository.
 *
 * @author Hugo Wong
 * @author Jade Webb
 *
 */
public interface UserRepository extends JpaRepository<UserProfile, Integer> {

    // SELECT
    @Query(value = "SELECT * FROM user WHERE username = :target", nativeQuery = true)
    UserProfile findProfileByName(@Param("target") String username);

}
