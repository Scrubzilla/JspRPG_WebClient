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
    private ArrayList<Character> characters = new ArrayList<>();

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

    public String addAccount(String username, String password, String email, String sq, String sqanswer, boolean isPremium) {
        boolean checkUsername = false;
        boolean checkEmail = false;

        if (checkUsernames(username) == true && checkEmail(email) == true) {
            accounts.add(new Account(username, password, email, isPremium, sq, sqanswer, 0));
            return "Account was created successfully!.";
        } else if (checkUsernames(username) == false && checkEmail(email) == true) {
            return "The username that you have entered is not valid or is already in use, try another one!";
        } else if (checkUsernames(username) == true && checkEmail(email) == false) {
            return "The email that you have entered is not valid or is already in use, try another one!";
        } else {
            return "The username and email that you have entered is not valid or is already in use, try another one!";
        }
    }

    public String addCharacter(String accountName, String name, int str, int dex, int vit, int inte, int wis, int cha) {
        boolean isNotUsed = false;
        boolean isValidLength = false;
        boolean containsNumbers = false;

        if (characters.size() > 0) {
            for (int i = 0; i < characters.size(); i++) {
                if (!characters.get(i).getName().equals(name)) {
                    isNotUsed = true;
                } else {
                    isNotUsed = false;
                    break;
                }
            }
        } else {
            isNotUsed = true;
        }

        if (name.length() >= 5 && name.length() <= 15) {
            isValidLength = true;
        }

        if (name.matches(".*\\d.*")) {
            System.out.println("Contains a number!");
            containsNumbers = true;
        } else {
            System.out.println("Does not contain a number!");
            containsNumbers = false;
        }

        if (isNotUsed == false) {
            return "The username is already in use, try another one!";
        } else if (isValidLength == false) {
            return "The username does not have a valid length, please enter another one!";
        } else if (containsNumbers == true) {
            return "The username contains numbers which is not allowed!!";
        } else if (isNotUsed == true && isValidLength == true && containsNumbers == false) {
            characters.add(new Character(accountName, name, 0, 0, 0, str, dex, vit, inte, wis, cha));
            
            for (int i = 0; i < characters.size(); i++) {
                System.out.println(characters.get(i).getName());
            }
            
            return "Character was created successfully!";

        } else {
            return "Something went wrong!";
        }
    }
    
    public boolean accountHasCharacter(String accountName){
        boolean result = false;
        
        for(int i = 0; i < characters.size(); i++){
            if(accountName.equals(characters.get(i).getAccountName())){
                result = true;
                break;
            }
        }
        
        return result;
    }

    public String changeEmail(String username, String oldEmail, String newEmail, String sqAnswer) {
        for (int i = 0; i < accounts.size(); i++) {
            if (username.equals(accounts.get(i).getUsername())) {
                if (oldEmail.equals(accounts.get(i).getEmail()) && sqAnswer.equals(accounts.get(i).getSqanswer())) {
                    accounts.get(i).setEmail(newEmail);
                    return "Email was changed successfully!";
                } else {
                    return "The information provided was incorrect, try again!";
                }
            }
        }
        return "Something went wrong!";
    }

    public String changePassword(String username, String oldPassword, String newPassword) {
        for (int i = 0; i < accounts.size(); i++) {
            if (username.equals(accounts.get(i).getUsername())) {
                if (oldPassword.equals(accounts.get(i).getPassword())) {
                    accounts.get(i).setPassword(newPassword);
                    return "Password was changed successfully!";
                } else {
                    return "The entered password was incorrect, try again!";
                }

            }
        }

        return "Something went wrong!";
    }

    public String getSecretQuestion(String username) {
        for (int i = 0; i < accounts.size(); i++) {
            if (username.equals(accounts.get(i).getUsername())) {
                return accounts.get(i).getSq();
            }
        }

        return "Something went wrong!";
    }

    public boolean getIsPremium(String username) {
        boolean result = false;

        for (int i = 0; i < accounts.size(); i++) {
            if (username.equals(accounts.get(i).getUsername())) {
                result = accounts.get(i).isIsPremium();
                break;
            }
        }

        return result;
    }

    public int getUserRole(String username) {
        int result = 0;

        for (int i = 0; i < accounts.size(); i++) {
            if (username.equals(accounts.get(i).getUsername())) {
                result = accounts.get(i).getAccountLevel();
                break;
            }
        }

        return result;
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
