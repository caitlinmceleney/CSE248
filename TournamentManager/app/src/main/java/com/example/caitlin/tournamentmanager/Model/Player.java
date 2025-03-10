package com.example.caitlin.tournamentmanager.Model;

public class Player {
    private String firstName;
    private String lastName;
    private String age;

    public Player(String firstName, String lastName, String age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }
    public Player(){

    }

    public String getFirstName() {
        return firstName;
    }

    public void editFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void editLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + ",  " + age;
    }
}
