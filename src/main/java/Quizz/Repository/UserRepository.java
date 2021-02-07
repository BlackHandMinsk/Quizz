package Quizz.Repository;

import Quizz.Exceptions.ItemNotFoundException;
import Quizz.Model.Rank;
import Quizz.Model.User;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.*;
import java.util.ArrayList;

public class UserRepository {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/quiz?serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";


    private Connection connection;

    public  Connection getConnection() throws SQLException {

        if (connection == null) {
            Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
            this.connection = connection;
        }

        return connection;
    }


    public int getUserScore(int idUser) throws SQLException, ItemNotFoundException {
        String query = "select score from users where id_users=?;";
        PreparedStatement preparedStatement =
                getConnection().prepareStatement(query);
        preparedStatement.setInt(1, idUser);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.next())
            throw new ItemNotFoundException("User");
        int score = resultSet.getInt("score");
        return score;
    }


    public ArrayList<Rank> getRank()throws SQLException{
        ArrayList<Rank> rank = new ArrayList<>();
        PreparedStatement preparedStatement =
                getConnection().prepareStatement("select user , max(score)\n" +
                        "from users\n" +
                        "group by id_users\n" +
                        "order by max(score) desc");


        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()) {
            String name = resultSet.getString("user");
            int score = resultSet.getInt("max(score)");
            Rank ranks = new Rank(name,score);
            rank.add(ranks);
        }
        return rank;
    }


    public User getByUser(String userName) throws SQLException, ItemNotFoundException {
        String query = "select * from users where user=?;";
        PreparedStatement preparedStatement =
                getConnection().prepareStatement(query);
        preparedStatement.setString(1, userName);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (!resultSet.next())
            throw new ItemNotFoundException("User");

        int id = resultSet.getInt("id_users");
        String user = resultSet.getString("user");
        int score = resultSet.getInt("score");
        String password = resultSet.getString("password");

        return new User(id, user, score, password);
    }

    public User newUser(String inName,int inScore, String inPassword) throws SQLException, ItemNotFoundException {
        String inPasswordHex = DigestUtils.sha256Hex(inPassword);

        String query = "insert into users value (null,?,?,?);";
        PreparedStatement preparedStatement =
                getConnection().prepareStatement(query);
        preparedStatement.setString(1, inName);
        preparedStatement.setInt(2, inScore);
        preparedStatement.setString(3, inPasswordHex);
        preparedStatement.executeUpdate();


        return getByUser(inName);

    }

    public void scoreUpdate(int id_users) throws SQLException{
        String query = "UPDATE users SET score = score+1 WHERE id_users=?";
        PreparedStatement preparedStatement =
                getConnection().prepareStatement(query);
        preparedStatement.setInt(1, id_users);
        preparedStatement.executeUpdate();
    }


}
