package dev.ooad.view;

/**
 * Basic interface for handling selection from menu and gathering input from users.
 */
public interface Selectable {

    enum MenuChoice {
        REGISTER,
        LIST_MINIMAL,
        LIST_FULL,
        INDIVIDUAL,
        DELETE_MEMBER,
        CHANGE_MEMBER,
        BOAT,
        ADD_BOAT,
        CHANGE_BOAT,
        DELETE_BOAT,
        EXIT,
        ERROR
    }

    MenuChoice getMainMenuChoice();
    MenuChoice getIndividualMemberMenuChoice();
    MenuChoice getBoatMenuChoice();
    String getInputString();
    String gatherMemberFirstName(boolean newMember);
    String gatherMemberLastName(boolean newMember);
    long gatherMemberPersNr(boolean newMember);
    int gatherBoatToChange();
    long getInputNumber();
    double getBoatLength();
}
