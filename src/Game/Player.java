package Game;

import java.util.Random;

public class Player extends Monster{
    private int potionLeft;
    public Player(int potionLeft){
        super(new Random().nextInt(300));
        this.potionLeft = potionLeft;
    }

    public int getPotionLeft(){
        return this.potionLeft;
    }
    
    public int[] attackMonster(Monster monster){
        Random rand = new Random();
        //calcolo i danni sostenuti dal player
        int playerLoss = rand.nextInt(this.getHP())+1;
        //calcolo i danni sostenuti dal mostro
        int monsterLoss = rand.nextInt(monster.getHP())+1;
        //infliggo i danni al player
        this.takeDamage(playerLoss);
        //infliggo i danni al mostro
        monster.takeDamage(monsterLoss);
        //memorizo i danni per ritornarli
        int[] globalLoss = {playerLoss,monsterLoss};
        return globalLoss;
    }

    public int drinkPotion(){
        int randValue = new Random().nextInt(this.potionLeft);
        this.takeDamage(-randValue);
        this.potionLeft-=randValue;
        return randValue;
    }
}
