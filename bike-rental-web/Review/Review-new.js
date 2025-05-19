// Star rating interaction
  const stars = document.querySelectorAll('.star');
  const ratingInput = document.getElementById('rating');

  stars.forEach(star => {
    star.addEventListener('click', () => {
      const rating = star.getAttribute('data-value');
      ratingInput.value = rating;
      stars.forEach(s => s.classList.remove('checked'));
      for (let i = 0; i < rating; i++) {
        stars[i].classList.add('checked');
      }
    });
  });

  // Handle form submission
  document.getElementById('reviewForm').addEventListener('submit', async function(e) {
    e.preventDefault();

    const name = document.getElementById('name').value;
    const rating = document.getElementById('rating').value;
    const desc = document.getElementById('desc').value;

    const reviewData = {
      name: name,
      rating: rating,
      desc: desc,
    };

  try {
      const response = await fetch('localhost:8080/api/reviews', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(reviewData)
      });

      if (response.ok) {
        alert('Review submitted successfully!');
        window.location.href = 'Review-display.html';
      } else {
        alert('Error submitting review.');
      }
    } catch (error) {
      console.error('Error:', error);
      alert('Error submitting review.');
    }
  });