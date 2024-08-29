# All-In-One-Encryptor
Java-based desktop application that allows users to encrypt and decrypt messages using various popular encryption algorithms. This application features a user-friendly graphical interface to select the encryption method and provide the necessary inputs. The supported encryption modes include: Caesar Cipher, AES, RSA, Vigenère Cipher, Blowfish, DES

# Message Encryption Application

This Java-based desktop application provides a simple interface for encrypting and decrypting messages using various encryption algorithms. The application allows users to select from multiple encryption methods, input a message, and view the encrypted and decrypted versions of the message.

## Features

- **Graphical User Interface (GUI)**: Built using Java Swing for a user-friendly experience.
- **Supports Multiple Encryption Algorithms**:
  - **Caesar Cipher**: A basic substitution cipher with a fixed shift.
  - **AES (Advanced Encryption Standard)**: A symmetric encryption algorithm requiring a 16-character secret key.
  - **RSA (Rivest-Shamir-Adleman)**: An asymmetric encryption algorithm using public and private keys.
  - **Vigenère Cipher**: A polyalphabetic substitution cipher using a keyword.
  - **Blowfish**: A symmetric block cipher.
  - **DES (Data Encryption Standard)**: A symmetric-key algorithm for data encryption.

## How to Run

1. **Clone the repository**:
    ```bash
    git clone https://github.com/SaiSrikarK03/encryption-app.git
    ```
2. **Compile the Java program**:
    Navigate to the directory where the `EncryptionApp.java` file is located and compile it using the following command:
    ```bash
    javac EncryptionApp.java
    ```

3. **Run the application**:
    After compilation, run the application using:
    ```bash
    java EncryptionApp
    ```

## Usage

1. **Select Encryption Algorithm**: Use the dropdown menu to choose an encryption algorithm.
2. **Enter Secret Key (for AES)**: Input a 16-character key if using AES.
3. **Enter Keyword (for Vigenère Cipher)**: Input a keyword if using the Vigenère Cipher.
4. **Input Message**: Enter the message you wish to encrypt in the provided text area.
5. **Encrypt Message**: Click the "Encrypt Message" button to see the encrypted version of your input message.
6. **Decrypt Message**: Click the "Decrypt Message" button to decrypt the encrypted message back to the original text.

## Requirements

- **Java Development Kit (JDK)**: Make sure you have JDK installed (version 8 or later).
- **Swing Library**: Included with standard JDK installations.

## Code Explanation

- **Key Generation**: The application initializes keys for RSA, Blowfish, and DES encryption methods.
- **Encryption and Decryption**: Methods are implemented for each encryption algorithm to handle message encryption and decryption.
- **UI Components**: The GUI is constructed using `JFrame`, `JPanel`, `JTextArea`, `JButton`, and other Swing components.

## Future Improvements

- Implement additional encryption algorithms.
- Add features for saving and loading encrypted messages.
- Enhance the user interface for better usability.

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.

## Acknowledgements

- Java Cryptography Architecture (JCA)
- Oracle's Java Swing framework
- Various online resources for cryptography and Swing examples

