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
document.getElementById('reviewForm').addEventListener('submit', function(e) {
  e.preventDefault();

  const name = document.getElementById('name').value;
  const rating = document.getElementById('rating').value;
  const review = document.getElementById('review').value;

  const reviewData = {
    name: name,
    rating: rating,
    review: review,
    timestamp: new Date().toISOString()
  };

  console.log('Review Submitted:', { name, rating, review });
});
