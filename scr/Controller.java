package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    public Button btnDel;
    @FXML
    public RadioButton radioKeyboard;
    @FXML
    public Group groupKeyboard;
    @FXML
    public ToggleButton toggleBackground;
    @FXML
    public AnchorPane CalculatorMCR;
    @FXML
    public Label labelFreq;
    @FXML
    public Label labelRes;
    @FXML
    public Label key;
    @FXML
    public CheckBox fixButton;
    @FXML
    private TextField inputNum;
    @FXML
    private TextField outputNum;
    @FXML
    private TextArea info;

    public Controller() throws IOException {
    }

    @FXML
    // on/off keyboard
    public void getRadioKeyboard(ActionEvent actionEvent) {
        if (radioKeyboard.isSelected()) {
            groupKeyboard.setVisible(false);
        } else {
            groupKeyboard.setVisible(true);
        }
    }

    @FXML
    // input from keyboard
    public void getButtonEvent(ActionEvent actionEvent) {
        String value = ((Button) actionEvent.getSource()).getText();
        inputNum.setText(inputNum.getText() + value);
    }

    @FXML
    // del keyboard
    public void getDellNums(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnDel) {
            String s = inputNum.getText();
            inputNum.setText("");
            for (int i = 0; i < s.length() - 1; i++) {
                inputNum.setText(inputNum.getText() + s.charAt(i));
            }
        }
    }

    @FXML
    void getInputNum(ActionEvent event) {
    }

    @FXML
    void getOutputNum(ActionEvent event) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        inputNum.setFocusTraversable(true);
        inputNum.requestFocus();

        Tooltip tooltipIn = new Tooltip();
        tooltipIn.setText("Частота");
        inputNum.setTooltip(tooltipIn);

        Tooltip tooltipOut = new Tooltip();
        tooltipOut.setText("Результат");
        outputNum.setTooltip(tooltipOut);

        //for output not formatting
        outputNum.setEditable(false);
        outputNum.setFocusTraversable(false);

        //for information not formatting
        info.setEditable(false);
        info.setMouseTransparent(true);
        info.setFocusTraversable(false);

        //main work from input to output
        try {
            inputNum.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    if (!newValue.matches("\\d*")) {
                        inputNum.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                    if (newValue.length() > 5) {
                        inputNum.setText(oldValue);
                    } else {
                        int in = Integer.parseInt(newValue);
                        if (in >= 10700 && in <= 12750) {
                            inputNum.setStyle("-fx-border-color: #3a8000");
                            if (in <= 11700) {
                                outputNum.setText(String.valueOf(in - 9750));
                                info.setVisible(true);
                                info.setText(String.valueOf(in + " - 9750 = " + (in - 9750)));
                            }
                            if (in > 11700) {
                                outputNum.setText(String.valueOf(in - 10600));
                                info.setVisible(true);
                                info.setText(String.valueOf(in + " - 10600 = " + (in - 10600)));
                            }
                        } else {
                            inputNum.setStyle("-fx-border-color: red");
                            info.setStyle("-fx-text-inner-color: #cc0000");
                            info.setVisible(true);
                            info.setText("Частота должна быть в диапазоне: 10700 - 12750");
                            info.setEditable(false);
                            outputNum.clear();
                        }
                    }
                }
            });
        } catch (Exception e) {
            info.setText("Числовой формат ввода");
        }

    }

    // about change fon white/black
    public void getToggleBackground(ActionEvent actionEvent) {
        if (toggleBackground.isSelected()) {
            CalculatorMCR.setStyle("-fx-background-color: #353839");
            outputNum.setStyle("-fx-background-color: white");
            labelFreq.setTextFill(Color.web("white"));
            labelRes.setTextFill(Color.web("white"));
            radioKeyboard.setTextFill(Color.web("white"));
            key.setTextFill(Color.web("white"));
            toggleBackground.setStyle("-fx-background-color: white");
            toggleBackground.setTextFill(Color.web("black"));
            fixButton.setTextFill(Color.web("white"));
        }else {
            CalculatorMCR.setStyle("-fx-background-color: white");
            outputNum.setStyle("-fx-border-color: #808080");
            labelFreq.setTextFill(Color.web("black"));
            labelRes.setTextFill(Color.web("black"));
            radioKeyboard.setTextFill(Color.web("black"));
            toggleBackground.setStyle("-fx-background-color: black");
            key.setTextFill(Color.web("black"));
            toggleBackground.setTextFill(Color.web("white"));
            fixButton.setTextFill(Color.web("black"));
        }
    }

    //fix app checkbox
    @FXML
    public void getFix(ActionEvent actionEvent) throws IOException {
        Stage stageAP = (Stage) CalculatorMCR.getScene().getWindow();
        if(fixButton.isSelected()){
            stageAP.setAlwaysOnTop(true);
        }else stageAP.setAlwaysOnTop(false);
    }
}
