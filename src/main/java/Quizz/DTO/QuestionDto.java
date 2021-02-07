package Quizz.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

public class QuestionDto {

    private  String question;
    private  String correctAnswer;
    private  String[] answerVar;

}
