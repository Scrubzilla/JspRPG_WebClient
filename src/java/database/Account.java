/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

/**
 *
 * @author Nicklas
 */
public class Account {

    String username;
    String password;
    String email;
    boolean isPremium;
    String sq;
    String sqanswer;
    String character;
    int accountLevel;

    public Account(String username, String password, String email, boolean isPremium, String sq, String sqanswer, int accountLevel) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.isPremium = isPremium;
        this.sq = sq;
        this.sqanswer = sqanswer;
        this.accountLevel = accountLevel;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;

    }

    public boolean isIsPremium() {
        return isPremium;
    }

    public void setIsPremium(boolean isPremium) {
        this.isPremium = isPremium;
    }

    public String getSq() {
        return sq;
    }

    public void setSq(String sq) {
        this.sq = sq;
    }

    public String getSqanswer() {
        return sqanswer;
    }

    public void setSqanswer(String sqanswer) {
        this.sqanswer = sqanswer;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public int getAccountLevel() {
        return accountLevel;
    }

    public void setAccountLevel(int accountLevel) {
        this.accountLevel = accountLevel;
    }

    
}
