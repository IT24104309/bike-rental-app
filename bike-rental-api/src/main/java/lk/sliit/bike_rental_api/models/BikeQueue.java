package lk.sliit.bike_rental_api.models;

public class BikeQueue {

    private Bike bike;
    private User[] userQueue;
    private int first = 0, size = 0;

    public BikeQueue(Bike bike, int queueCapacity) {
        this.bike = bike;
        this.userQueue = new User[queueCapacity];
    }

    public Bike getBike() {
        return this.bike;
    }

    // Check which user is the first
    public User peekFirst() {
        if (size == 0) return null;
        return userQueue[first];
    }

    // Add a new user to the queue, if it isn't yet full
    public void enqueue(User user) {
        // Check if the queue is full
        if (size == userQueue.length) return;
        int last = (first+size) % userQueue.length;
        userQueue[last] = user;
        size++;
    }

    // Fetch a user and remove from the queue
    public User dequeue() {
        // Check if queue is empty
        if (size == 0)
            return null;
        User user = userQueue[first];
        first = (first + 1) % userQueue.length;
        size--;
        return user;
    }
}
