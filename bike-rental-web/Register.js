document.getElementById("signupForm").addEventListener("submit", async function(event) {
    event.preventDefault(); // Prevent default form submission

    // Get form values
    const username = document.getElementById("username").value.trim();
    const email = document.getElementById("email").value.trim();
    const phone = document.getElementById("phone").value.trim();
    const password = document.getElementById("password").value;
    const confirmPassword = document.getElementById("confirmPassword").value;


    // Validation
    if (!validateEmail(email)) {
        alert("Please enter a valid email address.");
        return;
    }

    if (!validatePhone(phone)) {
        alert("Please enter a valid phone number.");
        return;
    }

    // Data to send
    const data = {
        username: username,
        email: email,
        phoneNumber: phone,
        password: password
    };

    try {
        const response = await fetch("http://localhost:8080/api/user-management/register", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
        });

        if (response.ok) {
            const user = await response.json(); // Parse JSON response

            alert("Account created successfully!");
            sessionStorage.setItem("userId", user.userID);
            sessionStorage.setItem("username", user.username);
            window.location.href = "User-dashboard.html"; // ✅ Redirect after successful registration
            return;
        } else {
            const error = await response.json();
            alert("Signup failed: " + (error.message || "Server error"));
        }
    } catch (err) {
        console.error("Error submitting form:", err);
        alert("An error occurred. Please try again later.");
    }
});

function validateEmail(email) {
    const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return re.test(email.toLowerCase());
}

function validatePhone(phone) {
    const re = /^\d{10,15}$/;
    return re.test(phone);
}
function validateForm() {
    const password = document.getElementById("password").value;
    const confirmPassword = document.getElementById("confirmPassword").value;

    if (password !== confirmPassword) {
        alert("Passwords do not match!");
        return false;
    }
    return false; // prevent default form submission

}