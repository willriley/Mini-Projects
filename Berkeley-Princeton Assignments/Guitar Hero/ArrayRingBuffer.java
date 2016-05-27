
public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
	
	private int first; // index for next dequeue or peek; also index of least recently inserted item
	private int last; // index for next enqueue; also index one beyond most recently inserted item
	private T[] rb; // array for storing buffer data

	public ArrayRingBuffer(int capacity) {
		rb = (T[])new Object[capacity];
		first = last = size = 0;
		this.capacity = capacity;
	}

	@Override
	public void enqueue(T x) { // adds item x to the end; 'wraps around' when needed
		if(isFull())
			throw new RuntimeException("Ring buffer overflow");
		
		rb[last] = x;
		last = (last+1) % capacity; // allows the elements to 'wrap around'
		size++;
	}

	@Override 
	public T dequeue() { // deletes and returns item from front
		if(isEmpty())
			throw new RuntimeException("Ring buffer underflow");

		T deleted = rb[first];
		first = (first+1) % capacity;
		size--;
		return deleted;
	}

	@Override
	public T peek() { // returns (but doesn't delete) item from front
		if(isEmpty())
			throw new RuntimeException("Ring buffer underflow");

		return rb[first];
	}

	
	/* 

	Testing testing 1,2,3...

	public static void main(String[] args) {
		int N = Integer.parseInt(args[0]);
        ArrayRingBuffer<Double> buffer = new ArrayRingBuffer<>(N);
        for (Double d = 1.0; d <= N; d+=1.0) {
            buffer.enqueue(d);
        }
        Double t = buffer.dequeue();
        buffer.enqueue(t);
        System.out.println("Size after wrap-around is " + buffer.size());
        while (buffer.size() >= 2) {
            Double x = buffer.dequeue();
            Double y = buffer.dequeue();
            buffer.enqueue(x + y);
        }
        System.out.println(buffer.peek());
	}
	*/
}