public class LinkedList<T> implements Stack <T>{
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public LinkedList(){
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * This method inserts at the top of the head
     */
    @Override
    public void push(T value) {
        var newNode = new Node<T>(value);

        if (head == null){
            head = newNode;
        } else {
            newNode.setNextNode(head);
            head = newNode;
        }
        size++;
    }
    
    /*
     * This method removes and returns the top of the linked list
     **/
    @Override
    public T pop() {
        if (head == null){
            return null;
        }
        var valToReturn = head.getValue();
        head = head.getNextNode();
        size--;
        return valToReturn;
    }

    /*
    * This function will print all the values from the LinkedList, from the head to the tail
    * */
    public void printValues(){
        Node currentNode = head;
        while (currentNode != null){
            System.out.print(currentNode.getValue());         // Print the value before moving
            currentNode = currentNode.getNextNode();            // Move our node, aka move through the LinkedList
        }
    }

    /**
     * Look at the top of the stack/head but DON'T remove from stack
     * */
    @Override
    public T peek() {
        return (head != null) ? head.getValue() : null;
    }

    /*
     * This method checks if the linked list is empty
     **/
    @Override
    public boolean isEmpty(){
        return (head == null);
    }

    /*
     * This method reverses the order of all nodes in the linked list
     **/
    @Override
    public void reverseStack() {
        // Invert a LinkedList
        Node currentNode = head;
        Node pastNode = null;

        while (currentNode != null){
            Node nextNode = currentNode.getNextNode();
            currentNode.setNextNode(pastNode);
            pastNode = currentNode;
            currentNode = nextNode;
        }
        head = pastNode;
    }

    /*
     * This method returns Size
     **/
    @Override
    public int getSize() {
        return size;
    }

    /*
     * This method returns a string value for entire linked list
     **/
    public String toString(){
        if (head == null)
            return "[]";
        if (size == 1)
            return "[" + head.getValue().toString() + "]";
        StringBuilder string = new StringBuilder("[");
        Node currNode = head;
        int count = 0;
        while(currNode != null){
            Object value = currNode.getValue();
            if (count != 0)
                string.append(", ");
            string.append(value.toString());
            currNode = currNode.getNextNode();
            count ++;
        }
        string.append("]");
        return string.toString();
    }
}
