package org.example.DSE105_Assignment_2.Controller;

import org.example.DSE105_Assignment_2.Model.RegistrationForm;
import org.example.DSE105_Assignment_2.Model.User;
import org.example.DSE105_Assignment_2.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthController authController;

    // Displays the home page, and if a user is logged in, adds their data to the model
    @GetMapping("/")
    public String showHomePage(Model model, HttpSession session) {
        User currentUser = AuthController.getCurrentUser(session);
        if (currentUser != null) {
            model.addAttribute("currentUser", currentUser);
        }
        return "index";
    }

    // Displays the registration form page
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("registrationForm", new RegistrationForm());
        return "register";
    }

    // Handles the form submission when a user attempts to register
    @PostMapping("/register")
    public String processRegistration(@ModelAttribute RegistrationForm registrationForm, Model model, HttpSession session) {
        // Validate password match
        if (!registrationForm.getPassword().equals(registrationForm.getConfirmPassword())) {
            model.addAttribute("error", "Passwords do not match");
            return "register";
        }

        // Check if email is already registered
        if (userRepository.existsByEmail(registrationForm.getEmail())) {
            model.addAttribute("error", "Email already registered");
            return "register";
        }

        // Ensure terms and conditions are accepted
        if (!registrationForm.isTermsAccepted()) {
            model.addAttribute("error", "You must accept the terms and conditions");
            return "register";
        }

        // Save the new user and redirect to confirmation
        User newUser = registrationForm.toUser();
        userRepository.save(newUser);
        session.setAttribute("newUser", newUser);
        return "redirect:/registerConfirmation";
    }

    // Displays the confirmation page after a successful registration
    @GetMapping("/registerConfirmation")
    public String showRegistrationConfirmation(Model model, HttpSession session) {
        User newUser = (User) session.getAttribute("newUser");
        if (newUser == null) {
            return "redirect:/";
        }
        model.addAttribute("user", newUser);
        session.removeAttribute("newUser");
        return "registerConfirmation";
    }

    // Displays the profile page for the currently logged-in user
    @GetMapping("/profile")
    public String showUserProfile(HttpSession session, Model model) {
        User currentUser = AuthController.getCurrentUser(session);
        if (currentUser == null) {
            return "redirect:/login";
        }

        Optional<User> refreshedUser = userRepository.findByEmail(currentUser.getEmail());
        if (refreshedUser.isEmpty()) {
            session.removeAttribute("currentUser");
            return "redirect:/login";
        }

        currentUser = refreshedUser.get();
        session.setAttribute("currentUser", currentUser);
        model.addAttribute("user", currentUser);
        return "profile";
    }

    // Displays the public profile of another user by email, if it is set to public
    @GetMapping("/publicProfile/{email}")
    public String showPublicProfile(@PathVariable String email, Model model, HttpSession session) {
        if (!AuthController.isLoggedIn(session)) {
            return "redirect:/login";
        }

        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            model.addAttribute("error", "User not found");
            return "redirect:/search";
        }

        User targetUser = user.get();
        if (!targetUser.isPublic()) {
            model.addAttribute("error", "This user's profile is private");
            return "redirect:/search";
        }

        model.addAttribute("user", targetUser);
        return "publicProfile";
    }

    // Handles the submission to update the profile of the logged-in user
    @PostMapping("/updateProfile")
    public String updateProfile(@ModelAttribute User updatedUser, @RequestParam(defaultValue = "false") boolean isPublic,
                                HttpSession session, Model model) {
        User currentUser = AuthController.getCurrentUser(session);
        if (currentUser == null) {
            return "redirect:/login";
        }

        Optional<User> userOptional = userRepository.findByEmail(currentUser.getEmail());
        if (userOptional.isEmpty()) {
            session.removeAttribute("currentUser");
            return "redirect:/login";
        }

        User userToUpdate = userOptional.get();

        // Update the profile fields with new values from the form
        userToUpdate.setFirstName(updatedUser.getFirstName());
        userToUpdate.setLastName(updatedUser.getLastName());
        userToUpdate.setCompany(updatedUser.getCompany());
        userToUpdate.setCity(updatedUser.getCity());
        userToUpdate.setCountry(updatedUser.getCountry());
        userToUpdate.setPhoneNumber(updatedUser.getPhoneNumber());
        userToUpdate.setProgrammingLanguages(updatedUser.getProgrammingLanguages());
        userToUpdate.setExperience(updatedUser.getExperience());
        userToUpdate.setBio(updatedUser.getBio());
        userToUpdate.setPublic(isPublic); // Update visibility setting

        userRepository.save(userToUpdate);
        session.setAttribute("currentUser", userToUpdate);

        model.addAttribute("message", "Profile updated successfully");
        model.addAttribute("user", userToUpdate);
        return "profile";
    }
}
