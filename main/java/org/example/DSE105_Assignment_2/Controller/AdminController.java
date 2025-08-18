package org.example.DSE105_Assignment_2.Controller;

import org.example.DSE105_Assignment_2.Model.User;
import org.example.DSE105_Assignment_2.Repository.UserRepository;
import org.example.DSE105_Assignment_2.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    // Verifies if the current session user has admin privileges
    private boolean isAdmin(HttpSession session) {
        User currentUser = AuthController.getCurrentUser(session);
        return currentUser != null && "admin".equals(currentUser.getRole());
    }

    // Displays the admin dashboard with user statistics
    @GetMapping("")
    public String adminDashboard(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!isAdmin(session)) {
            redirectAttributes.addFlashAttribute("error", "You do not have admin access");
            return "redirect:/login";
        }

        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        model.addAttribute("userCount", users.size());

        return "admin/adminDashBoard";
    }

    // Redirects from dashboard to the user management page
    @GetMapping("/adminUser")
    public String redirectToUsers(HttpSession session, RedirectAttributes redirectAttributes, Model model) {
        if (!isAdmin(session)) {
            redirectAttributes.addFlashAttribute("error", "You do not have admin access");
            return "redirect:/login";
        }

        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);

        return "admin/adminUser";
    }

    // Redirects from dashboard to the bulk email page
    @GetMapping("/adminBulkEmail")
    public String redirectToBulkEmail(HttpSession session, RedirectAttributes redirectAttributes) {
        if (!isAdmin(session)) {
            redirectAttributes.addFlashAttribute("error", "You do not have admin access");
            return "redirect:/login";
        }

        return "admin/adminBulkEmail";
    }

    // Shows the user management page listing all users
    @GetMapping("/users")
    public String manageUsers(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!isAdmin(session)) {
            redirectAttributes.addFlashAttribute("error", "You do not have admin access");
            return "redirect:/login";
        }

        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);

        return "admin/adminUser";
    }

    // Displays detailed information for a specific user
    @GetMapping("/users/{id}")
    public String viewUser(@PathVariable("id") Long userId, Model model, HttpSession session,
                           RedirectAttributes redirectAttributes) {
        if (!isAdmin(session)) {
            redirectAttributes.addFlashAttribute("error", "You do not have admin access");
            return "redirect:/login";
        }

        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "User not found");
            return "redirect:/admin/users";
        }

        model.addAttribute("user", user.get());
        return "admin/adminViewUser";
    }

    // Presents the form for editing a user's information
    @GetMapping("/users/{id}/edit")
    public String showEditUserForm(@PathVariable("id") Long userId, Model model, HttpSession session,
                                   RedirectAttributes redirectAttributes) {
        if (!isAdmin(session)) {
            redirectAttributes.addFlashAttribute("error", "You do not have admin access");
            return "redirect:/login";
        }

        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "User not found");
            return "redirect:/admin/users";
        }

        model.addAttribute("user", user.get());
        return "admin/adminEditUser";
    }

    // Handles the submission of the user edit form and updates user data
    @PostMapping("/users/{id}/edit")
    public String updateUser(@PathVariable("id") Long userId, @ModelAttribute User updatedUser,
                             HttpSession session, RedirectAttributes redirectAttributes) {
        if (!isAdmin(session)) {
            redirectAttributes.addFlashAttribute("error", "You do not have admin access");
            return "redirect:/login";
        }

        Optional<User> existingUserOpt = userRepository.findById(userId);
        if (existingUserOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "User not found");
            return "redirect:/admin/users";
        }

        User existingUser = existingUserOpt.get();

        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setCompany(updatedUser.getCompany());
        existingUser.setCity(updatedUser.getCity());
        existingUser.setCountry(updatedUser.getCountry());
        existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
        existingUser.setProgrammingLanguages(updatedUser.getProgrammingLanguages());
        existingUser.setExperience(updatedUser.getExperience());
        existingUser.setBio(updatedUser.getBio());
        existingUser.setRole(updatedUser.getRole());

        userRepository.save(existingUser);

        redirectAttributes.addFlashAttribute("message", "User updated successfully");
        return "redirect:/admin/users";
    }

    // Removes a user from the system
    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Long userId, HttpSession session,
                             RedirectAttributes redirectAttributes) {
        if (!isAdmin(session)) {
            redirectAttributes.addFlashAttribute("error", "You do not have admin access");
            return "redirect:/login";
        }

        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "User not found");
            return "redirect:/admin/users";
        }

        User currentUser = AuthController.getCurrentUser(session);
        if (user.get().getId().equals(currentUser.getId())) {
            redirectAttributes.addFlashAttribute("error", "You cannot delete your own admin account");
            return "redirect:/admin/users";
        }

        userRepository.deleteById(userId);
        redirectAttributes.addFlashAttribute("message", "User deleted successfully");
        return "redirect:/admin/users";
    }

    // Shows the form for sending bulk emails
    @GetMapping("/bulk-email")
    public String showBulkEmailForm(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!isAdmin(session)) {
            redirectAttributes.addFlashAttribute("error", "You do not have admin access");
            return "redirect:/login";
        }

        return "admin/bulkEmail";
    }

    // Processes bulk email sending initiated from the navbar
    @PostMapping("/bulk-email/send")
    public String sendBulkEmailFromNavbar(@RequestParam("emailAddresses") String emailAddresses,
                                          @RequestParam("emailSubject") String subject,
                                          @RequestParam("emailContent") String content,
                                          @RequestParam("emailFrom") String emailFrom,
                                          HttpSession session, RedirectAttributes redirectAttributes) {
        if (!isAdmin(session)) {
            redirectAttributes.addFlashAttribute("error", "You do not have admin access");
            return "redirect:/login";
        }

        try {
            // Split the email addresses by newline and trim whitespace
            String[] recipients = emailAddresses.split("\n");
            for (int i = 0; i < recipients.length; i++) {
                recipients[i] = recipients[i].trim();
            }

            // Send the email using the EmailService
            emailService.sendBulkEmail(recipients, subject, content, emailFrom);
            redirectAttributes.addFlashAttribute("message", "Bulk email sending started. Emails will be sent in the background.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to start bulk email sending: " + e.getMessage());
        }

        return "redirect:/admin/bulk-email";
    }

    // Processes bulk email sending initiated from the dashboard
    @PostMapping("/send-bulk-email")
    public String sendBulkEmail(@RequestParam("emailAddresses") String emailAddresses,
                                @RequestParam("emailSubject") String subject,
                                @RequestParam("emailContent") String content,
                                @RequestParam("emailFrom") String emailFrom,
                                HttpSession session, RedirectAttributes redirectAttributes) {
        if (!isAdmin(session)) {
            redirectAttributes.addFlashAttribute("error", "You do not have admin access");
            return "redirect:/login";
        }

        try {
            // Split the email addresses by newline and trim whitespace
            String[] recipients = emailAddresses.split("\n");
            for (int i = 0; i < recipients.length; i++) {
                recipients[i] = recipients[i].trim();
            }

            // Send the email using the EmailService
            emailService.sendBulkEmail(recipients, subject, content, emailFrom);
            redirectAttributes.addFlashAttribute("message", "Bulk email sending started. Emails will be sent in the background.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to start bulk email sending: " + e.getMessage());
        }

        return "redirect:/admin/adminBulkEmail";
    }
}