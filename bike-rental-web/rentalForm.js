document.addEventListener('DOMContentLoaded', () => {
  const bikeTypeSelect = document.getElementById('bike-type');
  const hoursInput = document.getElementById('hours');
  const bikeCards = document.querySelectorAll('.bike-card');
  const confirmBtn = document.querySelector('.confirm-btn');
  const cancelBtn = document.querySelector('.cancel-btn');

  // Bike model mappings based on type
  const bikeModels = {
    standard: { RS101: 'Trek Roadster', RJ202: 'Giant Escape', CG303: 'Schwinn Cruiser', DL404: 'Cannondale Quick', EW505: 'Raleigh Classic' },
    electric: { RS101: 'Super73-Z1', RJ202: 'RadRunner', CG303: 'Aventon Pace', DL404: 'Lectric XP', EW505: 'Himiway Cruiser' },
    mountain: { RS101: 'Trek Marlin', RJ202: 'Giant Talon', CG303: 'Specialized Rockrider', DL404: 'Cannondale Trail', EW505: 'GT Aggressor' },
    folding: { RS101: 'Dahon VYBE', RJ202: 'Brompton M6L', CG303: 'Tern Link', DL404: 'Gocycle GX', EW505: 'Swagtron EB5' }
  };

  // Hourly rate mappings based on renter ID
  const hourlyRates = { RS101: 12, RJ202: 8, CG303: 10, DL404: 9, EW505: 11 };

  // Update bike models when bike type changes
  bikeTypeSelect.addEventListener('change', () => {
    const selectedType = bikeTypeSelect.value;
    bikeCards.forEach(card => {
      const renterId = card.querySelector('.bike-detail strong').textContent.split(': ')[1];
      const modelElement = card.querySelectorAll('.bike-detail')[1];
      modelElement.innerHTML = `<strong>Model:</strong> ${bikeModels[selectedType][renterId] || 'Unknown'}`;
    });
  });

  // Confirm rental
  confirmBtn.addEventListener('click', async () => {
    const bikeType = bikeTypeSelect.value;
    const hours = parseInt(hoursInput.value);
    const selectedBike = document.querySelector('input[name="bike"]:checked');
    
    if (!hours || hours < 1) {
      alert('Please enter a valid number of hours.');
      return;
    }
    if (!selectedBike) {
      alert('Please select a bike.');
      return;
    }

    const bikeId = selectedBike.value;
    const renterId = selectedBike.parentElement.querySelector('.bike-detail strong').textContent.split(': ')[1];
    const hourlyRate = hourlyRates[renterId];
    const isPremiumUser = false; // Hardcoded for simplicity

    const transaction = {
      renterName: 'John Doe',
      username: 'johndoe123',
      bikeId,
      bikeType: bikeType.charAt(0).toUpperCase() + bikeType.slice(1) + ' Bike',
      hours,
      hourlyRate,
      isPremiumUser
    };

    try {
      const response = await fetch('http://localhost:8080/api/bikes/rent', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: new URLSearchParams(transaction).toString()
      });
      if (response.ok) {
        const data = await response.json();
        alert('Bike rental confirmed!');
        window.location.href = `order_summary.html?orderId=${data.orderId}`;
      } else {
        alert('Error renting bike. Please try again.');
      }
    } catch (error) {
      console.error('Error:', error);
      alert('Network error. Please try again.');
    }
  });

  // Cancel rental
  cancelBtn.addEventListener('click', () => {
    alert('Rental cancelled.');
    window.location.href = 'index.html';
  });
});