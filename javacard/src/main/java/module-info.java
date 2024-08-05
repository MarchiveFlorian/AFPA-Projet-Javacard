module fr.afpa {
    requires javafx.controls;
    requires javafx.fxml;
    requires ez.vcard;

    opens fr.afpa to javafx.fxml;
    opens fr.afpa.controllers to javafx.fxml;
    opens fr.afpa.models to javafx.fxml;
    exports fr.afpa;
}
