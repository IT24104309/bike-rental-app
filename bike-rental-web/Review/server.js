const express = require('express');
const fs = require('fs').promises;
const path = require('path');
const app = express();
const port = 3000;

app.use(express.json());
app.use(express.static('public')); // Serve static files from 'public' directory

const reviewsFile = path.join(__dirname, 'reviews.txt');

// Get all reviews
app.get('/api/reviews', async (req, res) => {
  try {
    const data = await fs.readFile(reviewsFile, 'utf8');
    const reviews = data.trim().split('\n').map(line => JSON.parse(line));
    res.json(reviews);
  } catch (error) {
    if (error.code === 'ENOENT') {
      res.json([]);
    } else {
      res.status(500).send('Error reading reviews');
    }
  }
});

// Add a new review
app.post('/api/reviews', async (req, res) => {
  try {
    const review = req.body;
    await fs.appendFile(reviewsFile, JSON.stringify(review) + '\n');
    res.status(201).send('Review added');
  } catch (error) {
    res.status(500).send('Error saving review');
  }
});

// Update a review
app.put('/api/reviews/:id', async (req, res) => {
  try {
    const id = parseInt(req.params.id);
    const { review: newText } = req.body;
    const data = await fs.readFile(reviewsFile, 'utf8');
    const reviews = data.trim().split('\n').map(line => JSON.parse(line));
    const index = reviews.findIndex(r => r.id === id);

    if (index !== -1) {
      reviews[index].review = newText;
      await fs.writeFile(reviewsFile, reviews.map(r => JSON.stringify(r)).join('\n') + '\n');
      res.send('Review updated');
    } else {
      res.status(404).send('Review not found');
    }
  } catch (error) {
    res.status(500).send('Error updating review');
  }
});

// Delete a review
app.delete('/api/reviews/:id', async (req, res) => {
  try {
    const id = parseInt(req.params.id);
    const data = await fs.readFile(reviewsFile, 'utf8');
    const reviews = data.trim().split('\n').map(line => JSON.parse(line));
    const filteredReviews = reviews.filter(r => r.id !== id);

    if (filteredReviews.length < reviews.length) {
      await fs.writeFile(reviewsFile, filteredReviews.map(r => JSON.stringify(r)).join('\n') + '\n');
      res.send('Review deleted');
    } else {
      res.status(404).send('Review not found');
    }
  } catch (error) {
    res.status(500).send('Error deleting review');
  }
});

app.listen(port, () => {
  console.log(`Server running at http://localhost:${port}`);
});