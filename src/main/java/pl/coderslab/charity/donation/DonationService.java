package pl.coderslab.charity.donation;

import pl.coderslab.charity.user.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DonationService {
    Optional<Donation> getDonation(Long id);
    void addDonation(Donation donation);
    List<Donation> getAllDonations();
    void updateDonation(Donation donation);
    Optional<Integer> getSumOfBags();
    Optional<Integer> getSumOfDonations();
    List<Donation>usersDonations(Long id);
    List<Donation>getAlreadyCollectedDonations(LocalDateTime currentDate, User user);
    List<Donation>getNotCollectedYetDonations(LocalDateTime currentDate, User user);
}
