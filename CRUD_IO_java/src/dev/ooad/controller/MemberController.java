package dev.ooad.controller;

import dev.ooad.model.Boat;
import dev.ooad.model.Member;
import dev.ooad.model.services.MemberService;
import dev.ooad.view.Viewable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

/**
 * Controller responsible for actions regarding members and their boats.
 * Gets delegated responsibility from the main controller and interacts with the model (MemberService).
 */
public class MemberController {

    private final MemberService memberService;

    public MemberController() {
        this.memberService = new MemberService();
    }

    /**
     * Register a new member.
     * @param view - implements the Viewable interface
     * @return - if successful or not
     */
    public boolean registerMember(Viewable view) {
        Member newMember = new Member();
        long pnr = view.gatherMemberPersNr(true);

        boolean validPnr = newMember.setPersonalNumber(pnr);
        if (!validPnr) return false;

        String firstName = view.gatherMemberFirstName(true);
        boolean validFirstName = newMember.setFirstName(firstName);
        if (!validFirstName) return false;

        String lastName = view.gatherMemberLastName(true);
        boolean validLastName = newMember.setLastName(lastName);
        if (!validLastName) return false;

        Member member = new Member(pnr, firstName, lastName);
        Member saved = memberService.save(member);

        if (saved == null) {
            view.displayMemberAlreadyExists(pnr);
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * Retrieves all currently registered/stored members.
     * @return - HashSet containing the member objects
     */
    public Set<Member> getAllMembers() {
        return memberService.findAll();
    }

    /**
     * Delete a member from the registry/storage.
     * @param id - the membership ID of the member to remove
     */
    public void deleteMemberByID(Integer id) {
        memberService.deleteById(id);
    }

    /**
     * Finds and returns a member with the specified membership ID.
     * @param id - Integer
     * @return - the member object
     */
    public Member selectMemberByID(Integer id) {
        return memberService.findById(id);
    }

    /**
     * Updates the member information of the specified member if input is valid.
     * Skips the field if:
     * - Pnr is not valid
     * - First name and/or Last name is numerical
     * @param view - implements the Viewable interface
     * @param member - member object to edit
     */
    public void changeMemberInfo(Viewable view, Member member) {
        long newPersnr = view.gatherMemberPersNr(false);

        // personal numbers should be unique
        if (memberService.memberPersnrAlreadyExists(newPersnr)) {
            view.displayMemberAlreadyExists(newPersnr);
            return;
        }
        else {
            member.setPersonalNumber(newPersnr);
        }

        String newFirstName = view.gatherMemberFirstName(false);
        member.setFirstName(newFirstName);

        String newLastName = view.gatherMemberLastName(false);
        member.setLastName(newLastName);

        memberService.update(member);
    }

    /**
     * Adds a new boat to the specified member.
     * @param view - implements the Viewable interface
     * @param member - the member to which a new boat should be added
     * @return - if successful or not
     */
    public boolean addBoat(Viewable view, Member member) {
        return addOrUpdateBoat(view, member);
    }

    /**
     * Change/edit the information of a boat.
     * @param view- implements the Viewable interface
     * @param member- the member whose boat should have its information changed
     * @return - if successful or not
     */
    public boolean changeBoatInfo(Viewable view, Member member) {
        // gets the position of the boat to change in the members boat list
        int position = view.gatherBoatToChange();

        boolean legal = (position >= 0) && (position < member.getBoats().size());

        if (!legal) return false;

        boolean success = addOrUpdateBoat(view, member);

        if (success) {
            member.removeBoatByIndex(position);
            memberService.update(member);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Delete/remove a boat from a specific member.
     * @param view - implements the Viewable interface
     * @param member - the member whose boat should be removed
     * @return - if successful or not
     */
    public boolean deleteBoat(Viewable view, Member member) {
        int position = view.gatherBoatToChange();

        boolean legal = member.removeBoatByIndex(position);

        if (!legal) {
            return false;
        }
        else {
            memberService.update(member);
            return true;
        }
    }

    /**
     * Saves the registry to persistence.
     */
    public void saveRegistry() {
        memberService.registryToStorage();
    }

    // private method that displays available boat types and validates information in order to add or update a members boat
    private boolean addOrUpdateBoat(Viewable view, Member member) {
        ArrayList<String> availableBoatTypes = new ArrayList<>();

        for (Boat.BoatType value : Boat.BoatType.values()) {
            availableBoatTypes.add(value.toString());
        }

        view.displayBoatTypes(availableBoatTypes);
        String boatType = view.getInputString();

        // validates the entered boat type
        boolean validBoatType = Arrays.stream(Boat.BoatType.values()).anyMatch(type -> type.toString().equalsIgnoreCase(boatType));

        // return false if the entered value is not a valid boat type
        if (!validBoatType) {
            return false;
        }
        else {
            double lengthToAdd = view.getBoatLength();
            Boat.BoatType typeToAdd = null;

            // get the type to add
            for (Boat.BoatType value : Boat.BoatType.values()) {
                if (value.toString().equalsIgnoreCase(boatType)) {
                    typeToAdd = value;
                }
            }

            // create a new boat object of the desired type and length, add it to the member and update the registry
            if (typeToAdd != null) {
                Boat boatToAdd = new Boat(typeToAdd, lengthToAdd);
                member.addBoat(boatToAdd);
                memberService.update(member);
                return true;
            }
            else {
                return false;
            }
        }
    }
}
