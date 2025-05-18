document.addEventListener('DOMContentLoaded', async () => {
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