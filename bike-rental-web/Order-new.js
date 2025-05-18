// Order-new.js
document.addEventListener('DOMContentLoaded', () => {
  const rentalData = JSON.parse(sessionStorage.getItem('rentalData'));
  if (!rentalData) {
    alert('No rental data found.');
    window.location.href = 'Order-form.html';
    return;
  }

  // Populate summary
  document.getElementById('renterName').textContent = rentalData.renterName;
  document.getElementById('username').textContent = rentalData.username;
  document.getElementById('bikeId').textContent = rentalData.bikeId;
  document.getElementById('bikeType').textContent = rentalData.bikeType;
  document.getElementById('hours').textContent = rentalData.hours;
  document.getElementById('hourlyRate').textContent = rentalData.hourlyRate.toFixed(2);
  const totalAmount = rentalData.hours * rentalData.hourlyRate * (rentalData.isPremiumUser ? 0.9 : 1);
  document.getElementById('totalAmount').textContent = totalAmount.toFixed(2);

  // Handle cancel
  document.getElementById('cancelBtn').addEventListener('click', () => {
    sessionStorage.removeItem('rentalData');
    window.location.href = 'Order-form.html';
  });

  // Handle confirm payment
  document.getElementById('confirmBtn').addEventListener('click', async () => {
    try {
      const response = await fetch('/api/bikes/rent', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: new URLSearchParams({
          renterName: rentalData.renterName,
          username: rentalData.username,
          bikeId: rentalData.bikeId,
          bikeType: rentalData.bikeType,
          hours: rentalData.hours,
          hourlyRate: rentalData.hourlyRate,
          isPremiumUser: rentalData.isPremiumUser
        })
      });
      if (response.ok) {
        sessionStorage.removeItem('rentalData');
        window.location.href = 'Past-order.html';
      } else {
        alert('Error confirming rental.');
      }
    } catch (error) {
      console.error('Error:', error);
      alert('Error confirming rental.');
    }
  });
});