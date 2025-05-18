const bikeApiBase = "http://localhost:8080/api/bikes";
const bikeTableBody = document.querySelector(".bike-table tbody");
let selectedBikeId = null;
let selectedBikeRow = null;

// Initialize on DOM load
document.addEventListener('DOMContentLoaded', function() {
    console.log('Bike Management Dashboard initialized');
    loadBikes();
    setupEventListeners();
});

// Load bikes from API
async function loadBikes() {
    try {
        console.log('Fetching bikes from API...');
        const response = await fetch(bikeApiBase);

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const bikes = await response.json();
        console.log(`Loaded ${bikes.length} bikes`);
        renderBikeTable(bikes);
    } catch (error) {
        console.error('Error loading bikes:', error);
        alert('Failed to load bikes: ' + error.message);
    }
}

// Render bikes in table
function renderBikeTable(bikes) {
    bikeTableBody.innerHTML = bikes.map(bike => `
        <tr data-id="${bike.id}">
            <td>${bike.id}</td>
            <td>${bike.type}</td>
            <td><span class="status ${bike.status.toLowerCase()}">${bike.status}</span></td>
            <td class="action-buttons">
                <a href="editBike.html?id=${bike.id}" class="btn edit-btn">Edit</a>
                <a href="#" class="btn delete-btn">Delete</a>
            </td>
        </tr>
    `).join('');
}

// Setup all event listeners
function setupEventListeners() {
    // Delete button handler (using event delegation)
    document.addEventListener('click', function(e) {
        if (e.target.classList.contains('delete-btn')) {
            e.preventDefault();
            const row = e.target.closest('tr');
            selectedBikeId = row.dataset.id;
            selectedBikeRow = row;

            if (confirm(`Are you sure you want to delete bike ${selectedBikeId}?`)) {
                deleteBike();
            }
        }
    });

    // Add Bike button
    document.querySelector('.add-btn')?.addEventListener('click', function(e) {
        e.preventDefault();
        window.location.href = "addBike.html";
    });
}

// Delete bike function
async function deleteBike() {
    if (!selectedBikeId) return;

    try {
        const response = await fetch(`${bikeApiBase}/${selectedBikeId}`, {
            method: "DELETE",
            headers: {
                // "Authorization": "Bearer " + localStorage.getItem("adminToken")
            }
        });

        if (!response.ok) {
            throw new Error(await response.text());
        }

        selectedBikeRow.remove();
        console.log(`Bike ${selectedBikeId} deleted successfully`);
    } catch (error) {
        console.error('Error deleting bike:', error);
        alert('Failed to delete bike: ' + error.message);
    }
}