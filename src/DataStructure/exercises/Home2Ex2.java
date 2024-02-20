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
    1 + 5 * 8 - 9
    1 5 8 * + 9 -

    res = 0;
    for (i in expression) {
        if i == str => { take  }
    }
 */

public class Home2Ex2 {
    public static void main(String[] args) {
        Stack<String> stack = new ArrayStack<>();
        Queue<String> queue = new NodeQueue<>();
        Stack<Integer> stackInt = new ArrayStack<>();

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
            String op = queue.pool();
            try {
                Integer number = Integer.valueOf(op);
                stackInt.push(number);
            } catch (Exception e) {
                Integer second = stackInt.pop();
                Integer first = stackInt.pop();

                if (op.equals("-")) {
                    stackInt.push(first - second);
                } else if (op.equals("+")) {
                    stackInt.push(first + second);
                } else if (op.equals("/")) {
                    stackInt.push(first / second);
                } else if (op.equals("*")) {
                    stackInt.push(first * second);
                } else if (op.equals("min")) {
                    if (second > first) {
                        stackInt.push(first);
                    } else {
                        stackInt.push(second);
                    }
                } else {
                    if (second < first) {
                        stackInt.push(first);
                    } else {
                        stackInt.push(second);
                    }
                }
            }
        }
        while (!stackInt.isEmpty()) {
            System.out.println(stackInt.pop());
        }
    }
}


