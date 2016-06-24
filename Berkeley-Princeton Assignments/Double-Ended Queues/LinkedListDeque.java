/*
 * Generic doubly linked list
 * with a circular sentinel topology
 *
 * @author Will Riley on 6/24/16
*/

public class LinkedListDeque<Item> implements Deque<Item> {

	private class Node<Item> {
		public Item item;
		public Node<Item> prev;
		public Node<Item> next; 

		public Node(Item t, Node<Item> p, Node<Item> n) {
			item = t;
			prev = p;
			next = n;
		}
	}

	private Node<Item> sentinel;
	private int size;

	public LinkedListDeque() {
		sentinel = new Node<>(null, null, null);
		sentinel.prev = sentinel;
		sentinel.next = sentinel;
		size = 0;
	}

	@Override
	public void addFirst(Item x) {
		Node<Item> oldFirst = sentinel.next;
		Node<Item> newFirst = new Node<>(x, sentinel, oldFirst);

		sentinel.next = newFirst;
		oldFirst.prev = newFirst;
		size++;
	}


	@Override
	public void addLast(Item x) {
		Node<Item> oldLast = sentinel.prev;
		Node<Item> newLast = new Node<>(x, oldLast, sentinel);

		sentinel.prev = newLast;
		oldLast.next = newLast;
		size++;
	} 

	@Override
	public boolean isEmpty() {
		return size==0;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public void printDeque() {
		Node pointer = sentinel;
		while (pointer.next != sentinel) {
			System.out.print(pointer.next.item + " ");
			pointer = pointer.next;
		}
		System.out.println();
	}

	@Override
	public Item removeFirst() {
		if (isEmpty()) return null;

		Node<Item> deleted = sentinel.next;
		sentinel.next = deleted.next;
		deleted.next.prev = sentinel;
		size--;
		/* Don't need to set deleted to null
		 * b/c nothing points to it anymore
		 * and it's 'garbage collected' by Java */
		return deleted.item;
	}

	@Override
	public Item removeLast() {
		if (size == 0) return null;

		Node<Item> deleted = sentinel.prev;
		sentinel.prev = deleted.prev;
		deleted.prev.next = sentinel;
		size--;

		return deleted.item;
	} 

	@Override
	public Item get(int index) {
		if (size == 0 || index >= size || index < 0) 
			return null;
		else if (index == 0) 
			return sentinel.next.item;

		Node<Item> pointer;

		/* Here, I'm asking which half of the list the item's on.
		 * That way, if you want the 95th item in a list of 100,
		 * I'm only iterating 5 times instead of 95. 
		*/

		if (index < size/2) {
			pointer = sentinel.next;
			for (int i = 0; i != index; i++)
				pointer = pointer.next;
		} else {
			pointer = sentinel.prev;
			for (int j = size - 1; j != index; j--) 
				pointer = pointer.prev;
		}

		return pointer.item;
	}

	public Item getRecursive(int index) {
		if (size == 0 || index >= size || index < 0) 
			return null; 

		return recursiveHelper(sentinel.next, index);	
	}

	private Item recursiveHelper(Node<Item> n, int index) {
		if (index == 0) return n.item;

		return recursiveHelper(n.next, index-1);
	}
}