public abstract class AbstractBoundedQueue<T> implements BoundedQueue<T> {
	
	protected int size, capacity;

	public int capacity() {
		return capacity;
	}

	public int size() {
		return size;
	}
}