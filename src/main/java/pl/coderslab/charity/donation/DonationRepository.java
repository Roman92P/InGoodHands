package pl.coderslab.charity.donation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.coderslab.charity.user.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DonationRepository extends JpaRepository<Donation,Long> {

    @Query(value ="SELECT SUM(d.quantity) FROM Donation d ")
    Optional<Integer> getNumberOfBags();

    @Query(value = "SELECT count(d) FROM Donation d")
    Optional<Integer> getSumOfDonations();

    List<Donation> findAllByUserId(Long id);

    @Query(value = "SELECT d FROM Donation d where d.pickUpDateTime<:currentDate and d.user =:user")
    List<Donation> getAlreadyCollectedDonations(LocalDateTime currentDate, User user);

    @Query(value = "SELECT d FROM Donation d where d.pickUpDateTime>:currentDate and d.user =:user")
    List<Donation> getNotCollectedYetDonations(LocalDateTime currentDate, User user);


}
