package yahtzeegame;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.Random;

public class Yahtzee extends Application {

    private int count = 0;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Yahtzee");

        // generate the array of images to use for rendering the dice
        Image[] dice = { new Image("yahtzeegame/1.png"),
                new Image("yahtzeegame/2.png"), new Image("yahtzeegame/3.png"),
                new Image("yahtzeegame/4.png"), new Image("yahtzeegame/5.png"),
                new Image("yahtzeegame/6.png")};

        // set the ImageView properties we desire and then populate the array with a set of starting dice
        ImageView[] images = new ImageView[5];
        for (int i = 0; i < images.length; i++) {
            images[i] = new ImageView();
            images[i].setFitWidth(80);
            images[i].setPreserveRatio(true);
            images[i].setSmooth(true);
        }

        for (int i = 0; i < 5; i++) {
            images[i].setImage(dice[i]);
        }

        // create the skeleton for our grid structure
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(12, 12, 14, 15));
        pane.setHgap(6);
        pane.setVgap(6);

        // create the heading and allow it to span across the columns
        final Label heading = new Label("Yahtzee");
        heading.setFont(new Font("Helvetica", 50));
        pane.add(heading, 0, 0, 2, 1);

        // render the images
        HBox picStorage = new HBox(20);
        picStorage.getChildren().addAll(images[0], images[1], images[2], images[3], images[4]);
        pane.add(picStorage, 0, 1, 6, 1);

        // generate the checkboxes and initialize them as disabled
        CheckBox[] boxes = new CheckBox[5];
        for (int i = 0; i < 5; i++) {
            boxes[i] = new CheckBox();
            boxes[i].setDisable(true);
        }

        // align the checkboxes below the dice images
        HBox checkboxes = new HBox(85);
        checkboxes.getChildren().addAll(boxes[0], boxes[1], boxes[2], boxes[3], boxes[4]);
        HBox.setMargin(boxes[0], new Insets(0, 0, 0, 31));
        pane.add(checkboxes, 0,3, 6, 1);

        Button rollTheDice = new Button("Roll the dice!");
        pane.add(rollTheDice, 0, 8);

        final Label result = new Label();
        pane.add(result, 1, 8);
        result.setText("You have 3 rolls left.");

        rollTheDice.setOnAction(e -> {
            if (!boxes[0].isSelected() && this.count < 3) {
                images[0].setImage(randomizeImage(dice));
            }
            if (!boxes[1].isSelected() && this.count < 3) {
                images[1].setImage(randomizeImage(dice));
            }
            if (!boxes[2].isSelected() && this.count < 3) {
                images[2].setImage(randomizeImage(dice));
            }
            if (!boxes[3].isSelected() && this.count < 3) {
                images[3].setImage(randomizeImage(dice));
            }
            if (!boxes[4].isSelected() && this.count < 3) {
                images[4].setImage(randomizeImage(dice));
            }
            this.count++;

            // enable the checkboxes once the first throw has been made
            if (this.count == 1) {
                for (int i = 0; i < boxes.length; i++) {
                    boxes[i].setDisable(false);
                }
                result.setText("You have 2 rolls left.");
            }

            if (this.count == 2) {
                result.setText("You have 1 roll left.");
            }

            // calculate the score when all throws have been made
            if (this.count == 3) {
                calculateTheScore(images, result);
            }
        });

        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Randomize an image from the dice array, which is then set in the ImageView slot that called the method.
     * @param dicePics - the array of dice images
     * @return - the Image to set
     */
    private Image randomizeImage(Image[] dicePics) {
        Random rnd = new Random();
        int imageToPick = rnd.nextInt(5 + 1);

        return dicePics[imageToPick];
    }

    /**
     * Extract the final values and calculate the score, which is then presented to the player.
     * @param theImages - the array of ImageViews currently being displayed
     */
    private void calculateTheScore(ImageView[] theImages, Label result) {
        int[] theNumbers = new int[theImages.length];

        // takes the value right before the file extension (.png) and casts it to an integer
        for (int i = 0; i < theImages.length; i++) {
            theNumbers[i] = Character.getNumericValue(theImages[i].getImage().getUrl()
                    .charAt(theImages[i].getImage().getUrl().length() - 5));
        }

        Arrays.sort(theNumbers);

        if (scoreYahtzee(theNumbers)) {
            result.setText("Yahtzee!");
        }
        else if (scoreFour(theNumbers)) {
            result.setText("Four of a kind");
        }
        else if (scoreThree(theNumbers)) {
            result.setText("Three of a kind");
        }
        else if (fullHouse(theNumbers)) {
            result.setText("Full house");
        }
        else if (largeStraight(theNumbers)) {
            result.setText("Large straight");
        }
        else if (smallStraight(theNumbers)) {
            result.setText("Small straight");
        }
        else if (scorePair(theNumbers)) {
            result.setText("Pair");
        }
        else {
            result.setText("Unlucky, try again!");
        }
    }

    // evaluate the result for Yahtzee (all 5 dices have the same value)
    private boolean scoreYahtzee(int[] numbers) {
        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i] != numbers[i - 1]) {
                return false;
            }
        }
        return true;
    }

    // evaluate the result for the score Four of a kind (4 dices with the same value)
    private boolean scoreFour(int[] numbers) {
        return numbers[0] == numbers[3] || numbers[1] == numbers[4];
    }

    // evaluate the result for the score Three of a kind (3 dices with the same value)
    private boolean scoreThree(int[] numbers) {
        int counter = 1;
        int num = numbers[0];

        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i] == numbers[i - 1]) {
                if (numbers[i] != num) {
                    counter = 2;
                    num = numbers[i];
                }
                else {
                    num = numbers[i];
                    counter++;
                }
            }
            if (counter == 3) {
                return true;
            }
        }
        return false;
    }

    // evaluate the result for the score Full house (Three of a kind + pair)
    private boolean fullHouse(int[] numbers) {
        return (numbers[0] == numbers[1] && numbers[2] == numbers[4]) ||
                (numbers[0] == numbers[2] && numbers[3] == numbers[4]);
    }

    // evaluate the result for the score Large straight (all 5 dices with consecutive values)
    private boolean largeStraight(int[] numbers) {
        int counter = 1;

        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i] == numbers[i - 1] + 1) {
                counter++;
            }
            if (counter == 5) {
                return true;
            }
        }
        return false;
    }

    // evaluate the result for the score Small straight (4 dices with consecutive values)
    private boolean smallStraight(int[] numbers) {
        int counter = 1;

        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i] == numbers[i - 1] + 1) {
                counter++;
            }
            if (counter == 4) {
                return true;
            }
        }
        return false;
    }

    // evaluate the result for the score Pair (2 dices with the same value)
    private boolean scorePair(int[] numbers) {
        return numbers[0] == numbers[1] || numbers[1] == numbers[2] || numbers[2] == numbers[3] ||
                numbers[3] == numbers[4];
    }

    public static void main(String[] args) {
        launch(args);
    }
}
