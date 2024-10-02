 package simpleForgotPassword;

import de.taimos.totp.TOTP;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.Scanner;

public class TwoFactorAuth {
    
    // Method to generate secret key
    public String generateSecretKey() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        Base32 base32 = new Base32();
        return base32.encodeToString(bytes);
    }

    // Method to generate TOTP code
    public static String getTOTPCode(String secretKey) {
        Base32 base32 = new Base32();
        byte[] bytes = base32.decode(secretKey);
        String hexKey = Hex.encodeHexString(bytes);
        return TOTP.getOTP(hexKey);
    }

    // Method to generate Google Authenticator barcode data
    public static String getGoogleAuthenticatorBarCode(String secretKey, String account, String issuer) {
        try {
            return "otpauth://totp/" + URLEncoder.encode(issuer + ":" + account, "UTF-8").replace("+", "%20") +
                    "?secret=" + URLEncoder.encode(secretKey, "UTF-8").replace("+", "%20") +
                    "&issuer=" + URLEncoder.encode(issuer, "UTF-8").replace("+", "%20");
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    // Method to generate QR Code
    public static void createQRCode(String barCodeData, String filePath, int height, int width)
            throws WriterException, IOException {
        BitMatrix matrix = new MultiFormatWriter().encode(barCodeData, BarcodeFormat.QR_CODE, width, height);
        try (FileOutputStream out = new FileOutputStream(filePath)) {
            MatrixToImageWriter.writeToStream(matrix, "png", out);
        }
    }

    // Main method
//    public static void main(String[] args) {
//        try {
//            // Generate secret key
//            String secretKey = generateSecretKey();
//            System.out.println("Secret Key: " + secretKey);
//
//            // Display barcode URL for Google Authenticator
//            String email = "test@gmail.com";
//            String companyName = "Roam Weather";
//            String barCodeUrl = getGoogleAuthenticatorBarCode(secretKey, email, companyName);
//            System.out.println("Google Authenticator Barcode URL: " + barCodeUrl);
//
//            // Generate QR code
//            createQRCode(barCodeUrl, "QRCode.png", 300, 300);
//            System.out.println("QR Code generated at: QRCode.png");
//
//            // Simulate 2FA flow
//            Scanner scanner = new Scanner(System.in);
//            while (true) {
//                System.out.print("Enter the 2FA code from Google Authenticator: ");
//                String code = scanner.nextLine();
//                if (code.equals(getTOTPCode(secretKey))) {
//                    System.out.println("Logged in successfully");
//                    break;
//                } else {
//                    System.out.println("Invalid 2FA Code, try again.");
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
