// pastOrders.js
document.addEventListener('DOMContentLoaded', async () => {
  const rentalsTable = document.getElementById('rentalsTable');
  const tbody = rentalsTable.querySelector('tbody');

  try {
    const response = await fetch('/api/bikes/rentals');
    if (!response.ok) {
      throw new Error('Failed to fetch rentals');
    }
    const rentals = await response.json();
    tbody.innerHTML = '';
    rentals.forEach(rental => {
      const row = document.createElement('tr');
      row.innerHTML = `
        <td>${rental.orderId}</td>
        <td>${new Date(rental.rentalStart).toLocaleDateString()}</td>
        <td>${rental.bikeType}</td>
        <td>${rental.durationHours} hrs</td>
        <td>$${rental.totalPrice.toFixed(2)}</td>
        <td><span class="status ${rental.status.toLowerCase()}">${rental.status}</span></td>
        <td>
          ${rental.status === 'ongoing' ? `
            <button onclick="updateRental('${rental.orderId}', 'return')">Return</button>
            <button onclick="updateRental('${rental.orderId}', 'cancel')">Cancel</button>
          ` : rental.status === 'completed' ? `
            <button onclick="deleteRental('${rental.orderId}')">Delete</button>
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

async function updateRental(orderId, action) {
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