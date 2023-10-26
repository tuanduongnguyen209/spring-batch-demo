package duongnt.example.springbatchdemo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Student {
    @Id
    private int id;
    @Column
    private String name;
    @Column
    private int mathScore;
    @Column
    private int physicsScore;
    @Column
    private int chemistryScore;
    @Column
    private int averageScore;

    public Student() {
    }

    public Student(int id, String name, int mathScore, int physicsScore, int chemistryScore) {
        this.id = id;
        this.name = name;
        this.mathScore = mathScore;
        this.physicsScore = physicsScore;
        this.chemistryScore = chemistryScore;
    }

    @Override
    public String toString() {
        return "Student [id=" + id + ",name=" + name + ", mathScore=" + mathScore + ", physicsScore=" + physicsScore + ", chemistryScore=" + chemistryScore + "]";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMathScore() {
        return mathScore;
    }

    public void setMathScore(int mathScore) {
        this.mathScore = mathScore;
    }

    public int getPhysicsScore() {
        return physicsScore;
    }

    public void setPhysicsScore(int physicsScore) {
        this.physicsScore = physicsScore;
    }

    public int getChemistryScore() {
        return chemistryScore;
    }

    public void setChemistryScore(int chemistryScore) {
        this.chemistryScore = chemistryScore;
    }

    public int getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(int averageScore) {
        this.averageScore = averageScore;
    }
}
