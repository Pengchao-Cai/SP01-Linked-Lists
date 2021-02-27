package pxc190029;


import java.util.Scanner;
import java.util.NoSuchElementException;

/**
 * DLL Implementation based on SLL
 * Course: CS 6301
 * @author Pengchao Cai & Jie Su
 * @param <T> Generic type.
 */
public class DoublyLinkedList<T> extends SinglyLinkedList<T> {
	
	/**
	 * Entry [prev|element|next] stores a dll node.
	 */
	static class Entry<E> extends SinglyLinkedList.Entry<E> {
		Entry<E> prev;

		Entry(E x, Entry<E> next, Entry<E> prev) {
			super(x, next);
			this.prev = prev;
		}
	}
	
	/**
	 * DLL default constructor.
	 */
	public DoublyLinkedList() {
		head = new Entry<>(null, null, null);
		tail = head;
		size = 0;
	}
	
	/**
	 * @return a dll iterator
	 */
	public DLLIterator dllIterator() { 
		return new DLLIterator(); 
	}

	/**
	 * DLL Iterator Interface
	 * DLLIterator cannot call iterator methods, since for ddl, it has more methods than
	 * a standard iterator interface. 
	 * @param <T>
	 */
	public interface DoublyLinkedListIterator<T> {
		boolean hasNext();
		boolean hasPrev();
		T next();
		T prev();
		void add(T x);
		void remove();
	}
	
	
	protected class DLLIterator extends SLLIterator implements DoublyLinkedListIterator<T> {

		public boolean hasPrev() {
			return ((DoublyLinkedList.Entry<T>) cursor).prev != null;
		}

		public T prev() {
			cursor = prev;
			prev = ((DoublyLinkedList.Entry<T>) cursor).prev;
			ready = true;
			return cursor.element;
		}

		/**
		 * Removes the current element (retrieved by the most recent next()). Remove can
		 * be called only if next has been called and the element has not been removed.
		 */
		public void remove() {

			if (!ready) {
				throw new NoSuchElementException();
			}

			// Handle case when tail of a list is to be deleted
			if (cursor == tail) {
				prev.next = null;
				tail = prev;
			} else {
				prev.next = cursor.next;
				((DoublyLinkedList.Entry<T>) cursor.next).prev = (Entry<T>) prev;
			}

			cursor = prev;
			prev = ((DoublyLinkedList.Entry<T>) cursor).prev;
			// Calling remove again without calling next will result in exception thrown
			ready = false;
			size--;
		}

		// Generic add(x) calls to add(Entry<T> e)
		public void add(T x) {
			System.out.println(x);
			add(new Entry<>(x, null, null));
		}

		/**
		 * Main add(). Adds an Entry before the element that will be returned by a call to next()
		 * @param ent the Entry to be added.
		 */
		public void add(Entry<T> ent) {

			// When cursor is tail 
			if (cursor == tail) {
				tail.next = ent;
				ent.prev = (Entry<T>) tail;
				tail = tail.next;
			} else {
				ent.prev = (Entry<T>) cursor;
				ent.next = cursor.next;
				((DoublyLinkedList.Entry<T>) cursor.next).prev = ent;
				cursor.next = ent;
			}
			cursor = ent;
			prev = ent.prev;
			size++;
		}

	} // end of class DLLIterator

	// Add new elements to the end of the list
	public void add(T x) {
		add(new Entry<>(x, null, null));
	}

	public void add(Entry<T> ent) {
		tail.next = ent;
		ent.prev = (Entry<T>) tail;
		tail = tail.next;
		size++;
	}

	public static void main(String[] args) throws NoSuchElementException {
		int n = 10;
		if (args.length > 0) { 
			n = Integer.parseInt(args[0]);
		}
		
		DoublyLinkedList<Integer> lst = new DoublyLinkedList<>();
		
		for (int i = 1; i <= n; i++) {
			lst.add(Integer.valueOf(i));
		}
		
		lst.printList();
		
		DoublyLinkedListIterator<Integer> it = lst.dllIterator();
		
		Scanner in = new Scanner(System.in);
		
		whileloop: 
		while (in.hasNext()) {
			int command = in.nextInt();
			
			switch (command) {

			// check if has next, if so, print next
			case 1:
				if (it.hasNext()) {
					System.out.println(it.next());
				} else {
					break whileloop;
				}
				break;
		    // check if has pre, if so, print pre
			case 2:
				if (it.hasPrev()) {
					System.out.println(it.prev());
				} else {
					break whileloop;
				}
				break;
		    // remove current Entry
			case 3:
				it.remove();
				lst.printList();
				break;
			// Insert a new Entry before next.
			case 4:
				int data = in.nextInt();
				it.add(data);
				lst.printList();
				break;

			default:
				break whileloop;
			}
		}

		lst.printList();
		lst.unzip();
		lst.printList();
	}

}
