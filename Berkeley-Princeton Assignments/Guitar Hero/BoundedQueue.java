public interface BoundedQueue<T> {
	int capacity();     // return max size of the buffer
	int size();    		// return number of items currently in the buffer
	void enqueue(T x);  // add item x to the end
	T dequeue();        // delete and return item from the front
	T peek();           // return (but do not delete) item from the front

	default boolean isEmpty() { // is the buffer empty (size equals zero)?
		return size()==0;
	}
	
	default boolean isFull() { // is the buffer full (size is same as capacity)?
		return size()==capacity();
	}        
}