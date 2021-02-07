package Quizz.Model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class Rank {
    public Rank(String name, int score) {
        this.name = name;
        Score = score;
    }

    @Override
    public String toString() {
        return
                "ИМЯ: " +name+" "+
                "ОЧКИ: " + Score;
    }

    String name;
    int Score;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }
}
