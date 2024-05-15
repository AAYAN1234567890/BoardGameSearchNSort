/**
 * Title: Board Game Search N Sort
 * Author: Aayan Samdani
 * Date: May 14, 2024
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to the Board Game Collection Manager!\n");

        // Input: Load board games from CSV file into an ArrayList
        ArrayList<String> boardGames = new ArrayList<>();
        loadFromCSV(boardGames, "board_games.csv");

        // Process: Main menu loop to handle user interactions
        while (true) {
            displayMenu();
            int choice = getUserChoice();
            
            switch (choice) {
                case 1:
                    addBoardGame(boardGames);
                    break;
                case 2:
                    printAlphabetically(boardGames);
                    break;
                case 3:
                    printDifficulty(boardGames);
                    break;
                case 4:
                    printGenre(boardGames);
                    break;
                case 5:
                    printTime(boardGames);
                    break;
                case 6:
                    saveToCSV(boardGames, "board_games.csv");
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Input: Load board games from a CSV file
    private static void loadFromCSV(ArrayList<String> collection, String filename) {
        try (BufferedReader fileReader = new BufferedReader(new FileReader(filename))) {
            // Skip the header line
            fileReader.readLine();

            String line;
            while ((line = fileReader.readLine()) != null) {
                // Ensure the line is not empty and contains the correct number of elements
                if (line.trim().length() > 0 && line.split(",").length >= 7) {
                    collection.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading from CSV file: " + e.getMessage());
        }
    }

    // Input: Get the user's choice and handle invalid input
    private static int getUserChoice() {
        int choice = -1;
        while (true) {
            try {
                choice = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
        return choice;
    }

    // Input: Add a new board game to the collection
    private static void addBoardGame(ArrayList<String> collection) {
        try {
            System.out.print("Enter the title of the game to add: ");
            String title = scanner.nextLine();
            System.out.print("Enter the rating of the game: ");
            double rating = Double.parseDouble(scanner.nextLine());
            System.out.print("Enter the difficulty of the game: ");
            double difficulty = Double.parseDouble(scanner.nextLine());
            System.out.print("Enter the number of players: ");
            int players = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter the time to play (in minutes): ");
            int time = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter the year of release: ");
            int year = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter the genre of the game: ");
            String genre = scanner.nextLine();

            String newGame = String.format("%s,%.5f,%.5f,%d,%d,%d,%s",
                    title, rating, difficulty, players, time, year, genre);
            collection.add(newGame);
            System.out.println("Game added to the collection!");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter the correct data type for each field.");
        }
    }

    // Process: Display the menu options to the user
    private static void displayMenu() {
        System.out.println("\nWhat would you like to do:");
        System.out.println("1. Add a game");
        System.out.println("2. Print games alphabetically");
        System.out.println("3. Print games based on difficulty");
        System.out.println("4. Print games based on genre");
        System.out.println("5. Print games based on time");
        System.out.println("6. Save and exit");
        System.out.print("Enter your choice: ");
    }

    // Process: Print board games alphabetically
    private static void printAlphabetically(ArrayList<String> collection) {
        bubbleSort(collection, 0);
        System.out.println("\nGames in alphabetical order: ");
        wait(2);
        collection.forEach(Main::printFormattedBoardGame);
    }

    // Process: Print board games by difficulty
    private static void printDifficulty(ArrayList<String> collection) {
        selectionSort(collection, 2, true);
        System.out.println("\nGames sorted by difficulty:");
        wait(2);
        collection.forEach(Main::printFormattedBoardGame);
    }

    // Process: Print board games by genre
    private static void printGenre(ArrayList<String> collection) {
        bubbleSort(collection, 6);
        System.out.println("\nGames sorted by genre:");
        wait(2);
        collection.forEach(Main::printFormattedBoardGame);
    }

    // Process: Print board games by play time
    private static void printTime(ArrayList<String> collection) {
        selectionSort(collection, 4, false);
        System.out.println("\nGames sorted by time:");
        wait(2);
        collection.forEach(Main::printFormattedBoardGame);
    }

    // Output: Save board games to a CSV file
    private static void saveToCSV(ArrayList<String> collection, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            for (String game : collection) {
                writer.write(game + "\n");
            }
            System.out.println("Collection saved to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving to CSV file: " + e.getMessage());
        }
    }

    // Sorting: Bubble sort by a specified index
    private static void bubbleSort(ArrayList<String> collection, int index) {
        int n = collection.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (collection.get(j).split(",")[index].toLowerCase()
                        .compareTo(collection.get(j + 1).split(",")[index].toLowerCase()) > 0) {
                    // Swap collection[j+1] and collection[j]
                    String temp = collection.get(j);
                    collection.set(j, collection.get(j + 1));
                    collection.set(j + 1, temp);
                }
            }
        }
    }

    // Sorting: Selection sort by a specified index and data type - if statements from Chat GPT
    private static void selectionSort(ArrayList<String> collection, int index, boolean isDouble) {
        int n = collection.size();
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (isDouble) {
                    if (Double.parseDouble(collection.get(j).split(",")[index])
                            < Double.parseDouble(collection.get(minIdx).split(",")[index])) {
                        minIdx = j;
                    }
                } else {
                    if (Integer.parseInt(collection.get(j).split(",")[index])
                            < Integer.parseInt(collection.get(minIdx).split(",")[index])) {
                        minIdx = j;
                    }
                }
            }
            // Swap the found minimum element with the first element
            String temp = collection.get(minIdx);
            collection.set(minIdx, collection.get(i));
            collection.set(i, temp);
        }
    }

    // Output: Print a formatted board game string
    private static void printFormattedBoardGame(String game) {
        String[] attributes = game.split(",");
        if (attributes.length >= 7) { // Used Chat GPT for formatting purposes
            System.out.printf("%-50s | Rating: %.2f | Difficulty: %.2f | Players: %3d | Time: %4d | Year: %d | Genre: %s\n",
                    attributes[0], Double.parseDouble(attributes[1]), Double.parseDouble(attributes[2]),
                    Integer.parseInt(attributes[3]), Integer.parseInt(attributes[4]),
                    Integer.parseInt(attributes[5]), attributes[6]);
        } else {
            System.out.println("Invalid data format for game: " + game);
        }
    }

    // Utility: Pause execution for a specified number of seconds
    private static void wait(int sec) {
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
