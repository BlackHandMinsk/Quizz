package Quizz;

import Quizz.MenuController.MenuController;
import Quizz.MenuController.SoundController;

public class Main {
    public static void main(String[] args) {
        new MenuController().start();
        SoundController.playSound("C:\\Users\\37544\\IdeaProjects\\Quizz\\src\\main\\resources\\1128.wav").play();
    }
}
