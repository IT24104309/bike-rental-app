const apiBase = "http://localhost:8080/api/admin-user-management";
const tableBody = document.getElementById("adminTableBody");
let selectedRow = null;
let selectedAdminId = null;

// Fetch and display admins
async function loadAdmins() {
    const res = await fetch(apiBase);
    const admins = await res.json();
    tableBody.innerHTML = "";

    admins.forEach(admin => {
        const row = document.createElement("tr");
        row.dataset.id = admin.id;

        row.innerHTML = `
      <td>${admin.id}</td>
      <td>${admin.name}</td>
      <td>${admin.role}</td>
      <td>${admin.email}</td>
      <td><span class="status ${admin.status.toLowerCase()}">${admin.status}</span></td>
      <td>
        <button class="action-btn edit-btn">Edit</button>
        <button class="action-btn delete-btn">Delete</button>
      </td>
    `;

        tableBody.appendChild(row);

        // DELETE
        row.querySelector(".delete-btn").addEventListener("click", () => {
            selectedRow = row;
            selectedAdminId = admin.id;
            document.getElementById("deleteModal").classList.remove("hidden");
        });

        // EDIT
        row.querySelector(".edit-btn").addEventListener("click", () => {
            selectedAdminId = admin.id;
            document.getElementById("editId").value = admin.id;
            document.getElementById("editName").value = admin.name;
            document.getElementById("editEmail").value = admin.email;
            document.getElementById("editRole").value = admin.role;
            document.getElementById("editStatus").value = admin.status;
            document.getElementById("editModal").classList.remove("hidden");
        });
    });
}
// OPEN Add Modal
document.querySelector(".add-admin").addEventListener("click", () => {
    document.getElementById("addModal").classList.remove("hidden");
});

// CANCEL Add
document.getElementById("cancelAdd").addEventListener("click", () => {
    document.getElementById("addModal").classList.add("hidden");
});

document.getElementById("confirmAdd").addEventListener("click", async () => {
    const newAdmin = {
        id: document.getElementById("addId").value,
        name: document.getElementById("addName").value,
        email: document.getElementById("addEmail").value,
        role: document.getElementById("addRole").value.toUpperCase(),
        status: document.getElementById("addStatus").value.toUpperCase(),
        password: document.getElementById("addPassword").value,
    };

    // Send POST request to backend
    try {
        const response = await fetch("http://localhost:8080/api/admin-user-management", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(newAdmin)
        });

        if (response.ok) {
            // Close modal and refresh admin list
            document.getElementById("addModal").classList.add("hidden");
            loadAdmins();
        } else {
            alert("Failed to add admin: " + response.status);
        }
    } catch (err) {
        alert("Error connecting to backend: " + err);
    }

    // Clear form inputs
    document.getElementById("addId").value = "";
    document.getElementById("addName").value = "";
    document.getElementById("addEmail").value = "";
    document.getElementById("addRole").value = "";
    document.getElementById("addStatus").value = "ACTIVE";
    document.getElementById("addPassword").value = "";
    document.getElementById("addPasswordConfirm").value = "";
});



// DELETE action
document.getElementById("confirmDelete").addEventListener("click", async () => {
    if (selectedAdminId) {
        await fetch(`${apiBase}/${selectedAdminId}`, {
            method: "DELETE"
        });
        selectedRow.remove();
        selectedAdminId = null;
    }
    document.getElementById("deleteModal").classList.add("hidden");
});

// CANCEL DELETE
document.getElementById("cancelDelete").addEventListener("click", () => {
    document.getElementById("deleteModal").classList.add("hidden");
});

// CANCEL EDIT
document.getElementById("cancelEdit").addEventListener("click", () => {
    document.getElementById("editModal").classList.add("hidden");
});

// SAVE EDIT
document.getElementById("saveEdit").addEventListener("click", async () => {
    const updated = {
        id: document.getElementById("editId").value,
        name: document.getElementById("editName").value,
        email: document.getElementById("editEmail").value,
        role: document.getElementById("editRole").value.toUpperCase(),
        status: document.getElementById("editStatus").value.toUpperCase(),
        password: document.getElementById("editPassword").value,
    };

    await fetch(`${apiBase}/${updated.id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(updated)
    });

    document.getElementById("editModal").classList.add("hidden");
    loadAdmins(); // Refresh UI
});

// Initial load
window.onload = loadAdmins;
