document.addEventListener("DOMContentLoaded", function () {
    // Attach validation function to the form submit event
    document.querySelector("form").addEventListener("submit", function (event) {
        if (!validateForgotPasswordForm()) {
            event.preventDefault(); // Prevent form submission if validation fails
            // Add Bootstrap's validation class to show styles
            event.target.classList.add('was-validated');
        }
    });

    // Add real-time validation on input change
    document.getElementById("email").addEventListener("input", validateForgotPasswordForm);
});

function validateForgotPasswordForm() {
    let isValid = true;

    // Get input field
    let emailField = document.getElementById("email");

    // Get error message container
    let emailError = document.getElementById("emailError");

    // Clear previous error messages and reset validation classes
    emailError.textContent = "";
    emailField.classList.remove("is-invalid", "is-valid");

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
        } else {
            emailField.classList.add("is-valid");
        }
    }

    return isValid; // Only submit the form if all validations pass
}