package com.example.ninemenmorrismvp;

import javafx.scene.control.Button;

public interface Iview {

    void displayMessage(String str);
    void setWhiteBackground(Button b);
    void setBlackBackground(Button b);
    void removeWhiteBackground(Button b);
    void removeBlackBackground(Button b);

}
