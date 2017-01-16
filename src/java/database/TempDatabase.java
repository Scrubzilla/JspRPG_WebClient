/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.util.ArrayList;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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
            //sendRegisterConfirmMail(email, username);
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

    public boolean accountHasCharacter(String accountName) {
        boolean result = false;

        for (int i = 0; i < characters.size(); i++) {
            if (accountName.equals(characters.get(i).getAccountName())) {
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

    private boolean sendRegisterConfirmMail(String recipient, String username){
        System.out.println("Sent confirmation email!");
        return mailSender(recipient, "Registration was successfull for the account " + username + ".\n\nYou can now login on the website http://localhost:8080/JspRPG_WebClient/Login.jsp \n\nPlease enjoy your time in the world of JspRPG!", "JspRPG Registration complete!") == true;
    }
    
    public boolean sendPasswordResetMail(String recipient){
        for(int i = 0; i < accounts.size(); i++){
            if(recipient.equals(accounts.get(i).getEmail())){
                System.out.println("Sent reset email!");
                return mailSender(recipient, "Hi there!\n\nSomeone(hopefully you) have requested a password retrieval for the account: " + accounts.get(i).getUsername() + ".\n\nIf this was intentional, please visit the link: http://localhost:8080/JspRPG_WebClient/PasswordReset.jsp?response=" + accounts.get(i).getUsername() + " \n\nThere you will be able to reset it.","JspRPG Forgotten password");
            }
        }
        
        return false;
    }

    private boolean mailSender(String recipient, String emailMessage, String emailSubject) {
        try {
            String username = "scruburuzilla@gmail.com";
            String password = "mysecretpassword";

            Properties props = new Properties();

            //This properties are valid for gmail.  You need to check for other mail providers/servers/agents.
            props.setProperty("mail.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.port", "587");
            props.setProperty("mail.smtp.auth", "true");
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
            System.out.println("So far so good -1 setting up session parameters");
            
            //Authentication is performed here.  
            SMTPAuthenticator auth = new SMTPAuthenticator(username, password);
            System.out.println("So far so good-2 authenticator called....");

            //The mail session is instantiated
            //The rest is copied from Java Mail documentation.
            Session mailConnection = Session.getInstance(props, auth);
            javax.mail.Message msg = new MimeMessage(mailConnection);
            System.out.println("So far so good-3 creating MIME message.....");
            Address sender = new InternetAddress(username, "Vehicle Insurance Company");
            Address receiver = new InternetAddress(recipient);
            Session session = Session.getInstance(props);
            msg.setContent(emailMessage, "text/plain");
            msg.setFrom(sender);
            msg.setRecipient(javax.mail.Message.RecipientType.TO, receiver);
            msg.setSubject(emailSubject);
            System.out.println("So far so good-4 ... finishing mail setup");

            Transport transport = session.getTransport("smtp");
            transport.connect(username, password);
            System.out.println("So far so good-5  connecting to server.....");

            Transport.send(msg);
            System.out.println("Message successfully sent");
            return true;        //Time to celebrate IF everything went fine upto this point.
        } catch (MessagingException e) {
            System.out.printf("Messaging Exception: " + e.getMessage());
//          throw new RuntimeException(e);
        } catch (Exception ex) {
            System.out.printf("General Exception: ");
            ex.printStackTrace();
        }
        return false;       //OOPS! something went terribly wrong.  Check the above exception messages!!
    }

}
