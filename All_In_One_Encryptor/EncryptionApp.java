import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class EncryptionApp {
    private static PublicKey publicKey;
    private static PrivateKey privateKey;
    private static SecretKeySpec blowfishKey;
    private static SecretKey desKey;

    public static void main(String[] args) {
        // Initialize RSA keys
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            publicKey = keyPair.getPublic();
            privateKey = keyPair.getPrivate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Initialize Blowfish key
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("Blowfish");
            keyGen.init(128); // Set Blowfish key size (can be 32 to 448 bits)
            SecretKey secretKey = keyGen.generateKey();
            blowfishKey = new SecretKeySpec(secretKey.getEncoded(), "Blowfish");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Initialize DES key
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("DES");
            desKey = keyGen.generateKey();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create the main frame
        JFrame frame = new JFrame("Message Encryption");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450, 650); // Adjusted size to fit content
        frame.setLayout(new BorderLayout(10, 10));

        // Create the main panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Algorithm selection label and dropdown
        JLabel algorithmLabel = new JLabel("Select encryption algorithm:");
        panel.add(algorithmLabel, gbc);

        gbc.gridy++;
        String[] algorithms = {"Caesar Cipher", "AES", "RSA", "Vigenère Cipher", "Blowfish", "DES"};
        JComboBox<String> algorithmDropdown = new JComboBox<>(algorithms);
        panel.add(algorithmDropdown, gbc);

        // Secret key label and text field for AES
        gbc.gridy++;
        JLabel keyLabel = new JLabel("Enter secret key (for AES):");
        panel.add(keyLabel, gbc);

        gbc.gridy++;
        JTextField keyField = new JTextField(30);
        panel.add(keyField, gbc);

        // Keyword label and text field for Vigenère Cipher
        gbc.gridy++;
        JLabel keywordLabel = new JLabel("Enter keyword (for Vigenère Cipher):");
        panel.add(keywordLabel, gbc);

        gbc.gridy++;
        JTextField keywordField = new JTextField(30);
        panel.add(keywordField, gbc);

        // Input message label and text area
        gbc.gridy++;
        JLabel inputLabel = new JLabel("Enter your message:");
        panel.add(inputLabel, gbc);

        gbc.gridy++;
        JTextArea inputMessage = new JTextArea(3, 30);
        inputMessage.setLineWrap(true);
        inputMessage.setWrapStyleWord(true);
        JScrollPane inputScroll = new JScrollPane(inputMessage);
        panel.add(inputScroll, gbc);

        // Encrypt button
        gbc.gridy++;
        JButton encryptButton = new JButton("Encrypt Message");
        panel.add(encryptButton, gbc);

        // Encrypted message label and text area
        gbc.gridy++;
        JLabel encryptedLabel = new JLabel("Encrypted message:");
        panel.add(encryptedLabel, gbc);

        gbc.gridy++;
        JTextArea encryptedMessage = new JTextArea(3, 30);
        encryptedMessage.setLineWrap(true);
        encryptedMessage.setWrapStyleWord(true);
        encryptedMessage.setEditable(false);
        JScrollPane encryptedScroll = new JScrollPane(encryptedMessage);
        panel.add(encryptedScroll, gbc);

        // Section to display the original message before encryption
        gbc.gridy++;
        JLabel showMessageLabel = new JLabel("Message before encryption:");
        panel.add(showMessageLabel, gbc);

        gbc.gridy++;
        JTextArea showMessage = new JTextArea(3, 30);
        showMessage.setLineWrap(true);
        showMessage.setWrapStyleWord(true);
        showMessage.setEditable(false);
        JScrollPane showMessageScroll = new JScrollPane(showMessage);
        panel.add(showMessageScroll, gbc);

        // Decrypt button
        gbc.gridy++;
        JButton decryptButton = new JButton("Decrypt Message");
        panel.add(decryptButton, gbc);

        // Decrypted message label and text area
        gbc.gridy++;
        JLabel decryptedLabel = new JLabel("Decrypted message:");
        panel.add(decryptedLabel, gbc);

        gbc.gridy++;
        JTextArea decryptedMessage = new JTextArea(3, 30);
        decryptedMessage.setLineWrap(true);
        decryptedMessage.setWrapStyleWord(true);
        decryptedMessage.setEditable(false);
        JScrollPane decryptedScroll = new JScrollPane(decryptedMessage);
        panel.add(decryptedScroll, gbc);

        // Add panel to frame
        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);

        // Define button actions
        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedAlgorithm = (String) algorithmDropdown.getSelectedItem();
                String message = inputMessage.getText();
                String encrypted = "";
                int shift = 3; // Default shift value for Caesar Cipher
                String key = keyField.getText(); // Get secret key
                String keyword = keywordField.getText(); // Get keyword for Vigenère Cipher

                // Display the message to be encrypted
                showMessage.setText(message);

                // Encrypt the message based on the selected algorithm
                switch (selectedAlgorithm) {
                    case "Caesar Cipher":
                        encrypted = caesarCipherEncrypt(message, shift);
                        break;
                    case "AES":
                        if (key.length() != 16) {
                            JOptionPane.showMessageDialog(frame, "AES key must be 16 characters long.");
                            return;
                        }
                        encrypted = aesEncrypt(message, key);
                        break;
                    case "RSA":
                        encrypted = rsaEncrypt(message, publicKey);
                        break;
                    case "Vigenère Cipher":
                        if (keyword.isEmpty()) {
                            JOptionPane.showMessageDialog(frame, "Keyword cannot be empty for Vigenère Cipher.");
                            return;
                        }
                        encrypted = vigenereCipherEncrypt(message, keyword);
                        break;
                    case "Blowfish":
                        encrypted = blowfishEncrypt(message);
                        break;
                    case "DES":
                        encrypted = desEncrypt(message);
                        break;
                    default:
                        break;
                }

                encryptedMessage.setText(encrypted);
                // Remove the old encrypted message from decryption section
                decryptedMessage.setText(""); // Clear any previous decrypted message
            }
        });

        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedAlgorithm = (String) algorithmDropdown.getSelectedItem();
                String encrypted = encryptedMessage.getText();
                String decrypted = "";
                int shift = 3; // Default shift value for Caesar Cipher
                String key = keyField.getText(); // Get secret key
                String keyword = keywordField.getText(); // Get keyword for Vigenère Cipher

                // Decrypt the message based on the selected algorithm
                switch (selectedAlgorithm) {
                    case "Caesar Cipher":
                        decrypted = caesarCipherDecrypt(encrypted, shift);
                        break;
                    case "AES":
                        if (key.length() != 16) {
                            JOptionPane.showMessageDialog(frame, "AES key must be 16 characters long.");
                            return;
                        }
                        decrypted = aesDecrypt(encrypted, key);
                        break;
                    case "RSA":
                        decrypted = rsaDecrypt(encrypted, privateKey);
                        break;
                    case "Vigenère Cipher":
                        if (keyword.isEmpty()) {
                            JOptionPane.showMessageDialog(frame, "Keyword cannot be empty for Vigenère Cipher.");
                            return;
                        }
                        decrypted = vigenereCipherDecrypt(encrypted, keyword);
                        break;
                    case "Blowfish":
                        decrypted = blowfishDecrypt(encrypted);
                        break;
                    case "DES":
                        decrypted = desDecrypt(encrypted);
                        break;
                    default:
                        break;
                }

                decryptedMessage.setText(decrypted);
            }
        });
    }

    // RSA methods
    private static String rsaEncrypt(String message, PublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptedBytes = cipher.doFinal(message.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String rsaDecrypt(String encryptedMessage, PrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedMessage));
            return new String(decryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // AES methods
    private static String aesEncrypt(String message, String key) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] encryptedBytes = cipher.doFinal(message.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String aesDecrypt(String encryptedMessage, String key) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedMessage));
            return new String(decryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Blowfish methods
    private static String blowfishEncrypt(String message) {
        try {
            Cipher cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.ENCRYPT_MODE, blowfishKey);
            byte[] encryptedBytes = cipher.doFinal(message.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String blowfishDecrypt(String encryptedMessage) {
        try {
            Cipher cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.DECRYPT_MODE, blowfishKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedMessage));
            return new String(decryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // DES methods
    private static String desEncrypt(String message) {
        try {
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, desKey);
            byte[] encryptedBytes = cipher.doFinal(message.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String desDecrypt(String encryptedMessage) {
        try {
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, desKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedMessage));
            return new String(decryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Caesar Cipher methods
    private static String caesarCipherEncrypt(String message, int shift) {
        StringBuilder encrypted = new StringBuilder();
        for (char i : message.toCharArray()) {
            if (Character.isLetter(i)) {
                char base = Character.isUpperCase(i) ? 'A' : 'a';
                encrypted.append((char) ((i - base + shift) % 26 + base));
            } else {
                encrypted.append(i);
            }
        }
        return encrypted.toString();
    }

    private static String caesarCipherDecrypt(String encryptedMessage, int shift) {
        return caesarCipherEncrypt(encryptedMessage, 26 - shift);
    }

    // Vigenère Cipher methods
    private static String vigenereCipherEncrypt(String message, String keyword) {
        StringBuilder encrypted = new StringBuilder();
        keyword = keyword.toUpperCase();
        int keywordLength = keyword.length();
        for (int i = 0; i < message.length(); i++) {
            char m = message.charAt(i);
            if (Character.isLetter(m)) {
                char k = keyword.charAt(i % keywordLength);
                char base = Character.isUpperCase(m) ? 'A' : 'a';
                encrypted.append((char) ((m - base + (k - 'A')) % 26 + base));
            } else {
                encrypted.append(m);
            }
        }
        return encrypted.toString();
    }

    private static String vigenereCipherDecrypt(String encryptedMessage, String keyword) {
        StringBuilder decrypted = new StringBuilder();
        keyword = keyword.toUpperCase();
        int keywordLength = keyword.length();
        for (int i = 0; i < encryptedMessage.length(); i++) {
            char m = encryptedMessage.charAt(i);
            if (Character.isLetter(m)) {
                char k = keyword.charAt(i % keywordLength);
                char base = Character.isUpperCase(m) ? 'A' : 'a';
                decrypted.append((char) ((m - base - (k - 'A') + 26) % 26 + base));
            } else {
                decrypted.append(m);
            }
        }
        return decrypted.toString();
    }
}
