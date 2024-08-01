module fr.afpa {
    requires javafx.controls;
    requires javafx.fxml;
    requires ez.vcard;

    opens fr.afpa to javafx.fxml;
    opens fr.afpa.Controllers to javafx.fxml;
    opens fr.afpa.Models to javafx.fxml;
    exports fr.afpa;
}
