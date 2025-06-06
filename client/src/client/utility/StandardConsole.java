package client.utility;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class StandardConsole implements Console {
    private static final String P1 = "$ ";
    private static Scanner fileScanner = null;
    private static Scanner defScanner = new Scanner(System.in);

    @Override
    public void print(Object obj) {
        System.out.print(obj);
    }

    @Override
    public void println(Object obj) {
        System.out.println(obj);
    }

    @Override
    public void printError(Object obj) {
        System.err.println("Error: " + obj);
    }

    @Override
    public String readln() throws NoSuchElementException, IllegalStateException {
        return (fileScanner != null ? fileScanner : defScanner).nextLine();
    }

    @Override
    public boolean isCanReadln() throws IllegalStateException {
        return (fileScanner != null ? fileScanner : defScanner).hasNextLine();
    }

    @Override
    public void prompt() {
        print(P1);
    }

    @Override
    public String getPrompt() {
        return P1;
    }

    @Override
    public void selectFileScanner(Scanner scanner) {
        fileScanner = scanner;
    }

    @Override
    public void selectConsoleScanner() {
        fileScanner = null;
    }
}