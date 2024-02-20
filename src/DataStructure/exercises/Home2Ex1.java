package DataStructure.exercises;

import java.util.Scanner;

/*
    очередь для чисел и стек для операций.
    если встречается опеарция, приоритенее peek(), то push(el),
    инача убрать 1 со стека и добавить в очередь

    2) все наоборот
    если оператор, берем 2 числа с стека и убираем, прибавляем к результату
 */

/*
    res = 0;
    for (i in expression) {
        if i == str => { take  }
    }
 */

public class Home2Ex1 {
    public static void main(String[] args) {
        Stack<String> stack = new ArrayStack<>();
        Queue<String> queue = new NodeQueue<>();

        Scanner in = new Scanner(System.in);
        String operand = "";
        while (in.hasNext()) {
            operand = in.next();
            try {
                Integer number = Integer.valueOf(operand);
                queue.offer(number.toString());
            } catch (Exception e) {
                if (stack.isEmpty()) {
                    stack.push(operand);
                } else {
                    if (operand.equals("-") || operand.equals("+")) {
                        if (!stack.peek().equals("+") && !stack.peek().equals("-") &&
                                !stack.peek().equals("/") && !stack.peek().equals("*")) {
                            stack.push(operand);
                        } else {
                            while (stack.size() > 0 && !(stack.peek().equals("("))) {
                                queue.offer(stack.pop());
                            }
                            stack.push(operand);
                        }
                    } else if (operand.equals("(")) {
                        stack.push(operand);
                    } else if (operand.equals("*") || operand.equals("/")) {
                        if (!stack.peek().equals("*") && !stack.peek().equals("/")) {
                            stack.push(operand);
                        } else {
                            queue.offer(stack.pop());
                            stack.push(operand);
                        }
                    } else if (operand.equals("min") || operand.equals("max")) {
                        // ( 2 + 3 - min ( 1 , 2 ) / 8 + 7)
                        // 1 2 3 1 2 min 8 / 7 + - +
                        stack.push(operand);
                    } else if (operand.equals(")")) {
                        while (!(stack.peek().equals("("))) {
                            queue.offer(stack.pop());
                        }
                        stack.pop();
                        if (!stack.isEmpty() &&
                                (stack.peek().equals("min") || stack.peek().equals("max"))) {
                            queue.offer(stack.pop());
                            //while (stack.size() > 0 && !(stack.peek().equals("("))) {
                            //    queue.offer(stack.pop());
                            //}
                        }
                    } else if (operand.equals(",")) {
                        while (!(stack.peek().equals("("))) {
                            queue.offer(stack.pop());
                        }
                    }
                }
            }
        }
        while (!stack.isEmpty()) {
            queue.offer(stack.pop());
        }
        while (!queue.isEmpty()) {
            System.out.print(queue.pool() + " ");
        }
    }
}

class Node<T> {
    T val;
    Node<T> next;
    public Node(T val, Node<T> next) {
        this.val = val;
        this.next = next;
    }
}

interface Queue<T> {
    void offer(T item);
    T pool();
    T peek();
    int size();
    boolean isEmpty();
}

class NodeQueue<T> implements Queue<T> {
    Node<T> head;
    Node<T> tail;
    int queueSize;
    public NodeQueue() {
        this.head = null;
        this.tail = null;
        this.queueSize = 0;
    }
    @Override
    public int size() {
        return this.queueSize;
    }
    @Override
    public boolean isEmpty() {
        return (this.queueSize == 0);
    }
    @Override
    public void offer(T item) {
        if (this.head == null) {
            this.head = new Node<>(item, null);
            this.tail = this.head;
        } else {
            this.tail.next = new Node<>(item, null);
            this.tail = this.tail.next;
        }
        this.queueSize++;
    }
    @Override
    public T pool() {
        T item = this.head.val;
        this.head = this.head.next;
        if (this.head == null) {
            this.tail = null;
        }
        this.queueSize--;
        return item;
    }
    @Override
    public T peek() {
        return this.head.val;
    }
}

class ArrayList<T> {
    int sizeOfElems = 0;
    Object[] arrayList;
    public ArrayList(int size) {
        this.arrayList = new Object[size];
    }
    public void add(T item) {
        this.arrayList[this.sizeOfElems] = item;
        this.sizeOfElems++;
    }
    public void add(int idx, T item) {
        this.arrayList[idx] = item;
        this.sizeOfElems++;
    }
    public void remove(int idx) {
        if (idx >= 0 && idx < this.sizeOfElems) {
            for (; idx < this.sizeOfElems; idx++) {
                this.arrayList[idx] = this.arrayList[idx+1];
            }
            this.sizeOfElems--;
        }
    }
    public T get(int idx) {
        return (T) this.arrayList[idx];
    }
}

interface Stack<T> {
    void push(T item);
    T pop();
    T peek();
    int size();
    boolean isEmpty();
}

class ArrayStack<T> implements Stack<T> {
    final int INITIAL_SIZE = 1000;
    ArrayList<T> items;
    int stackSize;

    public ArrayStack() {
        this.items = new ArrayList<>(INITIAL_SIZE);
        this.stackSize = 0;
    }
    @Override
    public void push(T item) {
        this.items.add(this.stackSize, item);
        this.stackSize++;
    }
    @Override
    public T pop() {
        if (this.stackSize <= 0) {
            throw new RuntimeException("Stack is empty");
        }
        this.stackSize--;
        T item = this.items.get(this.stackSize);
        this.items.remove(this.stackSize);
        return item;
    }
    @Override
    public T peek() {
        if (this.stackSize <= 0) {
            throw new RuntimeException("Stack is empty");
        }
        T item = this.items.get(this.stackSize-1);
        return item;
    }
    @Override
    public int size() {
        return this.stackSize;
    }
    @Override
    public boolean isEmpty() {
        if (this.stackSize <= 0) {
            return true;
        }
        return false;
    }
}

