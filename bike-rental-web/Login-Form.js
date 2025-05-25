document.getElementById("loginForm").addEventListener("submit", async function(event) {
    event.preventDefault(); // Prevent default form submission

    // Get user input
    const username = document.getElementById("username").value.trim();
    const password = document.getElementById("psw").value;

    // Basic validation
    if (!username || !password) {
        alert("Please enter both username and password.");
        return;
    }

    // Send data to backend
    const data = {
        username: username,
        password: password
    };

    try {
        const response = await fetch("http://localhost:8080/api/user-management/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
        });

        if (response.ok) {
            // Example: Redirect to dashboard
            const user = await response.json(); // Parse JSON response

            alert("Login successful!");
            sessionStorage.setItem("userId", user.userID);
            sessionStorage.setItem("username", user.username);
            window.location.href = "User-dashboard.html"; // or your protected route
        } else {
            const error = await response.json();
            alert("Login failed: " + (error.message || "Invalid credentials"));
        }
    } catch (err) {
        console.error("Login error:", err);
        alert("An error occurred. Please try again later.");
    }
});