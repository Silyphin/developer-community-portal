document.addEventListener("DOMContentLoaded", function () {
    // Attach validation function to the form submit event
    document.querySelector("form").addEventListener("submit", function (event) {
        if (!validateRegisterForm()) {
            event.preventDefault(); // Prevent form submission if validation fails
            // Add Bootstrap's validation class to show styles
            event.target.classList.add('was-validated');
        }
    });

    // Add real-time validation on input change
    document.getElementById("firstName").addEventListener("input", validateRegisterForm);
    document.getElementById("lastName").addEventListener("input", validateRegisterForm);
    document.getElementById("email").addEventListener("input", validateRegisterForm);
    document.getElementById("password").addEventListener("input", validateRegisterForm);
    document.getElementById("confirmPassword").addEventListener("input", validateRegisterForm);
    document.getElementById("termsAccepted").addEventListener("change", validateRegisterForm);
});

function validateRegisterForm() {
    let isValid = true;

    // Get input fields
    let firstNameField = document.getElementById("firstName");
    let lastNameField = document.getElementById("lastName");
    let emailField = document.getElementById("email");
    let passwordField = document.getElementById("password");
    let confirmPasswordField = document.getElementById("confirmPassword");
    let termsAcceptedField = document.getElementById("termsAccepted");

    // Get error message containers
    let firstNameError = document.getElementById("firstNameError");
    let lastNameError = document.getElementById("lastNameError");
    let emailError = document.getElementById("emailError");
    let passwordError = document.getElementById("passwordError");
    let confirmPasswordError = document.getElementById("confirmPasswordError");
    let termsError = document.getElementById("termsError");

    // Clear previous error messages and reset validation classes
    firstNameError.textContent = "";
    lastNameError.textContent = "";
    emailError.textContent = "";
    passwordError.textContent = "";
    confirmPasswordError.textContent = "";
    termsError.textContent = "";

    firstNameField.classList.remove("is-invalid", "is-valid");
    lastNameField.classList.remove("is-invalid", "is-valid");
    emailField.classList.remove("is-invalid", "is-valid");
    passwordField.classList.remove("is-invalid", "is-valid");
    confirmPasswordField.classList.remove("is-invalid", "is-valid");

    // First Name Validation
    let firstName = firstNameField.value.trim();
    if (firstName === "") {
        firstNameError.textContent = "First Name is required.";
        firstNameField.classList.add("is-invalid");
        isValid = false;
    } else {
        firstNameField.classList.add("is-valid");
    }

    // Last Name Validation
    let lastName = lastNameField.value.trim();
    if (lastName === "") {
        lastNameError.textContent = "Last Name is required.";
        lastNameField.classList.add("is-invalid");
        isValid = false;
    } else {
        lastNameField.classList.add("is-valid");
    }

    // Email Validation
    let email = emailField.value.trim();
    let emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/; // Simple email regex
    if (email === "") {
        emailError.textContent = "Email is required.";
        emailField.classList.add("is-invalid");
        isValid = false;
    } else if (!email.includes("@")) {
        emailError.textContent = `Please include an '@' in the email address. '${email}' is missing an '@'.`;
        emailField.classList.add("is-invalid");
        isValid = false;
    } else if (!emailPattern.test(email)) {
        const domain = email.split("@")[1];
        if (domain.startsWith(".") || domain.endsWith(".")) {
            emailError.textContent = `'.' is used at a wrong position in '${domain}'.`;
        } else {
            emailError.textContent = "Invalid email format.";
        }
        emailField.classList.add("is-invalid");
        isValid = false;
    } else {
        // Additional check for a valid TLD
        const domain = email.split("@")[1];
        const domainParts = domain.split(".");
        if (domainParts.length < 2 || domainParts[domainParts.length - 1].length < 2) {
            emailError.textContent = `Please include a valid top-level domain (e.g., .com, .org) in '${email}'.`;
            emailField.classList.add("is-invalid");
            isValid = false;
        } else {
            emailField.classList.add("is-valid");
        }
    }

    // Password Validation
    let password = passwordField.value;
    if (password === "") {
        passwordError.textContent = "Password is required.";
        passwordField.classList.add("is-invalid");
        isValid = false;
    } else if (password.length < 6) {
        passwordError.textContent = "Password must be at least 6 characters.";
        passwordField.classList.add("is-invalid");
        isValid = false;
    } else {
        passwordField.classList.add("is-valid");
    }

    // Confirm Password Validation
    let confirmPassword = confirmPasswordField.value;
    if (confirmPassword === "") {
        confirmPasswordError.textContent = "Confirm Password is required.";
        confirmPasswordField.classList.add("is-invalid");
        isValid = false;
    } else if (password !== confirmPassword) {
        confirmPasswordError.textContent = "Passwords do not match.";
        confirmPasswordField.classList.add("is-invalid");
        isValid = false;
    } else if (password !== "" && password.length >= 6) {
        confirmPasswordField.classList.add("is-valid");
    }

    // Terms & Conditions Validation
    let termsAccepted = termsAcceptedField.checked;
    if (!termsAccepted) {
        termsError.textContent = "You must accept the terms and conditions.";
        isValid = false;
    }

    return isValid; // Only submit the form if all validations pass
}