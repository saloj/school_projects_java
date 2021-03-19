package dev.ooad.view;

import java.util.ArrayList;

/**
 * Interface for displaying content regarding the Boat club.
 * Extends the Selectable interface in order to handle input and choices.
 */
public interface Viewable extends Selectable {

    void displayMainMenu();
    void displayNoMembers();
    void displayMembersMinimal(int memberID, String firstName, String lastName, int numberOfBoats);
    void displayMembersFull(int memberID, long persnr, String firstName, String lastName, ArrayList<String> boats);
    void displayRemoveMember();
    void displaySelectMember();
    void displayIndividualMemberMenu();
    void displayMemberInfo(int id, long persnr, String firstName, String lastName, ArrayList<String> boats);
    void displayConfirmRemoval();
    void displayMemberDoesntExist(int id);
    void displayMemberAlreadyExists(long persnr);
    void displayBoatMenu();
    void displayBoatTypes(ArrayList<String> availableBoatTypes);
    void displayMemberBoats(ArrayList<String> boats);
    void displaySuccessOrFail(boolean success);
}
