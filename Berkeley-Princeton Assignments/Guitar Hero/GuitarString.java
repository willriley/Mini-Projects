public class GuitarString { 
    private static final int SR = 44100;      // sampling Rate
    private static final double DECAY = .996; // energy decay factor
    private BoundedQueue<Double> buffer; // buffer for storing sound data

    // creates guitar string for a given freq
    // and initializes it to represent a string 
    // at rest by enqueueing the buffer with all zeros
    public GuitarString(double frequency) { 
        buffer = new ArrayRingBuffer<Double>((int)Math.round(SR/frequency));

        while (!buffer.isFull())
            buffer.enqueue(0.0);
    }

    /* Dequeues everything in the buffer, replacing it 
     * with random numbers between -0.5 and 0.5 
     *
     * This simulates the 'plucking' of a guitar string
     * by replacing the buffer with white noise
     */
    public void pluck() {
        while (!buffer.isEmpty())
            buffer.dequeue();

        while (!buffer.isFull())
            buffer.enqueue(Math.random() - 0.5);
    }

    /* Applies the Karplus-Strong algorithm by:
     * 1) deleting the sample at the front of the ring buffer and
     * 2) adding to the end of the ring buffer the avg of the 
     *    first two samples, multipled by the decay factor .996
     */
    public void tic() {
        double first = buffer.dequeue();
        buffer.enqueue((first + sample())* DECAY/2.0);
    }

    // returns the value of the item at the front of the ring buffer
    public double sample() {
        return buffer.peek();
    }
}
