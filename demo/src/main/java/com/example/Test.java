package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Test {
    private String playerName;
    private List<Question> basicQuestions;
    private List<Question> harderQuestions;
    private List<Question> answeredQuestions;
    private int score;
    private int additionScore;
    private int subtractionScore;
    private int multiplicationScore;
    private int divisionScore;

    public Test() {
        basicQuestions = new ArrayList<>();
        harderQuestions = new ArrayList<>();
        answeredQuestions = new ArrayList<>();
        getPlayerName();
        generateQuestions();
    }

    private void getPlayerName() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name: ");
        playerName = scanner.nextLine();
    }

    int counter = 0;

    private void generateQuestions() {
        // Generate 10 basic questions
        for (int i = 0; i < 10; i++) {
            basicQuestions.add(new Question(counter));
            counter++;
        }
    }

    private void generateHarderQuestions() {
        // Generate 10 harder questions
        for (int i = 0; i < 10; i++) {
            harderQuestions.add(new Question(counter));
        }
    }

    public void clearConsole() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (final Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Math Test!");

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                System.out.println("Time's up!");
                scanner.close(); // Close scanner when time's up
                printReport(); // Generate report
                System.exit(0); // Exit the program
            }
        }, 40 * 60 * 1000);

        displayExampleQuestion();

        // Present basic questions
        for (Question question : basicQuestions) {
            clearConsole();
            System.out.println(question.getQuestionText());
            System.out.print("Your answer: ");
            double userAnswer = scanner.nextDouble();
            question.setUserAnswer(userAnswer);
            if (userAnswer == question.getAnswer()) {
                System.out.println("Correct!");
                score++;
                updateScore(question.getQuestionText());
                System.out.println("Press Enter for new question");
                new Scanner(System.in).nextLine();
            } else {
                System.out.println("Incorrect!");
                System.out.println("Press Enter for new question");
                new Scanner(System.in).nextLine();
            }
            answeredQuestions.add(question);
        }

        // Generate and present harder questions
        generateHarderQuestions();
        for (Question question : harderQuestions) {
            clearConsole();
            System.out.println(question.getQuestionText());
            System.out.print("Your answer: ");
            double userAnswer = scanner.nextDouble();
            question.setUserAnswer(userAnswer);
            if (userAnswer == question.getAnswer()) {
                System.out.println("Correct!");
                score++;
                System.out.println("Press Enter for new question");
                new Scanner(System.in).nextLine();
            } else {
                System.out.println("Incorrect!");
                System.out.println("Press Enter for new question");
                new Scanner(System.in).nextLine();
            }
            answeredQuestions.add(question);
        }

        System.out.println("Test completed!");
        System.out.println("Addition Score: " + additionScore);
        System.out.println("Subtraction Score: " + subtractionScore);
        System.out.println("Multiplication Score: " + multiplicationScore);
        System.out.println("Division Score: " + divisionScore);
        System.out.println("Overall Score: " + score + "/20");
        System.out.println("Result: " + getResult());

        System.out.println("Press Enter if you are a teacher to see report");
        new Scanner(System.in).nextLine();
        printReport();

    }

    private void displayExampleQuestion() {
        // Display an example question
        System.out.println("Here's an example question:");
        Question exampleQuestion = new Question(0);
        System.out.println(exampleQuestion.getQuestionText());
        System.out.println("Press Enter to start the quiz...");
        new Scanner(System.in).nextLine();
    }

    private void printReport() {
        System.out.println("Report for " + playerName + ":");

        for (int i = 0; i < answeredQuestions.size(); i++) {
            Question question = answeredQuestions.get(i);
            System.out.println("Question " + (i + 1) + ": " + question.getQuestionText());
            System.out.println("Your Answer: " + question.getUserAnswer());
            System.out.println("Correct Answer: " + question.getAnswer());
            if (question.getUserAnswer() == question.getAnswer()) {
                System.out.println("Result: Correct");
            } else {
                System.out.println("Result: Incorrect");
            }
            System.out.println();
        }

    }

    private void updateScore(String questionText) {
        if (questionText.contains("+")) {
            additionScore++;
        } else if (questionText.contains("-")) {
            subtractionScore++;
        } else if (questionText.contains("*")) {
            multiplicationScore++;
        } else if (questionText.contains("/")) {
            divisionScore++;
        }
    }

    private String getResult() {
        if (score >= 17) {
            return "Distinction";
        } else if (score >= 11) {
            return "Merit";
        } else if (score >= 5) {
            return "Pass";
        } else {
            return "Fail";
        }
    }

}
