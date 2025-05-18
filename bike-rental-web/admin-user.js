// admin-user.js

document.addEventListener('DOMContentLoaded', function () {
    // Optional: Confirm before deleting a user
    const deleteForms = document.querySelectorAll('form[method="post"][action="/delete"]');

    deleteForms.forEach(form => {
        form.addEventListener('submit', function (e) {
            const confirmed = confirm("Are you sure you want to delete this user?");
            if (!confirmed) {
                e.preventDefault();
            }
        });
    });

    // Optional: Validate update form
    const updateForm = document.querySelector('form[th\\:action="@{/update}"]');
    if (updateForm) {
        updateForm.addEventListener('submit', function (e) {
            const email = updateForm.querySelector('input[type="email"]').value;
            const phone = updateForm.querySelector('input[name="phoneNumber"]').value;

            if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
                e.preventDefault();
                alert("Please enter a valid email.");
            }

            if (!/^\d{10,15}$/.test(phone)) {
                e.preventDefault();
                alert("Phone number must be 10 to 15 digits.");
            }
        });
    }
});
