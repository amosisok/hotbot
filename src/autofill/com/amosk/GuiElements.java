package autofill.com.amosk;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GuiElements {
    ArrayList<JTextField> newProfileTextFields = new ArrayList<>();
    ArrayList<JLabel> newProfileLabels = new ArrayList<>();

    ArrayList<JTextField> itemDetailTextFields = new ArrayList<>();
    ArrayList<JLabel> itemDetailLabels = new ArrayList<>();

    JTextField profileName = new JTextField(20);
    JTextField firstName = new JTextField(20);
    JTextField lastName = new JTextField(20);
    JTextField address1 = new JTextField(20);
    JTextField city = new JTextField(20);
    JTextField postalCode = new JTextField(20);
    JTextField province = new JTextField(20);
    JTextField email = new JTextField(20);
    JTextField phone = new JTextField(20);
    JTextField cardNumber = new JTextField(20);
    JTextField cardName = new JTextField(20);
    JTextField cardExpiration = new JTextField(20);
    JTextField cardCVV = new JTextField(20);

    JLabel profileNameLabel;
    JLabel firstNameLabel;
    JLabel lastNameLabel;
    JLabel address1Label;
    JLabel cityLabel;
    JLabel postalCodeLabel;
    JLabel provinceLabel;
    JLabel emailLabel;
    JLabel phoneLabel;
    JLabel cardNumberLabel;
    JLabel cardNameLabel;
    JLabel cardExpirationLabel;
    JLabel cardCCVLabel;

    JTextField productName = new JTextField(20);
    JTextField shoeSize = new JTextField(20);
    JTextField clothingSize = new JTextField(20);

    JLabel productLabel;
    JLabel shoeSizeLabel;
    JLabel clothingSizeLabel;

    BufferedImage mainImage;

    GuiElements() {
        addNewProfileTextFields();
        addNewProfileLabels();
        addItemDetailTextFields();
        addItemDetailLabels();
        setLabelFonts("Helvetica Nue", newProfileLabels);
        setLabelFonts("Helvetica Nue", itemDetailLabels);
    }

    public void addNewProfileTextFields() {

        newProfileTextFields.add(profileName);
        newProfileTextFields.add(firstName);
        newProfileTextFields.add(lastName);
        newProfileTextFields.add(address1);
        newProfileTextFields.add(city);
        newProfileTextFields.add(postalCode);
        newProfileTextFields.add(province);
        newProfileTextFields.add(email);
        newProfileTextFields.add(phone);
        newProfileTextFields.add(cardNumber);
        newProfileTextFields.add(cardName);
        newProfileTextFields.add(cardExpiration);
        newProfileTextFields.add(cardCVV);
    }

    public void addNewProfileLabels() {
        profileNameLabel = new JLabel("Profile Name: ");
        profileNameLabel.setLabelFor(profileName);
        firstNameLabel = new JLabel("First Name: ");
        firstNameLabel.setLabelFor(firstName);
        lastNameLabel = new JLabel("Last Name: ");
        lastNameLabel.setLabelFor(lastName);
        address1Label = new JLabel("Address Line 1: ");
        address1Label.setLabelFor(address1);
        cityLabel = new JLabel("City: ");
        cityLabel.setLabelFor(city);
        postalCodeLabel = new JLabel("Postal Code: ");
        postalCodeLabel.setLabelFor(postalCode);
        provinceLabel = new JLabel("Province: ");
        provinceLabel.setLabelFor(province);
        emailLabel = new JLabel("Email: ");
        emailLabel.setLabelFor(email);
        phoneLabel = new JLabel("Phone number: ");
        phoneLabel.setLabelFor(phone);
        cardNumberLabel = new JLabel("Card number: ");
        cardNumberLabel.setLabelFor(cardNumber);
        cardNameLabel = new JLabel("Name on card: ");
        cardNameLabel.setLabelFor(cardName);
        cardExpirationLabel = new JLabel("Expiration date (MM/YY): ");
        cardExpirationLabel.setLabelFor(cardExpiration);
        cardCCVLabel = new JLabel("CVV: ");
        cardCCVLabel.setLabelFor(cardCVV);

        newProfileLabels.add(profileNameLabel);
        newProfileLabels.add(firstNameLabel);
        newProfileLabels.add(lastNameLabel);
        newProfileLabels.add(address1Label);
        newProfileLabels.add(cityLabel);
        newProfileLabels.add(postalCodeLabel);
        newProfileLabels.add(provinceLabel);
        newProfileLabels.add(emailLabel);
        newProfileLabels.add(phoneLabel);
        newProfileLabels.add(cardNumberLabel);
        newProfileLabels.add(cardNameLabel);
        newProfileLabels.add(cardExpirationLabel);
        newProfileLabels.add(cardCCVLabel);

    }

    public void addItemDetailTextFields() {
        itemDetailTextFields.add(productName);
        itemDetailTextFields.add(shoeSize);
        itemDetailTextFields.add(clothingSize);
    }

    public void addItemDetailLabels() {
        productLabel = new JLabel("Product link: ");
        productLabel.setLabelFor(productName);
        shoeSizeLabel = new JLabel("Shoe size (US): ");
        shoeSizeLabel.setLabelFor(shoeSize);
        clothingSizeLabel = new JLabel("Clothing size: ");
        clothingSizeLabel.setLabelFor(clothingSize);

        itemDetailLabels.add(productLabel);
        itemDetailLabels.add(shoeSizeLabel);
        itemDetailLabels.add(clothingSizeLabel);
    }

    public ArrayList<JTextField> getNewProfileTextFields() {
        return newProfileTextFields;
    }

    public ArrayList<JLabel> getNewProfileLabels() {
        return newProfileLabels;
    }

    public ArrayList<JTextField> getItemDetailTextFields() {
        return itemDetailTextFields;
    }

    public ArrayList<JLabel> getItemDetailLabels() {
        return itemDetailLabels;
    }

    public void resetNewProfileTextFields() {
        profileName.setText("");
        firstName.setText("");
        lastName.setText("");
        address1.setText("");
        city.setText("");
        postalCode.setText("");
        province.setText("");
        email.setText("");
        phone.setText("");
        cardNumber.setText("");
        cardName.setText("");
        cardExpiration.setText("");
        cardCVV.setText("");
    }

    public void resetItemDetailTextFields() {
        productName.setText("");
        shoeSize.setText("");
        clothingSize.setText("");
    }

    public JLabel addImage(String path) {
        try {
            mainImage = ImageIO.read(new File(path));
        } catch (IOException e) {
            System.err.println("Cannot open image");
            e.printStackTrace();
        }

        return new JLabel(new ImageIcon(mainImage));
    }

    public void setLabelFonts(String font, ArrayList<JLabel> labels) {
        for(JLabel label: labels) {
            label.setFont(new Font(font, label.getFont().getStyle(), label.getFont().getSize()));
        }
    }

}
