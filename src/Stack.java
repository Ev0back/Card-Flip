public interface Stack <T> {
    void push(T value);

    T pop();

    void printValues();

    T peek();

    boolean isEmpty();

    void reverseStack();

    int getSize();
}
