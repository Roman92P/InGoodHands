package pl.coderslab.charity.donation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.coderslab.charity.model.Donation;
import pl.coderslab.charity.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
//  all users donation collected NOT USED
    @Query(value = "SELECT * From donations where user_id=? and pick_up_date > ? or pick_up_date = ? and  pick_up_time > ?;", nativeQuery = true)
    List<Donation> allNotCollected(Long id,LocalDate day, LocalDate day1, LocalTime time);

//    all users donation not collected NOT USED
    @Query(value="SELECT * From donations where user_id=? and pick_up_date < ? or pick_up_date = ? and  pick_up_time < ?;", nativeQuery = true)
    List<Donation>allCollected(Long id,LocalDate day, LocalDate day1, LocalTime time);

//    @Query(value = "SELECT d.id, d.city, d.createdOn, d.phoneNumber, d.pickUpComment," +
//            "d.pickUpDate,d.pickUpTime, d.quantity, d.street,d.zipCode, d.institution.name, d.user.id " +
//            "From Donation d where d.pickUpDateTime<:nowDateTime and d.user.id=:userId")
//    List<Donation> getDonationsCollected(LocalDateTime nowDateTime, Long userId);
//
//    @Query(value = "SELECT d.id, d.city, d.createdOn, d.phoneNumber, d.pickUpComment," +
//            "d.pickUpDate,d.pickUpTime, d.quantity, d.street,d.zipCode, d.institution.name, d.user.id " +
//            "From Donation d where d.pickUpDateTime>:nowDateTime and d.user.id=:userId")
//    List<Donation> getDonationsNotCollected(LocalDateTime nowDateTime, Long userId);

//USED
    @Query(value = "SELECT d.id, d.quantity, d.user.userName, d.institution.name," +
            "d.street,d.city, d.zipCode, d.pickUpDate,d.pickUpTime, d.pickUpComment, d.phoneNumber,d.createdOn,d.user.id " +
            "From Donation d where d.pickUpDateTime<:nowDateTime and d.user.id=:userId")
    List<Object[]> getDonationsCollected(LocalDateTime nowDateTime, Long userId);
//USED
    @Query(value = "SELECT d.id, d.quantity, d.user.userName, d.institution.name," +
            "d.street,d.city, d.zipCode, d.pickUpDate,d.pickUpTime, d.pickUpComment, d.phoneNumber,d.createdOn,d.user.id " +
            "From Donation d where d.pickUpDateTime>:nowDateTime and d.user.id=:userId")
    List<Object[]> getDonationsNotCollected(LocalDateTime nowDateTime, Long userId);

    @Query(value = "SELECT d FROM Donation d where d.pickUpDateTime<:currentDate and d.user =:user")
    List<Donation> getAlreadyCollectedDonations(LocalDateTime currentDate, User user);

    @Query(value = "SELECT d FROM Donation d where d.pickUpDateTime>:currentDate and d.user =:user")
    List<Donation> getNotCollectedYetDonations(LocalDateTime currentDate, User user);


}
