package dev.ooad;

import dev.ooad.controller.MenuController;
import dev.ooad.view.Console;
import dev.ooad.view.Viewable;

public class Main {

    public static void main(String[] args) {
	    Viewable view = new Console();
        MenuController controller = new MenuController(view);

        controller.handleMainMenu();
    }
}
