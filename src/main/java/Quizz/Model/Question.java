package Quizz.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Getter
@Setter
@EqualsAndHashCode

public class Question {
    @JsonProperty("question")
    private  String question;
    @JsonProperty("correctAnswer")
    private  String correctAnswer;
    @JsonProperty("answerVar")
    private String[] answerVar;

    public Question() {

    }

    public Question(String question, String correctAnswer, String... answerVar) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.answerVar = answerVar;
    }

//    public void printAnswer() {
//        System.out.println("Выберите один вариант овета: ");
//        for (int i = 0; i < answerVar.length; i++) {
//            System.out.println(i + 1 + ". " + answerVar[i]);
//        }
//    }
//
//    public int checkAnswer(String answer) {
//        if (answer.equals(correctAnswer))
//            return 1;
//        else return 0;
//    }
//
//    public String getQuestion() {
//        return question;
//    }
//
//    public String getCorrectAnswer() {
//        return correctAnswer;
//    }
//
//    public String[] getAnswerVar() {
//        return answerVar;
//    }

    @Override
    public String toString() {
        return "Question{" +
                "question='" + question + '\'' +
                ", correctAnswer='" + correctAnswer + '\'' +
                ", answerVar=" + Arrays.toString(answerVar) +
                '}';
    }
}
