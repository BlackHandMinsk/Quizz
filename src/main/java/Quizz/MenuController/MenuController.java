package Quizz.MenuController;


import Quizz.DTO.UserDto;
import Quizz.Exceptions.ItemNotFoundException;
import Quizz.Exceptions.MistakeException;
import Quizz.Model.Question;
import Quizz.Model.Rank;

import Quizz.Repository.UserRepository;
import Quizz.Service.QuestionService;
import Quizz.Service.UserService;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class MenuController {

    private static final String QUIZ_LOGO = "\n ╔═══╗╔╗╔╗╔══╗╔═══╗\n ║╔═╗║║║║║╚╗╔╝╚═╗─║\n ║║─║║║║║║─║║──╔╝╔╝\n ║║╔╝║║║║║─║║─╔╝╔╝\n ╚═══╝╚══╝╚══╝╚═══╝";
    private static final String  START_MENU =  "-------------------------\n1. ВОЙТИ В АККАУНТ\n2. СОЗДАТЬ АККАУНТ\ne. ЗАКРЫТЬ ПРОГРАММУ\n-------------------------";
    private static final String  USER_MENU =  "-------------------------\n1. НОВАЯ ИГРА\n2. ИГРА ДА ИЛИ НЕТ\n3. ПОСМОТРЕТЬ РЕЙТИНГ\n4. ПОКАЗАТЬ ТАБЛИЦУ ЛИДЕРОВ\ne. ПРЕДЫДУЩЕЕ МЕНЮ\n-------------------------";

    private static final String YES_OR_NO_QUESTIONS_PATH =  "C:\\Users\\37544\\IdeaProjects\\Quizz\\src\\main\\resources\\yes-or-no-questions.json";
    private static final String QUESTIONS_PATH = "C:\\Users\\37544\\IdeaProjects\\Quizz\\src\\main\\resources\\questions.json";

    private final UserService userService = new UserService();
    private final UserRepository userRepository = new UserRepository();

    private static final String MISTAKE_SOUND = "C:\\Users\\37544\\IdeaProjects\\Quizz\\src\\main\\resources\\sound-mistake.wav";
    private static final String GAME_OVER_SOUND = "C:\\Users\\37544\\IdeaProjects\\Quizz\\src\\main\\resources\\zvuk-game-over.wav";
    private final File MAIN_MENU_SOUND = new File("C:\\Users\\37544\\IdeaProjects\\Quizz\\src\\main\\resources\\startMenuSound.wav");






    public void start(){
        SoundController soundController = new SoundController(MAIN_MENU_SOUND);
            soundController.setVolume(0.7f);
            soundController.play();
            String in = "";
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println(QUIZ_LOGO);
            while (!"e".equals(in)) {
                try {
                    System.out.println(START_MENU);
                    in = reader.readLine();
                    switch (in) {
                        case "1":
                            soundController.close();
                            startSession();
                            break;
                        case "2":
                            soundController.close();
                            startCreature();
                            break;
                        case "e":
                            soundController.close();
                            break;
                    }

                } catch (IOException e) {
                    System.err.println("НЕВАЛИДНЫЙ ВВОД ДАННЫХ: " + e.getMessage());
                }
            }
        }





    public void startSession(){
        String in = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (!"e".equals(in)) {
            try {
                System.out.println("ВВЕДИТЕ ИМЯ ПОЛЬЗОВАТЕЛЯ, ЛИБО 'e' ДЛЯ ВЫХОДА В ПРЕДЫДУЩЕЕ МЕНЮ:");
                in = reader.readLine();
                if ("e".equals(in)){start();}
                else {
                    UserDto userDto = userService.getByUser(in);
                    if (userDto != null) {
                        System.out.println("ВВЕДИТЕ ПАРОЛЬ:");
                        in = reader.readLine();
                        if (userService.checkPassword(in, userDto)) {
                            startUserSession(userDto.getUser(),userDto.getId());
                            in = "e";
                        } else {
                            System.err.println("ПАРОЛЬ ВВЕДЕН НЕ ВЕРНО");
                        }
                    }
                }
            } catch(ItemNotFoundException e){
                System.err.println("ДАННАЯ УЧЕТНАЯ ЗАПИСЬ НЕ НАЙДЕНА: " + e.getMessage());
            } catch(IOException | SQLException e){
                e.printStackTrace();
            }

        }
    }


    private void startCreature() {
        String inName;
        String inPassword;
        int inScore = 0;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println(" ВЫ ПОПАЛИ В  МЕНЮ СОЗДАНИЯ АККАУНТА ");
            System.out.println("ВВЕДИТЕ ИМЯ ПОЛЬЗОВАТЕЛЯ, ЛИБО 'e' ДЛЯ ВЫХОДЫ В ПРЕДЫДУЩЕЕ МЕНЮ:");
            inName = reader.readLine();
            if ("e".equals(inName)) {
                start();
            } else {
                System.out.println("ВВЕДИТЕ ПАРОЛЬ");
                inPassword = reader.readLine();
                UserDto userDto = userService.newUser(inName, inScore, inPassword);
                System.out.println("НОВЫЙ АККАУНТ СОЗДАН, ДОБРО ПОЖАЛОВАТЬ");
                startUserSession(userDto.getUser(),userDto.getId());
            }
        } catch (IOException | SQLException | ItemNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void startUserSession(String user,int id) throws SQLException, ItemNotFoundException {
        String in = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (!"e".equals(in)) {
            try {
                System.out.println(USER_MENU);
                in = reader.readLine();
                switch (in) {
                    case "1":
                        newGame(id);
                        break;
                    case "2":
                        yesOrNo(id);
                        break;
                    case "3":
                        showRating(id);
                        break;
                    case "4":
                        showRank(id);
                        break;
                    case "e":
                        break;
                }
            } catch (IOException e) {
                System.err.println("НЕВАЛИДНЫЙ ВВОД ДАННЫХ: " + e.getMessage());
            }
        }
    }


    private void showRank(int id) throws SQLException {
       ArrayList<Rank> ranks = userRepository.getRank();
        System.out.println("ТАБЛИЦА ЛИДЕРОВ: ");
      for (Rank rank:ranks){
          System.out.println(rank);
      }
    }

    private void yesOrNo(int id) throws IOException {
        int mistake = 0;
        int level = 0;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<Question[]> arr = new ArrayList<>();
        QuestionService.toJavaObject(arr, YES_OR_NO_QUESTIONS_PATH);
        try {
            System.out.println("-----------------------\nДА ИЛИ НЕТ\n-----------------------");
            System.out.println("ВЫБЕРИТЕ УРОВЕНЬ СЛОЖНОСТИ: \n1. Легкий - 5 ошибок\n2. Средний - 3 ошибки\n3. Тяжелый - 1 ошибка");
            String in = reader.readLine();
            switch (in) {
                case "1":
                    level+=5;
                    System.out.println("------------------------\nВЫ ВЫБРАЛИ ЛЕГКИЙ УРОВЕНЬ СЛОЖНОСТИ\n------------------------");
                    break;
                case "2":
                    level+=3;
                    System.out.println("------------------------\nВЫ ВЫБРАЛИ СРЕДНИЙ УРОВЕНЬ СЛОЖНОСТИ\n------------------------");
                    break;
                case "3":
                    level+=1;
                    System.out.println("------------------------\nВЫ ВЫБРАЛИ ТЯЖЕЛЫЙ УРОВЕНЬ СЛОЖНОСТИ\n------------------------");
                    break;
            }
            System.out.println("ОТВЕЧАЙТЕ НА ВОПРОСЫ ДА ИЛИ НЕТ, ЗА КАЖДЫЙ ПРАВИЛЬНЫЙ ОТВЕТ ВЫ ПОЛУЧАТЕТЕ ОДИН БАЛЛ\nВЫ МОЖЕТЕ ДОПУСТИТЬ "+level+" ОШИБКИ");
            for (Question[] question : arr) {
                for (Question question1 : question) {
                    System.out.println(question1.getQuestion());
                    System.out.println(Arrays.toString(question1.getAnswerVar()));
                    String a = reader.readLine().toLowerCase();
                    if (!a.equals(question1.getCorrectAnswer().toLowerCase())) {
                        mistake += 1;
                        SoundController.playSound(MISTAKE_SOUND).join();
                        System.out.println("------------------------\nНЕПРАВИЛЬНЫЙ ОТВЕТ\n------------------------");
                        if(mistake==level){
                            throw new MistakeException();
                        }
                    } else if (a.equals(question1.getCorrectAnswer().toLowerCase())) {
                        userRepository.scoreUpdate(id);
                        System.out.println("------------------------\nОТВЕТ ВЕРЕН\n------------------------");
                        System.out.println(question1.getCorrectAnswerInfo()+"\n------------------------");
                    }
                }
            }

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        } catch (MistakeException c) {
            System.out.println("------------------------\nНЕПРАВИЛЬНЫЙ ОТВЕТ, ИГРА ЗАКОНЧЕНА\n------------------------");
            SoundController.playSound(GAME_OVER_SOUND).join();
        }
    }

    private void newGame(int id) throws IOException, SQLException {
        int mistake = 0;
        int level = 0;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<Question[]> arr = new ArrayList<>();
        QuestionService.toJavaObject(arr, QUESTIONS_PATH);
            try {
                System.out.println("-----------------------\nНОВАЯ ИГРА\n-----------------------");
                System.out.println("ВЫБЕРИТЕ УРОВЕНЬ СЛОЖНОСТИ: \n1. Легкий - 5 ошибок\n2. Средний - 3 ошибки\n3. Тяжелый - 1 ошибка");
                String in = reader.readLine();
                switch (in) {
                    case "1":
                       level+=5;
                        System.out.println("------------------------\nВЫ ВЫБРАЛИ ЛЕГКИЙ УРОВЕНЬ СЛОЖНОСТИ\n------------------------");
                        break;
                    case "2":
                        level+=3;
                        System.out.println("------------------------\nВЫ ВЫБРАЛИ СРЕДНИЙ УРОВЕНЬ СЛОЖНОСТИ\n------------------------");
                        break;
                    case "3":
                        level+=1;
                        System.out.println("------------------------\nВЫ ВЫБРАЛИ ТЯЖЕЛЫЙ УРОВЕНЬ СЛОЖНОСТИ\n------------------------");
                        break;
                }
                System.out.println("ОТВЕЧАЙТЕ НА ВОПРОСЫ, ЗА КАЖДЫЙ ПРАВИЛЬНЫЙ ОТВЕТ ВЫ ПОЛУЧАТЕТЕ ОДИН БАЛЛ\nВЫ МОЖЕТЕ ДОПУСТИТЬ "+level+" ОШИБКИ");
                    for (Question[] question : arr) {
                        for (Question question1 : question) {
                                System.out.println(question1.getQuestion());
                                System.out.println(Arrays.toString(question1.getAnswerVar()));
                                String a = reader.readLine();
                                if (!a.equals(question1.getCorrectAnswer())) {
                                    mistake += 1;
                                    SoundController.playSound(MISTAKE_SOUND).join();
                                    System.out.println("------------------------\nНЕПРАВИЛЬНЫЙ ОТВЕТ\n------------------------");
                                    if(mistake==level){
                                        throw new MistakeException();
                                    }
                                } else if (a.equals(question1.getCorrectAnswer())) {
                                    userRepository.scoreUpdate(id);
                                    System.out.println("------------------------\nОТВЕТ ВЕРЕН\n------------------------");
                                    System.out.println(question1.getCorrectAnswerInfo()+"\n------------------------");
                                }
                            }
                        }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (MistakeException c) {
                System.out.println("------------------------\nНЕПРАВИЛЬНЫЙ ОТВЕТ, ИГРА ЗАКОНЧЕНА\n------------------------");
                SoundController.playSound(GAME_OVER_SOUND).join();
            }
    }
        private void showRating ( int id) throws SQLException, ItemNotFoundException {
            int score = userRepository.getUserScore(id);
            System.out.println("ВАШ РЕЙТИНГ: " + score);
        }
    }
