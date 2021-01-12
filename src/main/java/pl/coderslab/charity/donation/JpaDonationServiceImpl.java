package pl.coderslab.charity.donation;

import org.springframework.stereotype.Service;
import pl.coderslab.charity.model.Donation;
import pl.coderslab.charity.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JpaDonationServiceImpl implements DonationService {

    private final DonationRepository donationRepository;

    public JpaDonationServiceImpl(DonationRepository donationRepository){
        this.donationRepository = donationRepository;
    }

    @Override
    public Optional<Donation> getDonation(Long id) {
        return donationRepository.findById(id);
    }

    @Override
    public void addDonation(Donation donation) {
        donationRepository.save(donation);
    }

    @Override
    public List<Donation> getAllDonations() {
        return donationRepository.findAll();
    }

    @Override
    public void updateDonation(Donation donation) {
        donationRepository.save(donation);
    }

    @Override
    public Optional<Integer> getSumOfBags() {
        return donationRepository.getNumberOfBags();
    }

    @Override
    public Optional<Integer> getSumOfDonations() {
        return donationRepository.getSumOfDonations();
    }

    @Override
    public List<Donation> usersDonations(Long id) {
        return donationRepository.findAllByUserId(id);
    }

    @Override
    public List<Donation> getAlreadyCollectedDonations(LocalDateTime currentDate, User user) {
        return donationRepository.getAlreadyCollectedDonations(currentDate, user);
    }

    @Override
    public List<Donation> getNotCollectedYetDonations(LocalDateTime currentDate, User user) {
        return donationRepository.getNotCollectedYetDonations(currentDate, user);
    }

}
