document.addEventListener('DOMContentLoaded', function() {
    const form = document.querySelector('form');

    form.addEventListener('submit', function(e) {
        e.preventDefault();

        // Get form values
        const bikeData = {
            model: document.getElementById('bikemodel').value.trim(),
            registrationNumber: document.getElementById('registrationnum').value.trim(),
            type: document.getElementById('biketype').value.trim(),
            hourlyRate: document.getElementById('hourlyrate').value.trim()
        };

        // Validate inputs
        if (!validateForm(bikeData)) {
            return;
        }

        // Submit to backend
        submitBikeData(bikeData);
    });

    // Add input field focus effects
    addInputEffects();
});

function validateForm(data) {
    // Check for empty fields
    if (!data.model || !data.registrationNumber || !data.type || !data.hourlyRate) {
        alert('Please fill in all fields');
        return false;
    }

    // Validate hourly rate is a number
    if (isNaN(data.hourlyRate) || parseFloat(data.hourlyRate) <= 0) {
        alert('Please enter a valid hourly rate (must be a positive number)');
        return false;
    }

    return true;
}

function submitBikeData(bikeData) {
    // Convert hourly rate to number
    bikeData.hourlyRate = parseFloat(bikeData.hourlyRate);

    // 🔄 Backend API request
    fetch('http://localhost:8080/api/bikes', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(bikeData)
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(msg => { throw new Error(msg); });
            }
            return response.json();
        })
        .then(createdBike => {
            alert(`Bike ${createdBike.model} (${createdBike.registrationNumber}) added successfully!`);
            form.reset(); // Reset the form
        })
        .catch(err => {
            alert(err.message || 'Failed to add bike. Please try again.');
            console.error('Error:', err);
        });
}

function addInputEffects() {
    const inputs = document.querySelectorAll('input');

    inputs.forEach(input => {
        input.addEventListener('focus', function() {
            this.style.borderColor = '#4285f4';
            this.style.boxShadow = '0 0 5px rgba(66, 133, 244, 0.5)';
        });

        input.addEventListener('blur', function() {
            this.style.borderColor = '#ccc';
            this.style.boxShadow = 'none';
        });
    });
}

// Handle cancel button
document.querySelector('.cancel-btn').addEventListener('click', function() {
    if (confirm('Are you sure you want to cancel? All unsaved changes will be lost.')) {
        form.reset();
    }
});