module fr.afpa {
    requires javafx.controls;
    requires javafx.fxml;
    requires ez.vcard;
    requires json.simple;
    requires com.google.zxing.javase;
    requires com.google.zxing;
    requires javafx.graphics;
    requires java.sql;

    opens fr.afpa to javafx.fxml;
    opens fr.afpa.controllers to javafx.fxml;
    opens fr.afpa.models to javafx.fxml;
    exports fr.afpa;
}
