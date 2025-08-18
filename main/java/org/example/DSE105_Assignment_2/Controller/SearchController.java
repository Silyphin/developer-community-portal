package org.example.DSE105_Assignment_2.Controller;

import org.example.DSE105_Assignment_2.Model.SearchForm;
import org.example.DSE105_Assignment_2.Model.User;
import org.example.DSE105_Assignment_2.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private UserRepository userRepository;

    /**
     * Displays the search form page.
     * Only accessible to logged-in users.
     */
    @GetMapping("/search")
    public String showSearchForm(Model model, HttpSession session) {
        // Redirect to login if user is not authenticated
        if (!AuthController.isLoggedIn(session)) {
            return "redirect:/login";
        }

        // Add an empty search form to the model for the view
        model.addAttribute("searchForm", new SearchForm());
        return "search";
    }

    /**
     * Processes the submitted search form.
     * Filters users based on search criteria and returns the result view.
     */
    @PostMapping("/search")
    public String processSearch(@ModelAttribute SearchForm searchForm, Model model, HttpSession session) {
        // Redirect to login if user is not authenticated
        if (!AuthController.isLoggedIn(session)) {
            return "redirect:/login";
        }

        // Ensure at least one search criterion is provided
        if (!searchForm.hasSearchCriteria()) {
            model.addAttribute("error", "Please provide at least one search criterion");
            return "search";
        }

        // Fetch all users from the database
        List<User> allUsers = userRepository.findAll();
        List<User> searchResults = new ArrayList<>();

        // Filter users that match the search criteria
        for (User user : allUsers) {
            if (matchesSearchCriteria(user, searchForm)) {
                searchResults.add(user);
            }
        }

        // Add results and form data back to the model
        model.addAttribute("searchResults", searchResults);
        model.addAttribute("searchForm", searchForm);

        return "searchResults";
    }

    /**
     * Checks if a user matches the search criteria provided in the form.
     * Ignores private profiles.
     */
    private boolean matchesSearchCriteria(User user, SearchForm searchForm) {
        // Only public profiles are searchable
        if (!user.isPublic()) {
            return false;
        }

        // Check each search field for a partial (case-insensitive) match
        if (searchForm.getFirstName() != null && !searchForm.getFirstName().isEmpty() &&
                !user.getFirstName().toLowerCase().contains(searchForm.getFirstName().toLowerCase())) {
            return false;
        }

        if (searchForm.getLastName() != null && !searchForm.getLastName().isEmpty() &&
                !user.getLastName().toLowerCase().contains(searchForm.getLastName().toLowerCase())) {
            return false;
        }

        if (searchForm.getCompany() != null && !searchForm.getCompany().isEmpty() &&
                (user.getCompany() == null || !user.getCompany().toLowerCase().contains(searchForm.getCompany().toLowerCase()))) {
            return false;
        }

        if (searchForm.getCity() != null && !searchForm.getCity().isEmpty() &&
                (user.getCity() == null || !user.getCity().toLowerCase().contains(searchForm.getCity().toLowerCase()))) {
            return false;
        }

        if (searchForm.getCountry() != null && !searchForm.getCountry().isEmpty() &&
                (user.getCountry() == null || !user.getCountry().toLowerCase().contains(searchForm.getCountry().toLowerCase()))) {
            return false;
        }

        if (searchForm.getProgrammingLanguage() != null && !searchForm.getProgrammingLanguage().isEmpty() &&
                (user.getProgrammingLanguages() == null || !user.getProgrammingLanguages().toLowerCase().contains(searchForm.getProgrammingLanguage().toLowerCase()))) {
            return false;
        }

        // If all criteria matched (or were empty), return true
        return true;
    }
}
