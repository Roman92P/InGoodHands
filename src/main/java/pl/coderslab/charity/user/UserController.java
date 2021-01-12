package pl.coderslab.charity.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.donation.DonationService;
import pl.coderslab.charity.model.Donation;
import pl.coderslab.charity.model.User;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController {

    Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private Validator validator;

    private final UserService userService;
    private final DonationService donationService;
    private final MessageSource messageSource;
    private final UserPasswordValidator userPasswordValidator;


    public UserController(UserService userService, SpringDataUserDetailsService springDataUserDetailsService, DonationService donationService, MessageSource messageSource, UserPasswordValidator userPasswordValidator) {
        this.userService = userService;
        this.donationService = donationService;
        this.messageSource = messageSource;
        this.userPasswordValidator = userPasswordValidator;
    }

    @GetMapping("/profile")
    public String getUserProfile(Principal principal, Model model, @AuthenticationPrincipal CurrentUser currentUser) {
        String name = principal.getName();
        User byUserName = userService.findByUserName(name).orElseThrow(EntityNotFoundException::new);
        model.addAttribute("user", byUserName);
        return "userViews/userProfile";
    }

    @GetMapping("/profile/changePassword")
    public String userChangeCurrentPassword(Model model, Principal principal) {
        User user = userService.findByUserName(principal.getName()).orElseThrow(EntityNotFoundException::new);
        UserDTO userDTO = new UserDTO();
        userDTO.setUserEmail(user.getUserEmail());
        model.addAttribute("userDTO", userDTO);
        return "userViews/userProfileChangePassword";
    }

    @PostMapping("/profile/changePassword")
    public String userChangeCurrentPassword(UserDTO userDTO, Locale locale, Model model) {
        String result = userPasswordValidator.validatePasswordMatch(userDTO);
        String result1 = userPasswordValidator.validateCurrentUserPassword(userDTO);
        if (result != null) {
            String message1 = messageSource.getMessage("password.matches." + result, null, locale);
            return "redirect:/user/profile/changePassword?lang=" + locale.getLanguage() + "&message1=" + message1;
        }
        if (result1 != null) {
            String message2 = messageSource.getMessage("user.profile.password.change." + result1, null, locale);
            return "redirect:/user/profile/changePassword?lang=" + locale.getLanguage() + "&message2=" + message2;
        }

        User user = userService.findByUserEmail(userDTO.getUserEmail()).orElseThrow(EntityNotFoundException::new);
        user.setPassword(userDTO.getPassword());
        Set<ConstraintViolation<User>> validate = validator.validate(user);
        if(!validate.isEmpty()){
            StringBuilder stringBuilder = new StringBuilder();
            for(ConstraintViolation<User> constraintViolation : validate){
                stringBuilder.append(constraintViolation.getMessage());
            }
            String s = stringBuilder.toString();
            model.addAttribute("errors", s);
            return "userViews/userProfileChangePassword";
        }
        userService.updateUserPassword(user, userDTO.getPassword());
        String message1 = messageSource.getMessage("user.profile.password.successChange", null, locale);
        return "redirect:/user/profile?lang=" + locale.getLanguage() + "&successPasswordMessage=" + message1;
    }

    private void updateAuthorities(User user) {
        userService.updateUser(user);
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        user.getRoles().forEach(r ->
                grantedAuthorities.add(new SimpleGrantedAuthority(r.getName())));
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword(), grantedAuthorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }


    @PostMapping("/profile")
    public String editUserProfile(@Valid User user, BindingResult result, UserDTO userDTO,
                                  Model model, @AuthenticationPrincipal CurrentUser currentUser) {
        if (result.hasErrors()) {
            User byUserName = userService.findByUserName(user.getUserName()).orElseThrow(EntityNotFoundException::new);
            model.addAttribute("user", byUserName);
            return "userViews/userProfile";
        }
        updateAuthorities(user);
        return "redirect:/user/profile";
    }

    @GetMapping("/donations")
    public String viewAllUserDonations(Model model, @AuthenticationPrincipal CurrentUser user) {
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
        return "userViews/userDonations";
    }

    @GetMapping("/donations/collected")
    public String viewAllCollectedUserDonations(Model model, @AuthenticationPrincipal CurrentUser user) {
        Long id = user.getUser().getId();
        List<Donation> donations = donationService.getAlreadyCollectedDonations(LocalDateTime.now(), user.getUser());
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

    @GetMapping("/donations/notcollected")
    public String viewAllNotCollectedUserDonations(Model model, @AuthenticationPrincipal CurrentUser user) {
        Long id = user.getUser().getId();
        List<Donation> donations = donationService.getNotCollectedYetDonations(LocalDateTime.now(), user.getUser());
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
    public String changeDonationDetailsStatus(@ModelAttribute("statusDonation") Donation donation) {
        Donation donation1 = donationService.getDonation(donation.getId()).orElseThrow(EntityNotFoundException::new);
        if (donation.getStatus().equals("nieodebrany")) {
            LocalDateTime localDateTime = LocalDateTime.from(LocalDateTime.now().atZone(ZoneId.of("Europe/Warsaw"))).plusDays(2);
            donation.setPickUpDateTime(localDateTime);
            LocalDate localDate = localDateTime.toLocalDate();
            LocalTime localTime = localDateTime.toLocalTime();
            log.error("New time: " + localTime);
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
    public Donation updateStatusDonation() {
        return new Donation();
    }

}
