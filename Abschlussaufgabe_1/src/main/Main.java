package main;

import userinterface.Session;

/**
 * Main-Class. Just initiates a Session and executes the run() method.
 * 
 * @author Christopher Roth
 * @version 1.0
 */
class Main {
    /**
     * The main method.
     * 
     * @param args arguments which this Java-Program does not handle any further.
     */
    public static void main(String[] args) {

        Session session = new Session();

        session.run();

    }
}
