package org.example;

import basilisk.core.artifact.BasiliskView;
import basilisk.inject.MVCMember;
import basilisk.metadata.ArtifactProviderFor;
import basilisk.util.BasiliskApplicationUtils;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.kordamp.basilisk.runtime.javafx.artifact.AbstractJavaFXBasiliskView;

import javax.annotation.Nonnull;
import java.util.Collections;

import static basilisk.util.BasiliskNameUtils.isBlank;
import static javafx.beans.binding.Bindings.bindBidirectional;
import static javafx.beans.binding.Bindings.equal;
import static javafx.beans.binding.Bindings.isEmpty;
import static javafx.beans.binding.Bindings.or;

@ArtifactProviderFor(BasiliskView.class)
public class TestView extends AbstractJavaFXBasiliskView {
    private TestController controller;
    private TestModel model;

    @FXML private TextField guessTextField;
    @FXML private Button guessActionTarget;
    @FXML private HBox alert;
    @FXML private Label remainingGuesses;
    @FXML private Label alertText;

    @MVCMember
    public void setController(@Nonnull TestController controller) {
        this.controller = controller;
    }

    @MVCMember
    public void setModel(@Nonnull TestModel model) {
        this.model = model;
    }

    @Override
    public void initUI() {
        Stage stage = (Stage) getApplication()
            .createApplicationContainer(Collections.<String, Object>emptyMap());
        stage.setTitle(getApplication().getConfiguration().getAsString("application.title"));
        stage.setScene(init());
        stage.sizeToScene();
        getApplication().getWindowManager().attach("mainWindow", stage);
    }

    // build the UI
    private Scene init() {
        Scene scene = new Scene(new Group());
        if (BasiliskApplicationUtils.isIOS) {
            Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
            scene = new Scene(new Group(), bounds.getWidth(), bounds.getHeight());
        }
        scene.setFill(Color.WHITE);
        scene.getStylesheets().add("bootstrapfx.css");

        Node node = loadFromFXML();
        connectActions(node, controller);
        connectMessageSource(node);

        bindBidirectional(guessTextField.textProperty(), model.guessProperty(), new StringConverter<Number>() {
            @Override
            public String toString(Number object) {
                return String.valueOf(object);
            }

            @Override
            public Number fromString(String string) {
                try {
                    return !isBlank(string) ? Integer.parseInt(string) : 0;
                } catch (NumberFormatException e) {
                    return 0;
                }
            }
        });

        guessActionTarget.disableProperty().bind(
            or(equal(0, model.remainingGuessesProperty()),
                isEmpty(guessTextField.textProperty())));

        model.setAlertClasses(alert.getStyleClass());
        alert.visibleProperty().bind(model.alertVisibleProperty());
        bindBidirectional(remainingGuesses.textProperty(), model.remainingGuessesProperty(), new StringConverter<Number>() {
            @Override
            public String toString(Number object) {
                return String.valueOf(object);
            }

            @Override
            public Number fromString(String string) {
                return Integer.parseInt(string);
            }
        });
        alertText.textProperty().bind(model.alertTextProperty());

        if (node instanceof Parent) {
            scene.setRoot((Parent) node);
        } else {
            ((Group) scene.getRoot()).getChildren().addAll(node);
        }

        return scene;
    }
}
