package lk.sliit.bike_rental_api.models;

import java.util.ArrayList;
import java.util.List;

public class RentRequestQueue  {
    private int maxSize;
    private RentRequest[] queArray;
    private int front;
    private int rear;
    private int nItems;

    public RentRequestQueue(int size) {
        this.maxSize = size;
        this.queArray = new RentRequest[maxSize];
        this.front = 0;
        this.rear = -1;
        this.nItems = 0;
    }

    // Insert = Enqueue
    public void insert(RentRequest request) {
        if (isFull()) {
            System.out.println("Queue is full. Cannot insert new request.");
            return;
        }

        if (rear == maxSize - 1) {
            rear = -1; // wrap around
        }

        queArray[++rear] = request;
        nItems++;
    }

    // Remove = Dequeue
    public RentRequest remove() {
        if (isEmpty()) {
            System.out.println("Queue is empty. Cannot remove.");
            return null;
        }

        RentRequest temp = queArray[front++];
        if (front == maxSize) {
            front = 0; // wrap around
        }

        nItems--;
        return temp;
    }

    public RentRequest peekFront() {
        if (isEmpty()) return null;
        return queArray[front];
    }

    public boolean isEmpty() {
        return (nItems == 0);
    }

    public boolean isFull() {
        return (nItems == maxSize);
    }

    public int size() {
        return nItems;
    }

    public RentRequest get(int index) {
        if (index < 0 || index >= nItems) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }

        int actualIndex = (front + index) % maxSize;
        return queArray[actualIndex];
    }

    public void displayQueue() {
        System.out.println("Queue contents:");
        for (int i = 0; i < nItems; i++) {
            RentRequest req = get(i);
            System.out.println((i + 1) + ". " + req.getUserId() + " requested bike " + req.getBikeId());
        }
    }

    // to make it easier to save to files
    public static RentRequestQueue fromList(List<RentRequest> list, int maxSize) {
        RentRequestQueue queue = new RentRequestQueue(maxSize);
        for (RentRequest request : list) {
            queue.insert(request);
        }
        return queue;
    }

    public List<RentRequest> toList() {
        List<RentRequest> list = new ArrayList<>();
        for (int i = 0; i < nItems; i++) {
            list.add(get(i)); // reuse your get(i) logic
        }
        return list;
    }


}
