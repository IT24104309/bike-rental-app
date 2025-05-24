// select-role.js

document.addEventListener("DOMContentLoaded", function () {
    const userId = localStorage.getItem("userId");
    if (!userId) {
        alert("User ID not found. Please register or log in first.");
        window.location.href = "Login-Form.html";
        return;
    }

    const apiUrl = "http://localhost:8080/set-role";

    document.getElementById("btnRenter").addEventListener("click", async () => {
        await updateRole(userId, "Renter", "bike-add.html");
    });

    document.getElementById("btnBorrower").addEventListener("click", async () => {
        await updateRole(userId, "Regular", "order-form.html");
    });

    document.getElementById("btnViewBikes").addEventListener("click", () => {
        window.location.href = "Bike-manage.html";
    });
    document.getElementById("btnPastOrders").addEventListener("click", () => {
        window.location.href = "Past-order.html";
    });

    async function updateRole(userId, userType, redirectPage) {
        try {
            const response = await fetch(apiUrl, {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded",
                },
                body: `userId=${encodeURIComponent(userId)}&userType=${encodeURIComponent(userType)}`,
            });

            if (response.ok) {
                window.location.href = redirectPage;
            } else {
                alert("Failed to update user role.");
            }
        } catch (error) {
            console.error("Error setting role:", error);
            alert("Error connecting to server.");
        }
    }
});
