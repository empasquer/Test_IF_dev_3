package com.emmapasquer.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private Label welcomeLabel;

    private MediaPlayer mediaPlayer;

    private int sceneCounter = 0;

    private final Choice[] choices = {
            new Choice("You find yourself standing at the entrance of a cave! Ahead of you is a dark corridor...", "Click on me! Don't worry.."),  // 0
            new Choice("You now stand in pitch darkness. Do you want to explore more if the corridor?", "Yes", "No"),  // 1
            new Choice("You encounter a mysterious figure...\nThe figure beckons you to follow. Will you go?", "Yes", "No"),  // 2
            new Choice("You follow the figure into a room with two doors. One door is marked 'Danger,' and the other is marked 'Safety.'\nWhich door will you choose?", "Danger", "Safety"),  // 3
            new Choice("You take the dangerous path and encounter a monster...", "Click on me! Don't worry.."),  // 4
            new Choice("You decide it's too risky and go back...", "Click on me! Don't worry.."),  // 5
            new Choice("You choose the safer path and find a hidden treasure...", "Click on me! Don't worry.."),  // 6
            new Choice("You feel uncertain and return to the starting point...", "Click on me! Don't worry.."),  // 7
            new Choice("You decide not to follow the figure and explore another path...", "Explore another path", "Stay in place"),  // 8
            new Choice("You find a hidden passage leading to a secret room...", "Click on me! Don't worry.."),  // 9
            new Choice("You enter the secret room and discover a valuable artifact...", "Click on me! Don't worry.."),  // 10
            new Choice("You choose to stay in place and the mysterious figure disappears...", "Click on me! Don't worry.."),  // 11
            new Choice("Thanks for playing! Bye for now", "Click on me! Don't worry..")  // 12
    };

    @FXML
    private Button[] buttons;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        welcomeLabel.setText(choices[sceneCounter].getText());
        String soundPath = new File("src/main/resources/scene_transition.mp3").toURI().toString();
        Media media = new Media(soundPath);
        mediaPlayer = new MediaPlayer(media);
        updateButtons();
    }

    private void updateButtons() {
        String[] options = choices[sceneCounter].getOptions();
        int numButtons = options.length;
        buttons = new Button[numButtons];
        for (int i = 0; i < numButtons; i++) {
            buttons[i] = new Button(options[i]);
            buttons[i].setOnAction(this::handleButtonClick);
        }

        VBox vbox = (VBox) welcomeLabel.getParent();
        vbox.getChildren().clear();
        vbox.getChildren().add(welcomeLabel);
        vbox.getChildren().addAll(buttons);
    }

    private void handleButtonClick(ActionEvent actionEvent) {
        mediaPlayer.play();
        String selectedOption = ((Button) actionEvent.getSource()).getText();
        choices[sceneCounter].handleChoice(selectedOption, this);
    }

    private static class Choice {
        private final String text;
        private final String[] options;

        public Choice(String text, String... options) {
            this.text = text;
            this.options = options;
        }

        public String getText() {
            return text;
        }

        public String[] getOptions() {
            return options;
        }

        public void handleChoice(String selectedOption, MainController controller) {
            switch (controller.sceneCounter) {
                case 1:
                    if (selectedOption.equals("No")) {
                        controller.sceneCounter = 7;
                    } else {
                        controller.sceneCounter = 2;
                    }
                    break;
                case 2:
                    if (selectedOption.equals("No")) {
                        controller.sceneCounter = 11;
                    } else {
                        controller.sceneCounter = 3;
                    }
                    break;
                case 3:
                    if (selectedOption.equals("Danger")) {
                        controller.sceneCounter = 4;
                    } else if (selectedOption.equals("Safety")) {
                        controller.sceneCounter = 6;
                    }
                    break;
                case 7:
                    controller.sceneCounter = 0;
                    break;
                case 8:
                    if (selectedOption.equals("Explore another path")) {
                        controller.sceneCounter = 10;
                    } else if (selectedOption.equals("Stay in place")) {
                        controller.sceneCounter = 7;
                    }
                    break;
                // ... (Add more cases for other scenes if needed)
                default:
                    controller.sceneCounter++;
                    break;
            }

            if (controller.sceneCounter < controller.choices.length) {
                controller.welcomeLabel.setText(controller.choices[controller.sceneCounter].getText());
                controller.updateButtons();
            } else {
                controller.welcomeLabel.setText("You've reached the end of the game.");
                for (Button button : controller.buttons) {
                    button.setDisable(true);
                }
            }
        }



    }
}
