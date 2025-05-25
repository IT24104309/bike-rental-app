// pastOrders.js
document.addEventListener('DOMContentLoaded', async () => {
  const tbody = document.getElementById('rentalsTable');

  try {
    const userId = sessionStorage.getItem('userId');
    const response = await fetch(`http://localhost:8080/api/rental-management/get-orders/${userId}`);
    if (!response.ok) {
      throw new Error('Failed to fetch rentals');
    }

    const rentals = await response.json();
    tbody.innerHTML = '';
    rentals.forEach(rental => {
      const row = document.createElement('tr');
      row.innerHTML = `
        <td>${rental.orderId}</td>
        <td>${new Date(rental.requestTime).toLocaleDateString()}</td>
        <td>${rental.bikeId}</td>
        <td>${rental.bikeType}</td>
        <td>${rental.hours} hrs</td>
        <td>$${rental.totalAmount.toFixed(2)}</td>
        <td><span class="status ${rental.status?.toLowerCase()}">${rental.status}</span></td>
        <td>
          ${rental.status === 'ONGOING' ? `
            <button onclick="releaseRental('${rental.bikeId}','${rental.orderId}')">Return</button>
          ` : rental.status === 'PROCESSING' ? `
            <button onclick="cancelRental('${rental.orderId}')">Cancel</button>
          ` : ''}
        </td>
      `;
      tbody.appendChild(row);
    });
  } catch (error) {
    console.error('Error fetching rentals:', error);
    tbody.innerHTML = '<tr><td colspan="7">Error loading rentals.</td></tr>';
  }
});

async function releaseRental(bikeId, orderId) {
  fetch(`http://localhost:8080/api/rental-management/release?bikeId=${encodeURIComponent(bikeId)}&orderId=${encodeURIComponent(orderId)}`, {
    method: "POST"
  })
      .then(response => {
        if (!response.ok) {
          throw new Error("Network response was not ok");
        }
        return response.text();
      })
      .then(result => {
        console.log("Server response:", result);
        alert("Release result: " + result);
      })
      .catch(error => {
        console.error("Error:", error);
        alert("Failed to release bike.");
      });

}

async function cancelRental(orderId, action) {
  try {
    const endpoint = action === 'return' ? `/api/bikes/return/${orderId}` : `/api/bikes/cancel/${orderId}`;
    const response = await fetch(endpoint, { method: 'PUT' });
    if (response.ok) {
      window.location.reload();
    } else {
      alert('Error updating rental.');
    }
  } catch (error) {
    console.error('Error:', error);
    alert('Error updating rental.');
  }
}

async function deleteRental(orderId) {
  try {
    const response = await fetch(`/api/bikes/delete/${orderId}`, { method: 'DELETE' });
    if (response.ok) {
      window.location.reload();
    } else {
      alert('Error deleting rental.');
    }
  } catch (error) {
    console.error('Error:', error);
    alert('Error deleting rental.');
  }
}