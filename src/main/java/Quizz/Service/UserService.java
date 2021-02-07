package Quizz.Service;

import Quizz.DTO.UserDto;
import Quizz.Exceptions.ItemNotFoundException;
import Quizz.Model.User;
import Quizz.Repository.UserRepository;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.SQLException;

public class UserService {

    private UserRepository userRepository = new UserRepository();

    public UserDto getByUser(String in) throws SQLException, ItemNotFoundException {

        User user = userRepository.getByUser(in);

        return UserDto.builder()
                .id(user.getId())
                .user(user.getUser())
                .score(user.getScore())
                .password(user.getPassword())
                .build();
    }

    public UserDto newUser(String inName,int inScore, String inPassword) throws SQLException, ItemNotFoundException {

        User user = userRepository.newUser(inName,inScore,inPassword);

        return UserDto.builder()
                .id(user.getId())
                .user(user.getUser())
                .score(user.getScore())
                .password(user.getPassword())
                .build();
    }


    public boolean checkPassword(String in, UserDto user) {
        return DigestUtils.sha256Hex(in).equals(user.getPassword());
    }
}
