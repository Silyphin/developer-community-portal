// TinyMCE Initialization - FREE VERSION
tinymce.init({
    selector: '#emailContent',
    height: 400,
    plugins: [
        'anchor', 'autolink', 'charmap', 'emoticons', 'link', 'lists', 'searchreplace', 'table', 'visualblocks', 'wordcount'
    ],
    toolbar: 'undo redo | blocks fontsize | bold italic underline | link table | numlist bullist | emoticons charmap | removeformat',
    content_style: 'body { font-family: Arial, sans-serif; font-size: 14px }',
    branding: false
});

// Email Validation Function
function validateEmails() {
    const emailTextarea = document.getElementById('emailAddresses');
    const emailError = document.getElementById('emailError');
    const emailLines = emailTextarea.value.split('\n').filter(line => line.trim() !== '');
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

    let allValid = true;
    emailLines.forEach(email => {
        if (!emailRegex.test(email.trim())) {
            allValid = false;
        }
    });

    if (!allValid) {
        emailTextarea.classList.add('is-invalid');
        emailError.style.display = 'block';
        return false;
    }

    emailTextarea.classList.remove('is-invalid');
    emailError.style.display = 'none';
    return true;
}