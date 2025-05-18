document.addEventListener('DOMContentLoaded', async () => {
  const rentalsTable = document.getElementById('rentalsTable');

  try {
    const response = await fetch('/api/bikes/rentals');
    const rentals = await response.json();
    rentalsTable.innerHTML = '';
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
      rentalsTable.appendChild(row);
    });
  } catch (error) {
    console.error('Error fetching rentals:', error);
    rentalsTable.innerHTML = '<tr><td colspan="7">Error loading rentals.</td></tr>';
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
}document.addEventListener('DOMContentLoaded', async () => {
  const tbody = document.querySelector('.order-table tbody');

  try {
    const response = await fetch('http://localhost:8080/api/bikes/rentals');
    if (response.ok) {
      const transactions = await response.json();
      tbody.innerHTML = '';
      transactions.forEach(t => {
        const row = document.createElement('tr');
        row.innerHTML = `
          <td>${t.orderId}</td>
          <td>${t.rentalStart.split('T')[0]}</td>
          <td>${t.bikeType}</td>
          <td>${t.durationHours} hrs</td>
          <td>$${t.totalPrice.toFixed(2)}</td>
          <td><span class="status ${t.status.toLowerCase()}">${t.status}</span></td>
        `;
        if (t.status === 'completed') {
          const deleteBtn = document.createElement('button');
          deleteBtn.textContent = 'Delete';
          deleteBtn.className = 'delete-btn';
          deleteBtn.onclick = async () => {
            try {
              const deleteResponse = await fetch(`http://localhost:8080/api/bikes/delete/${t.orderId}`, {
                method: 'DELETE'
              });
              if (deleteResponse.ok) {
                alert('Rental deleted.');
                row.remove();
              } else {
                alert('Error deleting rental.');
              }
            } catch (error) {
              console.error('Error:', error);
              alert('Network error. Please try again.');
            }
          };
          row.appendChild(deleteBtn);
        }
        tbody.appendChild(row);
      });
    } else {
      alert('Error fetching rentals.');
    }
  } catch (error) {
    console.error('Error:', error);
    alert('Network error. Please try again.');
  }
});