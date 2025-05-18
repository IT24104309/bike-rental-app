// register.js

document.addEventListener('DOMContentLoaded', function () {
    const form = document.querySelector('form');

    form.addEventListener('submit', function (e) {
        const username = form.querySelector('input[name="username"]').value.trim();
        const password = form.querySelector('input[name="password"]').value.trim();
        const email = form.querySelector('input[name="email"]').value.trim();
        const phone = form.querySelector('input[name="phoneNumber"]').value.trim();
        const userType = form.querySelector('select[name="userType"]').value;

        if (!username || !password || !email || !phone || !userType) {
            e.preventDefault();
            alert("Please fill out all fields.");
        } else if (!validateEmail(email)) {
            e.preventDefault();
            alert("Please enter a valid email address.");
        } else if (!/^\d{10,15}$/.test(phone)) {
            e.preventDefault();
            alert("Please enter a valid phone number (10–15 digits).");
        }
    });

    function validateEmail(email) {
        // Simple email regex pattern
        const pattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return pattern.test(email);
    }
});
