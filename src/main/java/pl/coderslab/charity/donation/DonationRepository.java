package pl.coderslab.charity.donation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.coderslab.charity.model.Donation;

import java.util.Optional;

public interface DonationRepository extends JpaRepository<Donation,Long> {
//    sprawdzić bez native query, ale JPQL
//    jpql nie obsługuje sum, count
    
    @Query(value ="SELECT SUM(bag_quantity)FROM donations" ,nativeQuery = true)
    Optional<Integer> getNumberOfBags();

    @Query(value ="SELECT count(donation_id)FROM donations" ,nativeQuery = true)
    Optional<Integer> getSumOfDonations();


}
