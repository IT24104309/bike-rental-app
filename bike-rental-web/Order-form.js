document.addEventListener('DOMContentLoaded', () => {
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
        const response = await fetch(`/api/bikes/available?bikeType=${encodeURIComponent(bikeType)}`);
        const bikes = await response.json();
        // Sort bikes by bikeId
        bikes.sort((a, b) => a.bikeId.localeCompare(b.bikeId));
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
  submitBtn.addEventListener('click', () => {
    const renterName = document.getElementById('renterName').value;
    const username = document.getElementById('username').value;
    const hours = parseInt(hoursInput.value);

    // Store data in sessionStorage to pass to Order-new.html
    sessionStorage.setItem('rentalData', JSON.stringify({
      renterName,
      username,
      bikeId: selectedBike.bikeId,
      bikeType: selectedBike.bikeType,
      hours,
      hourlyRate: selectedBike.hourlyRate,
      isPremiumUser: false // Assume non-premium for simplicity
    }));

    window.location.href = 'Order-new.html';
  });
});