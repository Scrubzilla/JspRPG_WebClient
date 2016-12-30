/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Nicklas
 */
public class TempDatabase {

    private static TempDatabase tempDatabase;
    private ArrayList<Account> accounts = new ArrayList<>();

    private TempDatabase() {

    }

    public static TempDatabase getInstance() {
        if (tempDatabase == null) {
            tempDatabase = new TempDatabase();
        }

        return tempDatabase;
    }

    public boolean login(String username, String password) {
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getUsername().equals(username) && accounts.get(i).getPassword().equals(password)) {
                return true;
            }
        }

        return false;
    }

    public String addAccount(String username, String password, String email, String sq, String sqanswer) {
        boolean checkUsername = false;
        boolean checkEmail = false;

        if (checkUsernames(username) == true && checkEmail(email) == true) {
            accounts.add(new Account(username, password, email, sq, sqanswer));
            return "Account was created successfully!.";
        } else if (checkUsernames(username) == false && checkEmail(email) == true) {
            return "The username that you have entered is not valid or is already in use, try another one!";
        } else if (checkUsernames(username) == true && checkEmail(email) == false) {
            return "The email that you have entered is not valid or is already in use, try another one!";
        } else {
            return "The username and email that you have entered is not valid or is already in use, try another one!";
        }
    }

    private boolean checkUsernames(String username) {
        boolean isNotUsed = false;
        boolean isValidLength = false;

        if (accounts.size() > 0) {
            for (int i = 0; i < accounts.size(); i++) {
                if (!accounts.get(i).getUsername().equals(username)) {
                    isNotUsed = true;

                } else {
                    isNotUsed = false;
                    break;
                }
            }
        } else {
            isNotUsed = true;
        }

        if (username.length() > 6 && username.length() < 20) {
            isValidLength = true;
        }

        if (isNotUsed == true && isValidLength == true) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkEmail(String email) {
        boolean isNotUsed = false;
        boolean isLegitEmail = true;

        if (accounts.size() > 0) {
            for (int i = 0; i < accounts.size(); i++) {
                if (!accounts.get(i).getEmail().equals(email)) {
                    isNotUsed = true;
                } else {
                    isNotUsed = false;
                    break;
                }
            }
        } else {
            isNotUsed = true;
        }

        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        
        if (matcher.matches() == true) {
            isLegitEmail = true;
        } else {
            isLegitEmail = false;
        }

        if (isNotUsed == true && isLegitEmail == true) {
            return true;
        } else {
            return false;
        }

    }

}
