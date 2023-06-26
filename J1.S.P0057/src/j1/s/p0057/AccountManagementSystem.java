/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package j1.s.p0057;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author ASUS
 */
public class AccountManagementSystem {
     private List<User> users;
    private static final String FILE_NAME = "user.dat";

    public AccountManagementSystem() throws IOException {
        this.users = new ArrayList<>();
        loadUsersFromFile();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            displayMenu();
            choice = getIntInput(scanner, "Enter your choice: ");

            switch (choice) {
                case 1:
                    createNewAccount(scanner);
                    break;
                case 2:
                    loginSystem(scanner);
                    break;
                case 3:
                    System.out.println("Exiting the program.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }

            System.out.println();
        } while (choice != 3);
    }

    private void displayMenu() {
        System.out.println("Menu:");
        System.out.println("1. Create a new account");
        System.out.println("2. Login System");
        System.out.println("3. Exit");
    }

    private void createNewAccount(Scanner scanner) {
        System.out.println("Create a new account");
        String username = getStringInput(scanner, "Enter username: ");
        String password = getStringInput(scanner, "Enter password: ");

        if (username.length() < 5 || username.contains(" ")) {
            System.out.println("Invalid username. Username must be at least 5 characters and no spaces.");
            return;
        }

        if (password.length() < 6 || password.contains(" ")) {
            System.out.println("Invalid password. Password must be at least 6 characters and no spaces.");
            return;
        }

        for (User user : users) {
            if (user.getUsername().equals(username)) {
                System.out.println("Username already exists. Please choose a different username.");
                return;
            }
        }

        User newUser = new User(username, password);
        users.add(newUser);
        saveUsersToFile();

        System.out.println("Account created successfully.");
    }

    private void loginSystem(Scanner scanner) {
        System.out.println("Login System");
        String username = getStringInput(scanner, "Enter username: ");
        String password = getStringInput(scanner, "Enter password: ");

        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                System.out.println("Login successful.");
                return;
            }
        }

        System.out.println("Invalid username or password.");
    }

    private void loadUsersFromFile() throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            users = (List<User>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No existing user file found.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading user file.");
        }
    }

    private void saveUsersToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(users);
        } catch (IOException e) {
            System.out.println("Error saving user file.");
        }
    }

    private String getStringInput(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private int getIntInput(Scanner scanner, String prompt) {
        int choice = -1;
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.print(prompt);
                choice = Integer.parseInt(scanner.nextLine());
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }

        return choice;
    }
}
