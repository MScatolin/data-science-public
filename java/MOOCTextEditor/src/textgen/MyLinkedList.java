package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		// TODO: Implement this method
		
		this.size = 0;
		this.head = new LLNode<E>();
		this.tail = new LLNode<E>();
		this.head.next = this.tail;
		this.tail.prev = this.head;
		
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element )  
	{
		// TODO: Implement this method.
		//if the element is a wrong type or is a null (which is a wrong type also), it throws and exception:
		if (element == null){
			throw new NullPointerException("Invalid element input");
		}
		LLNode<E> nodeToAdd = new LLNode<E>(element);
		nodeToAdd.next = tail;
		nodeToAdd.prev = tail.prev;
		tail.prev = nodeToAdd;
		nodeToAdd.prev.next = nodeToAdd;
		size++;
				
		return true;
	}


	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) 
	{
		// TODO: Implement this method.
		if (index < 0 || index > size-1){
			throw new IndexOutOfBoundsException("Index out of bounds");
		}
		LLNode<E> nodeToSeek = head;
		for (int j = 0 ;j <= index; j++){
			nodeToSeek = nodeToSeek.next; 
		}
		
		return nodeToSeek.data;
	}

	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element ) 
	{
		// TODO: Implement this method
		//Null elements are not allowed:
		if (element == null){
			throw new NullPointerException("Null elements are not allowed, sorry!");
		}
		if ((index < 0 || index > size-1) && (index != 0 || size != 0)){
			throw new IndexOutOfBoundsException("Index out of bounds");
		}
		LLNode<E> nodeToInsert = new LLNode<E>();
		LLNode<E> posToInsert = head;
		for (int j = 0 ;j <= index; j++){
			posToInsert = posToInsert.next;
		}
		
		LLNode<E> prev = posToInsert.prev;
		nodeToInsert.next = posToInsert;
		nodeToInsert.prev = prev;
		prev.next = nodeToInsert;
		posToInsert.prev = nodeToInsert;
		nodeToInsert.data = element;	
		size++;
	}


	/** Return the size of the list */
	public int size() 
	{
		// TODO: Implement this method
		return size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) 
	{
		// TODO: Implement this method
		if (index < 0 || index > size-1){
			throw new IndexOutOfBoundsException("Index out of bounds");
		}
		LLNode<E> nodeToRemove = head;
		for (int j = 0 ;j <= index; j++){
			nodeToRemove = nodeToRemove.next;
		}
		LLNode<E> prevNode = nodeToRemove.prev;
		LLNode<E> nextNode = nodeToRemove.next;
		
		nextNode.prev = prevNode;
		prevNode.next = nextNode;
		
		size--;
		
		E value = nodeToRemove.data;
		return value;
	}
	
	

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) 
	{
		// TODO: Implement this method
		if (element == null){
			throw new NullPointerException("Null elements are not allowed, Sorry!");
		}
		if (index < 0 || index > size-1) {
			throw new IndexOutOfBoundsException("Index out of bounds");
		}
		LLNode<E> nodeToSeek = head;
		for (int j = 0 ;j <= index; j++){
			nodeToSeek = nodeToSeek.next; 
		}
		E oldValue = nodeToSeek.data;
		nodeToSeek.data = element;
		return oldValue;
	}   
}

class LLNode<E> 
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	// TODO: Add any other methods you think are useful here
	// E.g. you might want to add another constructor

	public LLNode(E e) 
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}

	public LLNode() {
		// TODO Auto-generated constructor stub
		this.data = null;
		this.prev = null;
		this.next = null;
	}

}
