const fullNameInput = document.getElementById('fullName');
const emailInput = document.getElementById('email');
const passwordInput = document.getElementById('password');
const confirmPasswordInput = document.getElementById('confirmPassword');
const dateOfBirthInput = document.getElementById('dateOfBirth');
const submitButton = document.getElementById('submitButton');

const fullNameError = document.getElementById('fullNameError');
const emailError = document.getElementById('emailError');
const passwordError = document.getElementById('passwordError');
const confirmPasswordError = document.getElementById('confirmPasswordError');
const dateOfBirthError = document.getElementById('dateOfBirthError');

function validateFullName() {
    const fullName = fullNameInput.value.trim();
    const fullNameRegex = /^[a-zA-Z\s]{3,}$/;

    if (!fullNameRegex.test(fullName)) {
        fullNameError.textContent = 'Please enter a valid full name (at least 3 characters, only letters and spaces).';
        fullNameInput.classList.add('border-red-500');
        return false;
    } else {
        fullNameError.textContent = '';
        fullNameInput.classList.remove('border-red-500');
        return true;
    }
}

function validateEmail() {
    const email = emailInput.value.trim();
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    if (!emailRegex.test(email)) {
        emailError.textContent = 'Please enter a valid email address.';
        emailInput.classList.add('border-red-500');
        return false;
    } else {
        emailError.textContent = '';
        emailInput.classList.remove('border-red-500');
        return true;
    }
}

function validatePassword() {
    const password = passwordInput.value;
    const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/;

    if (!passwordRegex.test(password)) {
        passwordError.textContent = 'Password must be at least 8 characters long and contain both letters and numbers.';
        passwordInput.classList.add('border-red-500');
        return false;
    } else {
        passwordError.textContent = '';
        passwordInput.classList.remove('border-red-500');
        return true;
    }
}

function validateConfirmPassword() {
    const password = passwordInput.value;
    const confirmPassword = confirmPasswordInput.value;

    if (password !== confirmPassword) {
        confirmPasswordError.textContent = 'Passwords do not match.';
        confirmPasswordInput.classList.add('border-red-500');
        return false;
    } else {
        confirmPasswordError.textContent = '';
        confirmPasswordInput.classList.remove('border-red-500');
        return true;
    }
}

function validateDateOfBirth() {
    const dateOfBirth = dateOfBirthInput.value;
    const today = new Date();
    const birthDate = new Date(dateOfBirth);
    let age = today.getFullYear() - birthDate.getFullYear();
    const monthDiff = today.getMonth() - birthDate.getMonth();

    if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birthDate.getDate())) {
        age--;
    }

    if (age < 18) {
        dateOfBirthError.textContent = 'You must be at least 18 years old to register.';
        dateOfBirthInput.classList.add('border-red-500');
        submitButton.disabled = true;
        return false;
    } else {
        dateOfBirthError.textContent = '';
        dateOfBirthInput.classList.remove('border-red-500');
        submitButton.disabled = false;
        return true;
    }
}

// Event Listeners

fullNameInput.addEventListener('input', validateFullName);
emailInput.addEventListener('input', validateEmail);
passwordInput.addEventListener('input', validatePassword);
confirmPasswordInput.addEventListener('input', validateConfirmPassword);
dateOfBirthInput.addEventListener('input', validateDateOfBirth);

// Form Submission

document.getElementById('registrationForm').addEventListener('submit', function(event) {
    if (!validateFullName() || !validateEmail() || !validatePassword() || !validateConfirmPassword() || !validateDateOfBirth()) {
        event.preventDefault(); // Prevent form submission if any field is invalid
    }
});