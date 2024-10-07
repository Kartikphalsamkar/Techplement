import java.util.*;

class Question {
    private String question;
    private List<String> choices;
    public int correctChoice;

    public Question(String question, List<String> choices, int correctChoice) {
        this.question = question;
        this.choices = choices;
        this.correctChoice = correctChoice;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getChoices() {
        return choices;
    }

    public boolean isCorrect(int choice) {
        return choice == correctChoice;
    }
}

class Quiz {
    private String name;
    private List<Question> questions;

    public Quiz(String name) {
        this.name = name;
        this.questions = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public List<Question> getQuestions() {
        return questions;
    }
}

public class QuizApp {
    private Map<String, Quiz> quizzes;

    public QuizApp() {
        quizzes = new HashMap<>();
    }

    public void createQuiz(String name) {
        if (quizzes.containsKey(name)) {
            System.out.println("Quiz with this name already exists.");
            return;
        }
        quizzes.put(name, new Quiz(name));
        System.out.println("Quiz '" + name + "' created successfully.");
    }

    public void addQuestionToQuiz(String quizName, String questionText, List<String> choices, int correctChoice) {
        Quiz quiz = quizzes.get(quizName);
        if (quiz == null) {
            System.out.println("Quiz not found.");
            return;
        }
        Question question = new Question(questionText, choices, correctChoice);
        quiz.addQuestion(question);
        System.out.println("Question added to quiz '" + quizName + "'.");
    }

    public void takeQuiz(String quizName) {
        Quiz quiz = quizzes.get(quizName);
        if (quiz == null) {
            System.out.println("Quiz not found.");
            return;
        }
        
        Scanner scanner = new Scanner(System.in);
        int score = 0;

        for (Question question : quiz.getQuestions()) {
            System.out.println(question.getQuestion());
            List<String> choices = question.getChoices();
            for (int i = 0; i < choices.size(); i++) {
                System.out.println((i + 1) + ": " + choices.get(i));
            }
            System.out.print("Your answer (1-" + choices.size() + "): ");
            int userAnswer = scanner.nextInt() - 1;

            if (question.isCorrect(userAnswer)) {
                score++;
                System.out.println("Correct!");
            } else {
                System.out.println("Wrong! The correct answer was: " + (question.getChoices().indexOf(choices.get(question.correctChoice)) + 1));
            }
            System.out.println();
        }

        System.out.println("You scored " + score + " out of " + quiz.getQuestions().size());
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        QuizApp app = new QuizApp();
        
        while (true) {
            System.out.println("Commands: create_quiz, add_question, take_quiz, exit");
            System.out.print("Enter command: ");
            String command = scanner.nextLine();

            switch (command.toLowerCase()) {
                case "create_quiz":
                    System.out.print("Enter quiz name: ");
                    String quizName = scanner.nextLine();
                    app.createQuiz(quizName);
                    break;

                case "add_question":
                    System.out.print("Enter quiz name: ");
                    String qName = scanner.nextLine();
                    System.out.print("Enter question text: ");
                    String questionText = scanner.nextLine();
                    
                    List<String> choices = new ArrayList<>();
                    System.out.print("Enter number of choices: ");
                    int numChoices = scanner.nextInt();
                    scanner.nextLine(); // consume the newline
                    for (int i = 0; i < numChoices; i++) {
                        System.out.print("Enter choice " + (i + 1) + ": ");
                        choices.add(scanner.nextLine());
                    }
                    
                    System.out.print("Enter the index of the correct choice (1-" + numChoices + "): ");
                    int correctChoiceIndex = scanner.nextInt() - 1; // convert to zero-based index
                    scanner.nextLine(); // consume the newline

                    app.addQuestionToQuiz(qName, questionText, choices, correctChoiceIndex);
                    break;

                case "take_quiz":
                    System.out.print("Enter quiz name: ");
                    String takeQuizName = scanner.nextLine();
                    app.takeQuiz(takeQuizName);
                    break;

                case "exit":
                    System.out.println("Exiting the application.");
                    return;

                default:
                    System.out.println("Unknown command. Please try again.");
            }
            System.out.println();
        }
    }
}