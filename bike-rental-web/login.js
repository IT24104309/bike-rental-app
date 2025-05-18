// login.js

document.addEventListener('DOMContentLoaded', function () {
    const form = document.querySelector('form');

    form.addEventListener('submit', function (e) {
        const username = form.querySelector('input[name="username"]').value.trim();
        const password = form.querySelector('input[name="password"]').value.trim();

        if (!username || !password) {
            e.preventDefault(); // Prevent form submission
            alert('Both username and password are required.');
        }
    });
});
