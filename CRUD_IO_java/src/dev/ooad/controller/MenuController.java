package dev.ooad.controller;

import dev.ooad.model.Boat;
import dev.ooad.model.Member;
import dev.ooad.view.Viewable;

import java.util.ArrayList;
import java.util.Set;

/**
 * Main controller for the Boat Club (Member registry) application.
 * Controls the main flow and delegates action to the view and MemberController.
 */
public class MenuController {

    private final Viewable view;
    private final MemberController memberController;

    public MenuController(Viewable view) {
        this.view = view;
        this.memberController = new MemberController();
    }

    /**
     * Handles the control flow of the "main menu" of the application.
     */
    public void handleMainMenu() {
        view.displayMainMenu();
        Viewable.MenuChoice choice = view.getMainMenuChoice();

        // as long as the user doesn't want to exit the application
        while (choice != Viewable.MenuChoice.EXIT) {

            // register new member
            if (choice == Viewable.MenuChoice.REGISTER) {
                boolean success = memberController.registerMember(view);
                view.displaySuccessOrFail(success);
            }
            // display (list) all members of the boat club, either compact or verbose
            else if (choice == Viewable.MenuChoice.LIST_MINIMAL || choice == Viewable.MenuChoice.LIST_FULL) {
                Set<Member> members = memberController.getAllMembers();

                // if no members are registered
                if (members.isEmpty()) {
                    view.displayNoMembers();
                }

                // if there are members registered at the boat club
                else {
                    if (choice == Viewable.MenuChoice.LIST_MINIMAL) {
                        members.forEach(m -> view.displayMembersMinimal(m.getId(), m.getFirstName(), m.getLastName(),
                                m.getNumberOfBoats()));
                    }
                    // verbose member list
                    else {
                        // handle each individual member in the registry
                        for (Member member : members) {

                            ArrayList<String> boatsToPrint = listMembersBoats(member);

                            view.displayMembersFull(member.getId(), member.getPersonalNumber(), member.getFirstName(),
                                    member.getLastName(), boatsToPrint);

                            boatsToPrint.clear();
                        }

                    }

                    handleIndividualMemberMenu();
                }
            }

            // switch control to the "individual member" menu where further action can be taken
            else if (choice == Viewable.MenuChoice.INDIVIDUAL) {
                handleIndividualMemberMenu();
            }
            // remove member from boat club
            else if (choice == Viewable.MenuChoice.DELETE_MEMBER) {
                view.displayRemoveMember();
                long id = view.getInputNumber();

                Member member = memberController.selectMemberByID((int) id);

                if (member != null) {
                    memberController.deleteMemberByID((int) id);
                    view.displayConfirmRemoval();
                }
                else {
                    view.displayMemberDoesntExist((int) id);
                }
            }

            view.displayMainMenu();
            choice = view.getMainMenuChoice();
        }

        // user has chosen to exit the application, handle gracefully by saving the registry first
        memberController.saveRegistry();
        System.exit(0);
    }

    /**
     * Handles the control flow of the "individual member menu" of the application.
     */
    public void handleIndividualMemberMenu() {
        view.displaySelectMember();
        long id = view.getInputNumber();

        Member member = memberController.selectMemberByID((int) id);

        // if member exists, otherwise return
        if (member != null) {
            ArrayList<String> boatsToPrint = listMembersBoats(member);

            // displays detailed member information about the chosen member
            view.displayMemberInfo(member.getId(), member.getPersonalNumber(), member.getFirstName(),
                    member.getLastName(), boatsToPrint);

            view.displayIndividualMemberMenu();

            Viewable.MenuChoice choice = view.getIndividualMemberMenuChoice();

            // as long as the user doesn't want to exit this sub-menu (individual member section)
            while (choice != Viewable.MenuChoice.EXIT) {

                // change information about this specific member
                if (choice == Viewable.MenuChoice.CHANGE_MEMBER) {
                    memberController.changeMemberInfo(view, member);
                    break;
                }
                // delete this specific member
                else if (choice == Viewable.MenuChoice.DELETE_MEMBER) {
                    memberController.deleteMemberByID((int) id);
                    view.displayConfirmRemoval();
                    break;
                }
                // access this members boats to either add, update or delete
                else if (choice == Viewable.MenuChoice.BOAT) {
                    handleBoatMenu(member);
                    break;
                }

                view.displayIndividualMemberMenu();
                choice = view.getIndividualMemberMenuChoice();
            }
        }
        else {
            view.displayMemberDoesntExist((int) id);
        }
    }

    /**
     * Handles the control flow of the "boat menu" for a specific member.
     * @param member - the member whose boats to manage
     */
    public void handleBoatMenu(Member member) {
        view.displayBoatMenu();

        Viewable.MenuChoice choice = view.getBoatMenuChoice();

        // as long as the user doesn't want to exit this sub-menu (boat section)
        while (choice != Viewable.MenuChoice.EXIT) {

            // add a new boat to this member
            if (choice == Viewable.MenuChoice.ADD_BOAT) {
                boolean success = memberController.addBoat(view, member);
                view.displaySuccessOrFail(success);
            }
            // change the information of a boat for this member
            else if (choice == Viewable.MenuChoice.CHANGE_BOAT) {
                ArrayList<String> boatsToPrint = listMembersBoats(member);
                view.displayMemberBoats(boatsToPrint);
                boolean success = memberController.changeBoatInfo(view, member);
                view.displaySuccessOrFail(success);
            }
            // remove a boat from this member
            else if (choice == Viewable.MenuChoice.DELETE_BOAT) {
                ArrayList<String> boatsToPrint = listMembersBoats(member);
                view.displayMemberBoats(boatsToPrint);
                boolean success = memberController.deleteBoat(view, member);
                view.displaySuccessOrFail(success);
            }

            view.displayBoatMenu();
            choice = view.getBoatMenuChoice();
        }
    }

    // helper method to put together a list of, and format, a specific members boats
    private ArrayList<String> listMembersBoats(Member member) {
        ArrayList<String> boatsToPrint = new ArrayList<>();

        for (Boat boat : member.getBoats()) {
            String boatFormat = boat.getType() + ": " + boat.getLength() + " ft.";
            boatsToPrint.add(boatFormat);
        }

        return boatsToPrint;
    }
}
