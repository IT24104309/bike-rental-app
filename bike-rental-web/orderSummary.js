document.addEventListener('DOMContentLoaded', async () => {
  const urlParams = new URLSearchParams(window.location.search);
  const orderId = urlParams.get('orderId');
  const summaryBox = document.querySelector('.summary-box');
  const costSummary = document.querySelector('.cost-summary');
  const confirmBtn = document.querySelector('.confirm-btn');
  const cancelBtn = document.querySelector('.cancel-btn');

  if (!orderId) {
    alert('No order ID provided.');
    window.location.href = 'index.html';
    return;
  }

  try {
    const response = await fetch('http://localhost:8080/api/bikes/rentals');
    if (response.ok) {
      const transactions = await response.json();
      const transaction = transactions.find(t => t.orderId === orderId);
      if (transaction) {
        summaryBox.innerHTML = `
          <div class="summary-item"><strong>Renter Name:</strong> ${transaction.renterName}</div>
          <div class="summary-item"><strong>Username:</strong> ${transaction.username}</div>
          <div class="summary-item"><strong>Bike ID:</strong> ${transaction.bikeId}</div>
          <div class="summary-item"><strong>Bike Type:</strong> ${transaction.bikeType}</div>
        `;
        costSummary.innerHTML = `
          <div class="summary-item"><strong>Number of Hours:</strong> ${transaction.durationHours}</div>
          <div class="summary-item"><strong>Hourly Rate:</strong> $${transaction.hourlyRate.toFixed(2)}</div>
          <div class="summary-item"><strong>Total Amount:</strong> $${transaction.totalPrice.toFixed(2)}</div>
        `;
      } else {
        alert('Transaction not found.');
        window.location.href = 'index.html';
      }
    } else {
      alert('Error fetching transaction.');
    }
  } catch (error) {
    console.error('Error:', error);
    alert('Network error. Please try again.');
  }

  // Confirm payment
  confirmBtn.addEventListener('click', async () => {
    try {
      const response = await fetch(`http://localhost:8080/api/bikes/return/${orderId}`, {
        method: 'PUT'
      });
      if (response.ok) {
        alert('Payment confirmed!');
        window.location.href = 'Past-order.html';
      } else {
        alert('Error confirming payment.');
      }
    } catch (error) {
      console.error('Error:', error);
      alert('Network error. Please try again.');
    }
  });

  // Cancel order
  cancelBtn.addEventListener('click', async () => {
    try {
      const response = await fetch(`http://localhost:8080/api/bikes/cancel/${orderId}`, {
        method: 'PUT'
      });
      if (response.ok) {
        alert('Order has been cancelled.');
        window.location.href = 'Past-order.html';
      } else {
        alert('Error cancelling order.');
      }
    } catch (error) {
      console.error('Error:', error);
      alert('Network error. Please try again.');
    }
  });
});