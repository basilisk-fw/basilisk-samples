package org.example;

import basilisk.core.artifact.BasiliskController;
import basilisk.metadata.ArtifactProviderFor;
import org.kordamp.basilisk.runtime.core.artifact.AbstractBasiliskController;

import java.util.Map;
import java.util.Random;

import static java.util.Arrays.asList;

@ArtifactProviderFor(BasiliskController.class)
public class TestController extends AbstractBasiliskController {
    private static final Random RANDOM = new Random();

    private TestModel model;
    private int number;

    @Override
    public void mvcGroupInit(Map<String, Object> args) {
        startGame();
    }

    private void startGame() {
        number = RANDOM.nextInt(100);
        model.setGuess(0);
        model.setRemainingGuesses(10);
        model.setAlertVisible(false);
        System.out.println(number);
    }

    public void setModel(TestModel model) {
        this.model = model;
    }

    public void guess() {
        model.setAlertVisible(true);
        model.setRemainingGuesses(model.getRemainingGuesses() - 1);
        if (model.getGuess() == number) {
            model.setAlertText("Congratulations! You won!");
            model.setRemainingGuesses(0);
            model.getAlertClasses().setAll(asList("alert", "alert-success"));
            return;
        } else if (model.getGuess() > number) {
            model.setAlertText("Lower");
            model.getAlertClasses().setAll(asList("alert", "alert-info"));
        } else {
            model.setAlertText("Higher");
            model.getAlertClasses().setAll(asList("alert", "alert-warning"));
        }

        if (model.getRemainingGuesses() == 0) {
            model.setAlertText("The number was " + number);
            model.getAlertClasses().setAll(asList("alert", "alert-danger"));
        }
    }

    public void restart() {
        startGame();
    }
}