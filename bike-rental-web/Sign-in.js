document.getElementById("signupForm").addEventListener("submit", async function(event) {
    event.preventDefault(); // Prevent default form submission

    // Get form values
    const fullName = document.getElementById("fullname").value.trim();
    const username = document.getElementById("username").value.trim();
    const email = document.getElementById("email").value.trim();
    const phone = document.getElementById("phone").value.trim();
    const password = document.getElementById("psw").value;
    const confirmPassword = document.getElementById("psw-repeat").value;

    // Validation
    if (!validateEmail(email)) {
        alert("Please enter a valid email address.");
        return;
    }

    if (!validatePhone(phone)) {
        alert("Please enter a valid phone number.");
        return;
    }

    if (password !== confirmPassword) {
        alert("Passwords do not match.");
        return;
    }

    if (password.length < 6) {
        alert("Password must be at least 6 characters long.");
        return;
    }

    // Data to send
    const data = {
        fullName: fullName,
        username: username,
        email: email,
        phoneNumber: phone,
        password: password
    };

    try {
        const response = await fetch("localhost:8080/api/register", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
        });

        if (response.ok) {
            alert("Account created successfully!");
            document.getElementById("signupForm").reset(); // Clear the form
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