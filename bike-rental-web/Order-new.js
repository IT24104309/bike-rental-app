// Order-new.js
document.addEventListener('DOMContentLoaded', () => {
    const rentalData = JSON.parse(sessionStorage.getItem('rentalData'));
    if (!rentalData) {
        alert('No rental data found.');
        window.location.href = 'Order-form.html';
        return;
    }

    // Populate summary
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

    document.getElementById('confirmBtn').addEventListener('click', async () => {
        try {
            const rentRequest = {
                userId: rentalData.userID,
                bikeId: rentalData.bikeId,
                bikeType: rentalData.bikeType,
                hours: rentalData.hours,
                hourlyRate: rentalData.hourlyRate,
                totalAmount: totalAmount,
            };

            const response = await fetch('http://localhost:8080/api/rental-management/request', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(rentRequest)
            });

            if (response.ok) {
                const result = await response.text(); // Or .json() if backend returns JSON
                sessionStorage.removeItem('rentalData');
                alert(result); // shows server message like "Bike assigned immediately"
                window.location.href = 'User-dashboard.html';
            } else {
                alert('Error confirming rental.');
            }
        } catch (error) {
            console.error('Error:', error);
            alert('Error confirming rental.');
        }
    });

});