package Quizz.DTO;

import lombok.*;
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

public class UserDto {
    private int id;
    private String user;
    private int score;
    private String password;
}

