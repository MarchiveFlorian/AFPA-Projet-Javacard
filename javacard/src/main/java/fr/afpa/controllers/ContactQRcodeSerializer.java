package fr.afpa.controllers;

// Importing necessary libraries for QR code generation
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ContactQRcodeSerializer {

    /**
     * Creates a QR code and saves it to the specified path.
     *
     * @param data     The data to be encoded in the QR code
     * @param path     The file path where the QR code image will be saved
     * @param charset  The character set to be used
     * @param hashMap  Map containing QR code encoding parameters
     * @param height   The height of the QR code image
     * @param width    The width of the QR code image
     * @throws WriterException If an error occurs during the writing process
     * @throws IOException     If an I/O error occurs
     */
    public static void createQR(String data, String path,
                                String charset, Map<EncodeHintType, ErrorCorrectionLevel> hashMap,
                                int height, int width)
            throws WriterException, IOException {

        // Encoding the data using the specified charset and generating a QR code matrix
        BitMatrix matrix = new MultiFormatWriter().encode(
                new String(data.getBytes(charset), charset),
                BarcodeFormat.QR_CODE, width, height);

        // Defining the path to save the QR code image
        Path pathFile = Paths.get(path);

        // Writing the QR code matrix to an image file
        MatrixToImageWriter.writeToPath(
                matrix,
                path.substring(path.lastIndexOf('.') + 1),
                pathFile);
    }

    /**
     * Reads the content of a file and returns it as a string.
     *
     * @param filePath The path of the file to be read
     * @return The content of the file as a string
     * @throws IOException If an I/O error occurs
     */
    public static String readFileAsString(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

    /**
     * Main method to generate a QR code from a vCard file.
     *
     * @param args Command line arguments
     * @throws WriterException If an error occurs during the writing process
     * @throws IOException     If an I/O error occurs
     */
    public static void main(String[] args)
            throws WriterException, IOException {

        // The path to the vCard file
        String vcardFilePath = "contacts.vcf";

        // Reading the vCard file content
        String vcardString = readFileAsString(vcardFilePath);

        // The path where the QR code image will be saved
        String qrCodeImagePath = "QRcode.png";

        // Encoding charset
        String charset = "UTF-8";

        // Creating a map to define the QR code error correction level
        Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<>();
        hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

        // Creating the QR code and saving it as a PNG file
        createQR(vcardString, qrCodeImagePath, charset, hashMap, 200, 200);
        System.out.println("QR Code Generated!!! ");
    }
}