document.addEventListener('DOMContentLoaded', async () => {
  const bikeTypeSelect = document.getElementById('bikeType');
  const hoursInput = document.getElementById('hours');
  const bikesGrid = document.getElementById('bikesGrid');
  const submitBtn = document.getElementById('submitBtn');
  let selectedBike = null;

  // Fetch and display bikes when bike type changes
  bikeTypeSelect.addEventListener('change', async () => {
    const bikeType = bikeTypeSelect.value;
    bikesGrid.innerHTML = '';
    selectedBike = null;
    submitBtn.disabled = true;

    if (bikeType) {
      try {
        const response = await fetch(`http://localhost:8080/api/bikes/available?bikeType=${encodeURIComponent(bikeType)}`);
        const bikes = await response.json();
        // Bikes are already sorted by bikeType server-side
        bikes.forEach(bike => {
          const bikeCard = document.createElement('div');
          bikeCard.className = 'bike-card';
          bikeCard.innerHTML = `
            <input type="radio" name="bike" value="${bike.bikeId}" data-type="${bike.bikeType}" data-rate="${bike.hourlyRate}">
            <div class="bike-detail"><strong>Bike ID:</strong> ${bike.bikeId}</div>
            <div class="bike-detail"><strong>Type:</strong> ${bike.bikeType}</div>
            <div class="bike-detail"><strong>Hourly Rate:</strong> $${bike.hourlyRate.toFixed(2)}</div>
          `;
          bikesGrid.appendChild(bikeCard);
        });

        // Handle bike selection
        bikesGrid.querySelectorAll('input[type="radio"]').forEach(radio => {
          radio.addEventListener('change', () => {
            selectedBike = {
              bikeId: radio.value,
              bikeType: radio.dataset.type,
              hourlyRate: parseFloat(radio.dataset.rate)
            };
            submitBtn.disabled = !hoursInput.value || hoursInput.value < 1;
          });
        });
      } catch (error) {
        console.error('Error fetching bikes:', error);
        bikesGrid.innerHTML = '<p>Error loading bikes.</p>';
      }
    }
  });

  // Enable submit button when hours are valid
  hoursInput.addEventListener('input', () => {
    submitBtn.disabled = !selectedBike || !hoursInput.value || hoursInput.value < 1;
  });

  // Handle form submission
  submitBtn.addEventListener('click', async () => {
    const hours = parseInt(hoursInput.value);

    try {

      const userID = sessionStorage.getItem('userId');
      const username = sessionStorage.getItem('username');

      // Store data in sessionStorage to pass to Order-new.html
      sessionStorage.setItem('rentalData', JSON.stringify({
        userID: userID,
        username: username,
        bikeId: selectedBike.bikeId,
        bikeType: selectedBike.bikeType,
        hours,
        hourlyRate: selectedBike.hourlyRate,
      }));

      window.location.href = 'Order-new.html';
    } catch (error) {
      console.error('Error fetching user data:', error);
      alert('Error retrieving user information. Please try again.');
    }
  });
});