package com.example;

import java.util.Random;

public class Question {
    private int operand1;
    private int operand2;
    private int operand3;
    private char operator1;
    private char operator2;
    private double answer;
    private double userAnswer;

    public Question(int counter) {
        Random random = new Random();
        if (counter < 10) { // Generate basic question
            operand1 = random.nextInt(50) + 1;
            operand2 = random.nextInt(50) + 1;
            operator1 = randomOperator();
            answer = calculateAnswer(operand1, operand2, operator1);
        } else { // Generate harder question
            operand1 = random.nextInt(10) + 1;
            operand2 = random.nextInt(10) + 1;
            operand3 = random.nextInt(10) + 1;
            operator1 = randomOperator();
            operator2 = randomOperator();
            answer = calculateAnswer(operand1, operand2, operand3, operator1, operator2);
        }
    }

    private char randomOperator() {
        Random random = new Random();
        char[] operators = { '+', '-', '*', '/' };
        return operators[random.nextInt(operators.length)];
    }

    private int calculateAnswer(int operand1, int operand2, char operator) {
        switch (operator) {
            case '+':
                return operand1 + operand2;
            case '-':
                return operand1 - operand2;
            case '*':
                return operand1 * operand2;
            case '/':
                // Ensure division is valid (operand2 should not be 0)
                while (operand2 == 0) {
                    operand2 = new Random().nextInt(50) + 1;
                }
                return operand1 / operand2;
            default:
                return 0; // Handle unexpected operator
        }
    }

    private int calculateAnswer(int operand1, int operand2, int operand3, char operator1, char operator2) {
        // Evaluate the expression based on BODMAS/BIDMAS rules
        int intermediateResult = calculateAnswer(operand1, operand2, operator1);
        switch (operator2) {
            case '+':
                return intermediateResult + operand3;
            case '-':
                return intermediateResult - operand3;
            case '*':
                return intermediateResult * operand3;
            case '/':
                // Ensure division is valid (operand3 should not be 0)
                while (operand3 == 0) {
                    operand3 = new Random().nextInt(10) + 1;
                }
                return intermediateResult / operand3;
            default:
                return 0; // Handle unexpected operator
        }
    }

    public String getQuestionText() {
        if (operator2 != '\u0000') { // If it's a harder question
            return operand1 + " " + operator1 + " " + operand2 + " " + operator2 + " " + operand3 + " = ?";
        } else {
            return operand1 + " " + operator1 + " " + operand2 + " = ?";
        }
    }

    public double getAnswer() {
        return answer;
    }

    public double getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(double userAnswer) {
        this.userAnswer = userAnswer;
    }
}
