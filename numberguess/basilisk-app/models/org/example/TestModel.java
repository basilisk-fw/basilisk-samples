package org.example;

import basilisk.core.artifact.BasiliskModel;
import basilisk.metadata.ArtifactProviderFor;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import org.kordamp.basilisk.runtime.core.artifact.AbstractBasiliskModel;

import javax.annotation.Nonnull;

@ArtifactProviderFor(BasiliskModel.class)
public class TestModel extends AbstractBasiliskModel {
    private IntegerProperty guess;
    private IntegerProperty remainingGuesses;
    private BooleanProperty alertVisible;
    private StringProperty alertText;
    private ObservableList<String> alertClasses;

    @Nonnull
    public final IntegerProperty guessProperty() {
        if (guess == null) {
            guess = new SimpleIntegerProperty(this, "guess", 0);
        }
        return guess;
    }

    public void setGuess(int guess) {
        runInsideUIAsync(() -> guessProperty().set(guess));
    }

    public int getGuess() {
        return guessProperty().get();
    }

    @Nonnull
    public final IntegerProperty remainingGuessesProperty() {
        if (remainingGuesses == null) {
            remainingGuesses = new SimpleIntegerProperty(this, "remainingGuesses", 10);
        }
        return remainingGuesses;
    }

    public void setRemainingGuesses(int remainingGuesses) {
        runInsideUIAsync(() -> remainingGuessesProperty().set(remainingGuesses));
    }

    public int getRemainingGuesses() {
        return remainingGuessesProperty().get();
    }

    @Nonnull
    public BooleanProperty alertVisibleProperty() {
        if (alertVisible == null) {
            alertVisible = new SimpleBooleanProperty(this, "alertVisible", false);
        }
        return alertVisible;
    }

    public boolean getAlertVisible() {
        return alertVisibleProperty().get();
    }

    public void setAlertVisible(boolean alertVisible) {
        runInsideUIAsync(() -> alertVisibleProperty().set(alertVisible));
    }

    @Nonnull
    public StringProperty alertTextProperty() {
        if (alertText == null) {
            alertText = new SimpleStringProperty(this, "alertText");
        }
        return alertText;
    }

    public String getAlertText() {
        return alertTextProperty().get();
    }

    public void setAlertText(String alertText) {
        runInsideUIAsync(() -> alertTextProperty().set(alertText));
    }

    public ObservableList<String> getAlertClasses() {
        return alertClasses;
    }

    public void setAlertClasses(ObservableList<String> alertClasses) {
        this.alertClasses = alertClasses;
    }
}