/*
 * Generic resizeable array implementing the Deque interface
 * @author Will Riley on 6/24/16
 */
public class ArrayDeque<Item> implements Deque<Item> {
    private Item[] items;
    private int size;
    private int nextFirst; // index for next addFirst
    private int nextLast; // index for next addLast

    // # that old size will be mult/divided by whenever array is expanded/downsized
    private static final int RESIZE_FACTOR = 2;
    // initial array size
    private static final int INIT_SIZE = 8;
    // min % of array that can be used before it's downsized
    private static final double MIN_USAGE = 0.25;

    public ArrayDeque() {
        items = (Item[]) new Object[INIT_SIZE]; // creates generic array
        size = 0;
        nextFirst = 3;
        nextLast = 4;
    }

    @Override
    public void addFirst(Item i) {
        if (isFull()) {
            resize(items.length * RESIZE_FACTOR);
        }
        items[nextFirst] = i;
        nextFirst = minusOne(nextFirst);
        size++;
    }

    @Override
    public void addLast(Item i) {
        if (isFull()) {
            resize(items.length * RESIZE_FACTOR);
        }
        items[nextLast] = i;
        nextLast = plusOne(nextLast);
        size++;
    }

    // returns next index for addFirst or last index for addLast
    private int minusOne(int index) {
        if (index == 0) {
            return items.length - 1;
        }
        return index - 1;
    }

    // returns next index for addLast or last index for addFirst
    private int plusOne(int index) {
        if (index == items.length-1) {
            return 0;
        }
        return index + 1;
    }

    @Override
    public int size() { return size; }

    @Override
    public boolean isEmpty() { return size == 0; }

    public boolean isFull() { return size == items.length; }
    
    @Override
    public void printDeque() {
        for (int i=0; i<size; i++) {
            System.out.print(get(i) + " ");
        }
        System.out.println();
    }

    @Override
    public Item removeFirst() {
        if (isEmpty()) { return null; }
        int currentFirst = plusOne(nextFirst);
        nextFirst = currentFirst;
        return remove(currentFirst);
    }

    @Override
    public Item removeLast() {
        if (isEmpty()) { return null; }
        int currentLast = minusOne(nextLast);
        nextLast = currentLast;
        return remove(currentLast);
    }

    private Item remove(int index) {
        Item removed = items[index];
        items[index] = null;
        size--;
        checkUsage();
        return removed;
    }

    @Override
    // gets ith item in array based on insertion order, NOT actual index in array
    public Item get(int index) {
        if (invalidIndex(index)) { return null; }
        int firstIndex = plusOne(nextFirst);
        return items[(firstIndex + index) % items.length];
    }

    private boolean invalidIndex(int index) {
        return (size == 0) || (index >= size) || (index < 0);
    }

    private void checkUsage() {
        double percentUsed = (double) size / items.length;
        if (percentUsed < MIN_USAGE && items.length >= 16) {
            resize(items.length/RESIZE_FACTOR);
        }
    }

    private void resize(int capacity) {
        Item[] temp = (Item[]) new Object[capacity];
        for (int i=0; i<size; i++) {
            temp[i] = get(i);
        }
        nextFirst = temp.length-1;
        nextLast = size;
        items = temp;
    }
}