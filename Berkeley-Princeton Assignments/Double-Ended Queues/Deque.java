public interface Deque<Item> {

	// adds item to front of Deque
	void addFirst(Item i); 
	// adds item to back of Deque
	void addLast(Item i); 
	// returns true if Deque is empty
	boolean isEmpty(); 
	// returns # of items in Deque
	int size(); 
	// prints items from first to last, sep. by a space
	void printDeque(); 
	// removes & returns item at front of Deque. If it DNE, returns null
	Item removeFirst(); 
	// removes & returns item at back of Deque. If it DNE, returns null
	Item removeLast(); 
	// gets the item at the given index
	Item get(int index);

}