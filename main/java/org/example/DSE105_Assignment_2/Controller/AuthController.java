package org.example.DSE105_Assignment_2.Controller;

import org.example.DSE105_Assignment_2.Model.LoginForm;
import org.example.DSE105_Assignment_2.Model.PasswordResetForm;
import org.example.DSE105_Assignment_2.Model.User;
import org.example.DSE105_Assignment_2.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    // Displays the login form page
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }

    // Processes login form submission and authenticates user
    @PostMapping("/login")
    public String processLogin(@ModelAttribute LoginForm loginForm, Model model, HttpSession session) {
        String email = loginForm.getEmail();
        String password = loginForm.getPassword();

        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent() && userOptional.get().getPassword().equals(password)) {
            User user = userOptional.get();
            session.setAttribute("currentUser", user);

            // Check if user is admin and redirect accordingly
            if ("admin".equals(user.getRole())) {
                return "redirect:/admin";
            } else {
                return "redirect:/";
            }
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }
    }

    // Handles user logout by invalidating the session
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    // Displays the forgot password form page
    @GetMapping("/forgotPassword")
    public String showForgotPasswordForm(Model model) {
        model.addAttribute("passwordResetForm", new PasswordResetForm());
        return "forgotPassword";
    }

    // Processes forgot password form submission
    @PostMapping("/forgotPassword")
    public String processForgotPassword(@ModelAttribute PasswordResetForm passwordResetForm, Model model) {
        String email = passwordResetForm.getEmail();

        if (userRepository.existsByEmail(email)) {
            model.addAttribute("message", "Password reset instructions have been sent to your email.");
            return "forgotPasswordConfirmation";
        } else {
            model.addAttribute("error", "Email not found");
            return "forgotPassword";
        }
    }

    // Retrieves the currently logged-in user from the session
    public static User getCurrentUser(HttpSession session) {
        return (User) session.getAttribute("currentUser");
    }

    // Checks if a user is currently logged in
    public static boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("currentUser") != null;
    }

    // Adds a new user to the repository
    public void addUser(User user) {
        userRepository.save(user);
    }

    // Retrieves a user by their email address
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    // Retrieves all users from the repository
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}