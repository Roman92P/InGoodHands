package pl.coderslab.charity.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.donation.DonationService;
import pl.coderslab.charity.model.Donation;
import pl.coderslab.charity.model.User;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.security.Principal;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController {

    Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final SpringDataUserDetailsService springDataUserDetailsService;
    private final DonationService donationService;

    public UserController(UserService userService, SpringDataUserDetailsService springDataUserDetailsService, DonationService donationService) {
        this.userService = userService;
        this.springDataUserDetailsService = springDataUserDetailsService;
        this.donationService = donationService;
    }

    @RequestMapping("/profile")
    public String getUserProfile(Principal principal, Model model, @AuthenticationPrincipal CurrentUser currentUser){
        String name = principal.getName();
        User byUserName = userService.findByUserName(name).orElseThrow(EntityNotFoundException::new);
        model.addAttribute("user", byUserName);
        return"userViews/userProfile";
    }

    @PostMapping("/profile")
    public String editUserProfile(@Valid User user, BindingResult result, Model model, @AuthenticationPrincipal CurrentUser currentUser){
        if(result.hasErrors()){
            User byUserName = userService.findByUserName(user.getUserName()).orElseThrow(EntityNotFoundException::new);
            model.addAttribute("user", byUserName);
            return"userViews/userProfile";
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        user.getRoles().forEach(r ->
                grantedAuthorities.add(new SimpleGrantedAuthority(r.getName())));
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword(), grantedAuthorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        userService.updateUser(user);
        return "redirect:/user/profile";
    }

    @RequestMapping("/donations")
    public String viewAllUserDonations(Model model, @AuthenticationPrincipal CurrentUser user){
        Long id = user.getUser().getId();
        List<Donation> donations = donationService.usersDonations(id);
        List<Donation> donationList = donations.stream().sorted(Comparator.comparing(Donation::getPickUpDate)).collect(Collectors.toList());
        model.addAttribute("allDonations", donationList);
        LocalDate currentDate = LocalDate.now();
        LocalTime currentT = LocalTime.now();
        String timeNow = currentT.format(DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime parse = LocalTime.parse(timeNow);
        model.addAttribute("dateNow", currentDate);
        model.addAttribute("timeNow", parse);

//        log.error("Czas: "+timeNow);
//        log.error("Data: "+ currentDate);
        for ( Donation d:donations ){
            log.error("czas z db: "+ d.getPickUpTime());
        }

        return "userViews/userDonations";
    }

    @RequestMapping("/donations/collected")
    public String viewAllCollectedUserDonations(Model model, @AuthenticationPrincipal CurrentUser user){
        Long id = user.getUser().getId();
        List<Donation> donations = donationService.getAlreadyCollectedDonations(LocalDateTime.now(),user.getUser());
        List<Donation> donationList = donations.stream().sorted(Comparator.comparing(Donation::getPickUpDate)).collect(Collectors.toList());
        model.addAttribute("allCollectedDonations", donationList);
        LocalDate currentDate = LocalDate.now();
        LocalTime currentT = LocalTime.now();
        String timeNow = currentT.format(DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime parse = LocalTime.parse(timeNow);
        model.addAttribute("dateNow", currentDate);
        model.addAttribute("timeNow", parse);
        return "userViews/userCollectedDonations";
    }

    @RequestMapping("/donations/notcollected")
    public String viewAllNotCollectedUserDonations(Model model, @AuthenticationPrincipal CurrentUser user){
        Long id = user.getUser().getId();
        List<Donation> donations = donationService.getNotCollectedYetDonations(LocalDateTime.now(),user.getUser());
        List<Donation> donationList = donations.stream().sorted(Comparator.comparing(Donation::getCreatedOn)).collect(Collectors.toList());
        model.addAttribute("allNotCollectedDonations", donationList);
        LocalDate currentDate = LocalDate.now();
        LocalTime currentT = LocalTime.now();
        String timeNow = currentT.format(DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime parse = LocalTime.parse(timeNow);
        model.addAttribute("dateNow", currentDate);
        model.addAttribute("timeNow", parse);
        return "userViews/userNotCollectedDonations";
    }

    @PostMapping("/donations/details/changeStatus")
    public String changeDonationDetailsStatus(@ModelAttribute("statusDonation") Donation donation){
        Donation donation1 = donationService.getDonation(donation.getId()).orElseThrow(EntityNotFoundException::new);
        if(donation.getStatus().equals("nieodebrany")){
            LocalDateTime localDateTime = LocalDateTime.from(LocalDateTime.now().atZone(ZoneId.of("Europe/Warsaw"))).plusDays(2);
            donation.setPickUpDateTime(localDateTime);
            LocalDate localDate = localDateTime.toLocalDate();
            LocalTime localTime = localDateTime.toLocalTime();
            log.error("New time: "+ localTime);
            donation.setPickUpDate(String.valueOf(localDate));
            donation.setPickUpTime(localTime);
            donation.setCreatedOn(donation1.getCreatedOn());
            donationService.updateDonation(donation);
            return "redirect:/user/donations";
        }
        donation.setPickUpDateTime(LocalDateTime.now());
        donation.setPickUpDate(String.valueOf(LocalDate.now()));
        donation.setPickUpTime(LocalTime.now());
        donation.setCreatedOn(donation1.getCreatedOn());
        donationService.updateDonation(donation);
        return "redirect:/user/donations";
    }


    @ModelAttribute("statusDonation")
    public Donation updateStatusDonation(){
        return new Donation();
    }

}
