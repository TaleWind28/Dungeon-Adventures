package Game;

import java.util.Random;

public class GameFactory{
    public Monster createMonster(){
        Monster monster = new Monster(new Random().nextInt(200));
        return monster;
    }
    public Player createPlayer(){
        return new Player(new Random().nextInt(150));
    }
}