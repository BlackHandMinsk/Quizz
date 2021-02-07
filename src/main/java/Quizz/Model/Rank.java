package Quizz.Model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class Rank {
    String name;
    int score;
    public Rank(String name, int score) {
        this.name = name;
        this.score = score;
    }

    @Override
    public String toString() {
        return
                "ИМЯ: " +name+" "+
                "ОЧКИ: " + score;
    }
}
