/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 *
 * @author Nicklas
 */
public class SMTPAuthenticator extends Authenticator { //The Authentication object has to be caast to a class that extends Authenticator.

    private PasswordAuthentication authentication;

    public SMTPAuthenticator(String login, String password) {
        authentication = new PasswordAuthentication(login, password);
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return authentication;
    }
}
