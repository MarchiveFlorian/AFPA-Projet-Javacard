module fr.afpa {
    requires javafx.controls;
    requires javafx.fxml;
    requires ez.vcard;

    opens fr.afpa to javafx.fxml;
    opens fr.afpa.controller to javafx.fxml;
    opens fr.afpa.model to javafx.fxml;
    exports fr.afpa;
}
