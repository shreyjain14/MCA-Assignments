document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('registrationForm');
    
    form.addEventListener('submit', function(event) {
        event.preventDefault();
        if (validateForm()) {
            alert('Form submitted successfully!');
            form.reset();
        }
    });

    function validateForm() {
        let isValid = true;

        // Validate Name
        const name = document.getElementById('name');
        if (name.value.trim() === '') {
            setError(name, 'Name is required');
            isValid = false;
        } else {
            setSuccess(name);
        }

        // Validate Email
        const email = document.getElementById('email');
        if (email.value.trim() === '') {
            setError(email, 'Email is required');
            isValid = false;
        } else if (!isValidEmail(email.value)) {
            setError(email, 'Provide a valid email address');
            isValid = false;
        } else {
            setSuccess(email);
        }

        // Validate Course
        const course = document.getElementById('course');
        if (course.value === '') {
            setError(course, 'Please select a course');
            isValid = false;
        } else {
            setSuccess(course);
        }

        // Validate Phone
        const phone = document.getElementById('phone');
        if (phone.value.trim() === '') {
            setError(phone, 'Phone number is required');
            isValid = false;
        } else if (!isValidPhone(phone.value)) {
            setError(phone, 'Provide a valid phone number');
            isValid = false;
        } else {
            setSuccess(phone);
        }

        return isValid;
    }

    function setError(element, message) {
        const inputControl = element.parentElement;
        const errorDisplay = inputControl.querySelector('.error-message');

        if (!errorDisplay) {
            const errorElement = document.createElement('div');
            errorElement.className = 'error-message text-red-500 text-sm mt-1';
            errorElement.innerText = message;
            inputControl.appendChild(errorElement);
        } else {
            errorDisplay.innerText = message;
        }

        element.classList.add('border-red-500');
        element.classList.remove('border-green-500');
    }

    function setSuccess(element) {
        const inputControl = element.parentElement;
        const errorDisplay = inputControl.querySelector('.error-message');

        if (errorDisplay) {
            errorDisplay.remove();
        }

        element.classList.add('border-green-500');
        element.classList.remove('border-red-500');
    }

    function isValidEmail(email) {
        const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        return re.test(String(email).toLowerCase());
    }

    function isValidPhone(phone) {
        const re = /^[\+]?[(]?[0-9]{3}[)]?[-\s\.]?[0-9]{3}[-\s\.]?[0-9]{4,6}$/im;
        return re.test(String(phone));
    }
});