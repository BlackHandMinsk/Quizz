package Quizz.Service;

import Quizz.Model.Question;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class QuestionService {

    public static void toJavaObject(ArrayList<Question[]> questions, String baseFile) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        questions.add(mapper.readValue(new File(baseFile), Question[].class));
    }
}
