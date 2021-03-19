package dev.ooad.view;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Console view for the Boat Club application.
 * Implements the Viewable interface.
 */
public class Console implements Viewable {
    Scanner scan = new Scanner(System.in);

    // gets and returns a number from the user
    public long getInputNumber() {
        long input = -1;

        while (input < 0) {
            try {
                input = scan.nextLong();
                if (input < 0) {
                    System.out.print("Number can't be negative. Please enter a valid number: ");
                }
            }
            catch (InputMismatchException err) {
                System.out.println("Please enter a valid number.");
                scan.nextLine();
            }
        }

        return input;
    }

    // gets and returns a String from the user
    public String getInputString() {
        return scan.next();
    }

    // gets and returns main menu choice
    public MenuChoice getMainMenuChoice() {
        long choice = getInputNumber();

            switch ((int) choice) {
                case 1:
                    return MenuChoice.REGISTER;
                case 2:
                    return MenuChoice.LIST_MINIMAL;
                case 3:
                    return MenuChoice.LIST_FULL;
                case 4:
                    return MenuChoice.INDIVIDUAL;
                case 5:
                    return MenuChoice.DELETE_MEMBER;
                case 0:
                    return MenuChoice.EXIT;
                default:
                    System.out.println("That is not a valid menu choice.");
                    return MenuChoice.ERROR;
            }
    }

    // displays the main menu
    public void displayMainMenu() {
        System.out.println("\n===========================");
        System.out.println("Boat Club - Member Registry");
        System.out.println("===========================");
        System.out.println("1. Register new member");
        System.out.println("2. List all members - Minimal");
        System.out.println("3. List all members - Full");
        System.out.println("4. View individual member - Select by ID");
        System.out.println("5. Remove member from boat club");
        System.out.println("0. Exit");
        System.out.print("==> ");
    }

    // displays the list of currently registered members (compact view)
    public void displayMembersMinimal(int memberID, String firstName, String lastName, int numberOfBoats) {
        System.out.println("Membership ID: " + memberID + " >> Name: " + firstName + " " + lastName +
                " >> Number of boats: " + numberOfBoats);
    }

    // displays the list of currently registered members (verbose view)
    public void displayMembersFull(int memberID, long persnr, String firstName, String lastName, ArrayList<String> boats) {
        System.out.println("Membership ID: " + memberID + " >> Personal Nr: " + persnr + " >> Name: "
                + firstName + " " + lastName + " >> Boats: " + boats.toString());
    }

    // displays that no members are registered
    public void displayNoMembers() {
        System.out.println("No members currently registered...");
    }

    // displays the removal prompt for removing members from the boat club
    public void displayRemoveMember() {
        System.out.print("Enter the membership ID of the member to remove: ");
    }

    // displays the selection prompt for viewing an individual member
    public void displaySelectMember() {
        System.out.println("===========================");
        System.out.print("Select a member by entering their ID: ");
    }

    // displays detailed member information for a specific member
    public void displayMemberInfo(int id, long persnr, String firstName, String lastName, ArrayList<String> boats) {
        System.out.println("\n===========================");
        System.out.println("        Member View");
        System.out.println("===========================");
        System.out.println("Membership ID: " + id + "\nPersonal number: " + persnr + "\nFirst name: " + firstName +
                "\nLast name: " + lastName + "\nBoats: ");

        for (String boat : boats) {
            System.out.println(">> " + boat);
        }
    }

    // displays the individual member menu
    public void displayIndividualMemberMenu() {
        System.out.println("===========================");
        System.out.println("1. Change member information");
        System.out.println("2. Remove member from boat club");
        System.out.println("3. Add, change or remove boat");
        System.out.println("0. Main Menu");
        System.out.print("==> ");
    }

    // gets and returns the menu choice for the individual member menu
    public MenuChoice getIndividualMemberMenuChoice() {
        long choice = getInputNumber();

        switch ((int) choice) {
            case 1:
                return MenuChoice.CHANGE_MEMBER;
            case 2:
                return MenuChoice.DELETE_MEMBER;
            case 3:
                return MenuChoice.BOAT;
            case 0:
                return MenuChoice.EXIT;
            default:
                System.out.println("That is not a valid menu choice.");
                return MenuChoice.ERROR;
        }
    }

    // displays the boat menu
    public void displayBoatMenu() {
        System.out.println("\n===========================");
        System.out.println("         Boat View");
        System.out.println("===========================");
        System.out.println("1. Add boat");
        System.out.println("2. Change boat");
        System.out.println("3. Remove boat");
        System.out.println("0. Main Menu");
        System.out.print("==> ");
    }

    // gets and returns the menu choice for the boat menu
    public MenuChoice getBoatMenuChoice() {
        long choice = getInputNumber();

        switch ((int) choice) {
            case 1:
                return MenuChoice.ADD_BOAT;
            case 2:
                return MenuChoice.CHANGE_BOAT;
            case 3:
                return MenuChoice.DELETE_BOAT;
            case 0:
                return MenuChoice.EXIT;
            default:
                System.out.println("That is not a valid menu choice.");
                return MenuChoice.ERROR;
        }
    }

    // displays either a success- or failure message
    public void displaySuccessOrFail(boolean success) {
        if (success) {
            System.out.println("Success!");
        }
        else {
            System.out.println("Something went wrong... Please try again");
        }
    }

    // displays the currently available boat types to the user and prompts a choice
    public void displayBoatTypes(ArrayList<String> availableBoatTypes) {
        availableBoatTypes.forEach(boat -> System.out.print(boat + " "));
        System.out.print("\nPlease enter the desired boat type: ");
    }

    // gets and returns boat length
    public double getBoatLength() {
        System.out.print("Enter boat length (ft.): ");

        double input = 0.0;

        while (input <= 0) {
            try {
                input = scan.nextDouble();
                if (input < 0) {
                    System.out.print("Number can't be negative. Please enter a valid length (ft.): ");
                }
            }
            catch (InputMismatchException err) {
                System.out.print("Please enter a valid length (ft.): ");
                scan.nextLine();
            }
        }

        return input;
    }

    // prompts, gets and returns an integer choice (boat to change)
    public int gatherBoatToChange() {
        System.out.print("Select boat to change (number): ");
        return (int) getInputNumber();
    }

    // displays a members currently registered boats, numbered
    public void displayMemberBoats(ArrayList<String> boats) {
        int position = 0;
        for (String boat : boats) {
            System.out.println("[" + position++ + "] " + boat);
        }
    }

    // displays a confirmation of removal
    public void displayConfirmRemoval() {
        System.out.println("\nRemoval successful!");
    }

    // displays that a member with passed in ID doesn't exist
    public void displayMemberDoesntExist(int id) {
        System.out.println("No member with ID " + id + " currently exists");
    }

    // if member already exists in the registry
    public void displayMemberAlreadyExists(long persnr) {
        System.out.println("A member with personal number " + persnr + " already exists in the registry");
    }

    // get and return personal number
    public long gatherMemberPersNr(boolean newMember) {
        if (newMember) {
            System.out.print("Enter Personal Number (10 digits): ");
        }
        else {
            System.out.print("Enter Personal Number, 10 digits ('0' to skip): ");
        }

        return getInputNumber();
    }

    // get and return first name
    public String gatherMemberFirstName(boolean newMember) {
        if (newMember) {
            System.out.print("Enter first name: ");
        }
        else {
            System.out.print("Enter first name ('0' to skip): ");
        }

        return getInputString();
    }

    // get and return last name
    public String gatherMemberLastName(boolean newMember) {
        if (newMember) {
            System.out.print("Enter last name: ");
        }
        else {
            System.out.print("Enter last name ('0' to skip): ");
        }

        return getInputString();
    }
}
