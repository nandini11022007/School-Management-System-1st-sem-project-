package School;

class StringQueue {
    String[] queue;
    int front, rear, capacity;

    public StringQueue(int capacity) {
        this.capacity = capacity;
        queue = new String[capacity];
        front = 0;
        rear = 0;
    }

    public void enqueue(String data) {
        if (rear == capacity) {
            System.out.println("Queue is full");
            return;
        }
        queue[rear++] = data;
    }

    public String dequeue() {
        if (front == rear) {
            System.out.println("Queue is empty");
            return null;
        }
        String value = queue[front++];
        return value;
    }

    public int size() {
        return rear - front;
    }

    public boolean isEmpty() {
        return front == rear;
    }

    public void display() {
        if (isEmpty()) {
            System.out.println("Queue is empty");
            return;
        }
        for (int i = front; i < rear; i++) {
            System.out.println(queue[i]);
        }
    }
}
