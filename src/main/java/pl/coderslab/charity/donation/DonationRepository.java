package pl.coderslab.charity.donation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.coderslab.charity.model.Donation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface DonationRepository extends JpaRepository<Donation,Long> {

//    @Query(value ="SELECT SUM(bag_quantity)FROM donations" ,nativeQuery = true)
    @Query(value ="SELECT SUM(d.quantity) FROM Donation d ")
    Optional<Integer> getNumberOfBags();

    @Query(value = "SELECT count(d) FROM Donation d")
    Optional<Integer> getSumOfDonations();
//all users donation
    List<Donation> findAllByUserId(Long id);
//  all users donation collected
    @Query(value = "SELECT * From donations where pick_up_date > ? or pick_up_date = ? and  pick_up_time > ?;", nativeQuery = true)
    List<Donation> allNotCollected(LocalDate day, LocalDate day1, LocalTime time );
//    List<Donation> findAllByPickUpDateBeforeAndPickUpTimeBefore(LocalDate today, LocalTime time);
//    List<Donation> findAllByPickUpDateBefore(LocalDate today);
//    all users donation not collected
    @Query(value="SELECT * From donations where pick_up_date < ? or pick_up_date = ? and  pick_up_time < ?;", nativeQuery = true)
    List<Donation>allCollected(LocalDate day, LocalDate day1, LocalTime time );
//    List<Donation> findAllByPickUpDateAfterAndPickUpTimeAfter(LocalDate today, LocalTime time);
//    List<Donation> findAllByPickUpDateAfter(LocalDate today);



}
