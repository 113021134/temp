package Models;

public class Ability {
    private Float strength;
    private Float endurance;
    private Float flexibility;
    private Integer speed;
    private Integer agility;
    private Integer balance;
    private Integer power;
    private String notes;

    Ability(){
        strength = null;
        endurance = null;
        flexibility = null;
        speed = null;
        agility = null;
        balance = null;
        power = null;
        notes = "None";
    }
    
    Ability(Float strength, Float endurance, Float flexibility, Integer speed, Integer agility, Integer balance, Integer power, String notes){
        this.strength = strength;
        this.endurance = endurance;
        this.flexibility = flexibility;
        this.speed = speed;
        this.agility = agility;
        this.balance = balance;
        this.power = power;
        this.notes = notes;
    }

    public Float getTotalScore(){
        Float totalScore = (strength + endurance + flexibility + speed + agility + balance + power) * 100 /35;
        return totalScore;
    }

    public Ability countScore(){
        return null;
    }

    public Integer getAgility() {
        return agility;
    }
    public Integer getBalance() {
        return balance;
    }
    public Float getEndurance() {
        return endurance;
    }
    public Float getFlexibility() {
        return flexibility;
    }
    public Integer getPower() {
        return power;
    }
    public Integer getSpeed() {
        return speed;
    }
    public Float getStrength() {
        return strength;
    }
    public String getNotes() {
        return notes;
    }

    public void setAgility(Integer agility) {
        this.agility = agility;
    }
    public void setBalance(Integer balance) {
        this.balance = balance;
    }
    public void setEndurance(Float endurance) {
        this.endurance = endurance;
    }
    public void setFlexibility(Float flexibility) {
        this.flexibility = flexibility;
    }
    public void setPower(Integer power) {
        this.power = power;
    }
    public void setSpeed(Integer speed) {
        this.speed = speed;
    }
    public void setStrength(Float strength) {
        this.strength = strength;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }    
}
