document.addEventListener("DOMContentLoaded", function () {
    // Attach validation function to the form submit event
    document.querySelector("form").addEventListener("submit", function (event) {
        if (!validateLoginForm()) {
            event.preventDefault(); // Prevent form submission if validation fails
            // Add Bootstrap's validation class to show styles
            event.target.classList.add('was-validated');
        }
    });

    // Add real-time validation on input change
    document.getElementById("email").addEventListener("input", validateLoginForm);
    document.getElementById("password").addEventListener("input", validateLoginForm);
});

function validateLoginForm() {
    let isValid = true;

    // Get input fields
    let emailField = document.getElementById("email");
    let passwordField = document.getElementById("password");

    // Get error message containers
    let emailError = document.getElementById("emailError");
    let passwordError = document.getElementById("passwordError");

    // Clear previous error messages and reset validation classes
    emailError.textContent = "";
    passwordError.textContent = "";
    emailField.classList.remove("is-invalid", "is-valid");
    passwordField.classList.remove("is-invalid", "is-valid");

    // Email validation
    let emailValue = emailField.value.trim();
    let emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/; // Simple email regex

    if (emailValue === "") {
        emailError.textContent = "Email is required.";
        emailField.classList.add("is-invalid");
        isValid = false;
    } else if (!emailValue.includes("@")) {
        emailError.textContent = `Please include an '@' in the email address. '${emailValue}' is missing an '@'.`;
        emailField.classList.add("is-invalid");
        isValid = false;
    } else if (!emailPattern.test(emailValue)) {
        const domain = emailValue.split("@")[1];
        if (domain.startsWith(".") || domain.endsWith(".")) {
            emailError.textContent = `'.' is used at a wrong position in '${domain}'.`;
        } else {
            emailError.textContent = "Invalid email format.";
        }
        emailField.classList.add("is-invalid");
        isValid = false;
    } else {
        // Additional check for a valid TLD
        const domain = emailValue.split("@")[1];
        const domainParts = domain.split(".");
        if (domainParts.length < 2 || domainParts[domainParts.length - 1].length < 2) {
            emailError.textContent = `Please include a valid top-level domain (e.g., .com, .org) in '${emailValue}'.`;
            emailField.classList.add("is-invalid");
            isValid = false;
        }
    }

    // Add is-valid class if email is valid
    if (isValid && emailValue !== "") {
        emailField.classList.add("is-valid");
    }

    // Password validation
    let passwordValue = passwordField.value.trim();

    if (passwordValue === "") {
        passwordError.textContent = "Password is required.";
        passwordField.classList.add("is-invalid");
        isValid = false;
    } else if (passwordValue.length < 6) {
        passwordError.textContent = "Password must be at least 6 characters long.";
        passwordField.classList.add("is-invalid");
        isValid = false;
    } else {
        passwordField.classList.add("is-valid");
    }

    return isValid; // Only submit the form if all validations pass
}