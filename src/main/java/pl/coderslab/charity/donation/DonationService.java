package pl.coderslab.charity.donation;

import pl.coderslab.charity.model.Donation;

import java.util.List;
import java.util.Optional;

public interface DonationService {
    Optional<Donation> getDonation(Long id);
    void addDonation(Donation donation);
    List<Donation> getAllDonations();
    void updateDonation(Donation donation);
    void removeDonation(Donation donation);
    Optional<Integer> getSumOfBags();
    Optional<Integer> getSumOfDonations();
}
