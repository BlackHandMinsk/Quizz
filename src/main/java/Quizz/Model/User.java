package Quizz.Model;

import lombok.*;

@Getter
@Setter
@ToString(exclude = "password")
@EqualsAndHashCode(of = "user")
public class User {
    private int id;
    private String user;
    private int score;
    @Setter(AccessLevel.NONE)
    private String password;

    public User(int id, String user,int score, String password) {
        this.id = id;
        this.user = user;
        this.score = score;
        this.password = password;
    }
}
