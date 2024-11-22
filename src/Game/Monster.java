package Game;

public class Monster {
    private int hp;
    public Monster(int randHP){
        this.hp = randHP;
    }
    public int getHP(){
        return this.hp;
    }
    public int takeDamage(int damageTaken){
        this.hp-= damageTaken;
        return damageTaken;
    }
    public boolean isAlive(){
        if(this.hp>0){
            return true;
        }else{
            return false;
        }
    }
}
