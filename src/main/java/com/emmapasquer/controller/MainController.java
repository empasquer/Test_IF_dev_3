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

    private int sceneCounter = 0; // To track the current scene

    private final String[] scenes = {
            "You chose to explore the dark corridor...",
            "You encounter a mysterious figure...\nThe figure beckons you to follow. Will you go? Yes/No",
            "Thanks for playing! Bye for now"
    };

    private final String[][] sceneButtons = {
            {"Click on me! Don't worry.."},
            {"Click on me! Don't worry.."},
            {"Yes", "No"},
            {"Click on me! Don't worry.."}
    };

    @FXML
    private Button[] buttons;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize your UI components and logic here
        welcomeLabel.setText("Welcome to Horror Game IF test hehe");
        // Initialize media with a scene transition sound
        String soundPath = new File("src/main/resources/scene_transition.mp3").toURI().toString();
        Media media = new Media(soundPath);
        mediaPlayer = new MediaPlayer(media);
        // Set up initial buttons
        updateButtons();
    }

    private void updateButtons() {
        int numButtons = sceneButtons[sceneCounter].length;
        buttons = new Button[numButtons];
        for (int i = 0; i < numButtons; i++) {
            buttons[i] = new Button(sceneButtons[sceneCounter][i]);
            buttons[i].setOnAction(this::handleButtonClick);
        }

        // Clear previous buttons and add new ones to the UI
        VBox vbox = (VBox) welcomeLabel.getParent();
        vbox.getChildren().removeIf(node -> node instanceof Button);
        vbox.getChildren().addAll(buttons);
    }

    @FXML
    public void handleButtonClick(ActionEvent actionEvent) {
        if (sceneCounter == 2) {
            // Handle scene 3 with Yes/No options
            if (actionEvent.getSource() == buttons[0]) {
                welcomeLabel.setText("You chose to follow the figure...");
            } else {
                welcomeLabel.setText("You chose not to follow the figure...");
            }
        } else {
            // Handle other scenes
            welcomeLabel.setText(scenes[sceneCounter]);
        }

        mediaPlayer.play(); // Play the scene transition sound

        if (sceneCounter < scenes.length - 1) {
            sceneCounter++;
            updateButtons();
        } else {
            welcomeLabel.setText("You've reached the end of the game.");
            // Disable the buttons to prevent further clicks
            for (Button button : buttons) {
                button.setDisable(true);
            }
        }
    }
}
