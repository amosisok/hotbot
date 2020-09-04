package autofill;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class GUI implements ActionListener {
    JFrame frame = new JFrame("Autocheckout");
    JPanel mainPanel = new JPanel();
    JPanel homePage = new JPanel();
    JPanel itemDetails;
    JPanel newProfilePanel;
    JPanel existingProfilePanel;
    JPanel optionsPanel;
    CardLayout cardLayout = new CardLayout();
    Options options = Options.getInstance();

    GUI() {

        options.setHeadless(false);
        mainPanel.setLayout(cardLayout);
        homePage.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        mainPanel.add(homePage, "homePage");

        newProfilePanel = createNewProfile();
        mainPanel.add(newProfilePanel, "newProfile");

        existingProfilePanel = getExistingProfile();
        mainPanel.add(existingProfilePanel, "existingProfile");

        optionsPanel = createOptions();
        mainPanel.add(optionsPanel, "options");

        JButton newProfileButton = new JButton("Create new profile");
//        newProfileButton.addActionListener(this);
        homePage.add(newProfileButton);

        newProfileButton.addActionListener(actionEvent -> {
            cardLayout.show(mainPanel, "newProfile");
        });

        JButton existingProfileButton = new JButton("Use existing profile");
//        existingProfileButton.addActionListener(this);
        homePage.add(existingProfileButton);

        existingProfileButton.addActionListener(actionEvent -> {
            mainPanel.remove(existingProfilePanel);
            mainPanel.add(getExistingProfile(), "existingProfile");
            cardLayout.show(mainPanel, "existingProfile");
        });

        JButton optionsButton = new JButton("Options");
//        optionsButton.addActionListener(this);
        homePage.add(optionsButton);
        optionsButton.addActionListener(actionEvent -> {
            cardLayout.show(mainPanel, "options");
        });


        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(650, 600);
        frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }

    public JPanel createNewProfile() {
        UserInfo info = new UserInfo();
        FileOperations operations = new FileOperations();
//        GridBagLayout flowLayout = new GridBagLayout();
//        GridLayout flowLayout = new GridLayout(14,1);
        FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT, 120, 10);
        JPanel newProfile = new JPanel(flowLayout);
        GridBagConstraints gbc = new GridBagConstraints();

        JTextField profileName = new JTextField(20);
        JLabel profileNameLabel = new JLabel("Profile Name: ");
        profileNameLabel.setLabelFor(profileName);
        newProfile.add(profileNameLabel, gbc);
        newProfile.add(profileName, gbc);

        JTextField firstName = new JTextField(20);
        JLabel firstNameLabel = new JLabel("First Name: ");
        firstNameLabel.setLabelFor(firstName);
        newProfile.add(firstNameLabel);
        newProfile.add(firstName);

        JTextField lastName = new JTextField(20);
        JLabel lastNameLabel = new JLabel("Last Name: ");
//        lastNameLabel.setLabelFor(lastName);
        newProfile.add(lastNameLabel);
        newProfile.add(lastName);

        JTextField address1 = new JTextField(20);
        JLabel address1Label = new JLabel("Address Line 1: ");
//        address1Label.setLabelFor(address1);
        newProfile.add(address1Label);
        newProfile.add(address1);

        JTextField city = new JTextField(20);
        JLabel cityLabel = new JLabel("City: ");
//        cityLabel.setLabelFor(city);
        newProfile.add(cityLabel);
        newProfile.add(city);

        JTextField postalCode = new JTextField(20);
        JLabel postalCodeLabel = new JLabel("Postal Code: ");
//        postalCodeLabel.setLabelFor(postalCode);
        newProfile.add(postalCodeLabel);
        newProfile.add(postalCode);

        JTextField province = new JTextField(20);
        JLabel provinceLabel = new JLabel("Province: ");
        provinceLabel.setLabelFor(province);
        newProfile.add(provinceLabel);
        newProfile.add(province);

        JTextField email = new JTextField(20);
        JLabel emailLabel = new JLabel("Email: ");
        emailLabel.setLabelFor(email);
        newProfile.add(emailLabel);
        newProfile.add(email);

        JTextField phone = new JTextField(20);
        JLabel phoneLabel = new JLabel("Phone number: ");
        phoneLabel.setLabelFor(phone);
        newProfile.add(phoneLabel);
        newProfile.add(phone);

        JTextField cardNumber = new JTextField(20);
        JLabel cardNumberLabel = new JLabel("Card number: ");
        cardNumberLabel.setLabelFor(cardNumber);
        newProfile.add(cardNumberLabel);
        newProfile.add(cardNumber);

        JTextField cardName = new JTextField(20);
        JLabel cardNameLabel = new JLabel("Name on card: ");
        cardNameLabel.setLabelFor(cardName);
        newProfile.add(cardNameLabel);
        newProfile.add(cardName);

        JTextField cardExpiration = new JTextField(15);
        JLabel cardExpirationLabel = new JLabel("Expiration date (MM/YY): ");
        cardExpirationLabel.setLabelFor(cardExpiration);
        newProfile.add(cardExpirationLabel);
        newProfile.add(cardExpiration);

        NumberFormat amountFormat = NumberFormat.getNumberInstance();
        JFormattedTextField cardCVV  = new JFormattedTextField(amountFormat);
        cardCVV.setColumns(20);
        JLabel cardCCVLabel = new JLabel("CVV: ");
        cardCCVLabel.setLabelFor(cardCVV);
        newProfile.add(cardCCVLabel);
        newProfile.add(cardCVV);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(actionEvent -> {
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
            info.resetInfo(info);
            newProfile.setVisible(false);
            cardLayout.show(mainPanel, "homePage");
        });
        newProfile.add(backButton);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(actionEvent -> {
            info.profileName = profileName.getText();
            info.firstName = firstName.getText();
            info.lastName = lastName.getText();
            info.address1 = address1.getText();
            info.city = city.getText();
            info.postalCode = postalCode.getText();
            info.province = province.getText();
            info.email = email.getText();
            info.phoneNumber = phone.getText();
            info.cardNumber = cardNumber.getText();
            info.cardName = cardName.getText();
            info.cardExpiration = cardExpiration.getText();
            info.cardCVV = cardCVV.getText();
            operations.writeToFile(info);
            newProfile.setVisible(false);
        });
        newProfile.add(saveButton);

//        JScrollPane scrollPane = new JScrollPane(newProfile, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
//                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        return newProfile;
    }

    public JPanel getExistingProfile() {
        FileOperations operate = new FileOperations();
        FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT, 120, 10);
        JPanel existingProfile = new JPanel(flowLayout);
//        ArrayList<String> profiles = operate.getProfiles("/Users/amosk/Desktop/profiles");
        ArrayList<String> profiles = operate.getProfiles("/Users/" + System.getProperty("user.name") + "/Desktop/profiles");

        JButton backButton = new JButton("Back");
        backButton.addActionListener(actionEvent -> {
            existingProfile.setVisible(false);
            cardLayout.show(mainPanel, "homePage");
        });
        existingProfile.add(backButton);

        for(String profile: profiles) {
            JButton button = new JButton(profile);
            existingProfile.add(button);
            button.addActionListener(actionEvent -> {
                itemDetails = setItemDetails(profile);
                mainPanel.add(itemDetails, "itemDetails");
                existingProfile.setVisible(false);
                cardLayout.show(mainPanel, "itemDetails");
            });
        }

        return existingProfile;
    }

    public JPanel setItemDetails(String profile) {
        FileOperations operations = new FileOperations();
        FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT, 120, 10);
        JPanel details = new JPanel(flowLayout);

        JTextField productName = new JTextField(20);
        JLabel productLabel = new JLabel("Product link: ");
        productLabel.setLabelFor(productName);
        productLabel.add(productName);
        details.add(productLabel);
        details.add(productName);

        JTextField shoeSize = new JTextField(20);
        JLabel shoeSizeLabel = new JLabel("Shoe size (US): ");
        shoeSizeLabel.setLabelFor(shoeSize);
        shoeSizeLabel.add(shoeSize);
        details.add(shoeSizeLabel);
        details.add(shoeSize);

        JTextField clothingSize = new JTextField(20);
        JLabel clothingSizeLabel = new JLabel("Clothing size: ");
        clothingSizeLabel.setLabelFor(clothingSize);
        clothingSizeLabel.add(clothingSize);
        details.add(clothingSizeLabel);
        details.add(clothingSize);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(actionEvent -> {
            details.setVisible(false);
            cardLayout.show(mainPanel, "existingProfile");
        });
        details.add(backButton);

        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.addActionListener(actionEvent -> {
            ItemInfo itemInfo = new ItemInfo();
            itemInfo.productName = productName.getText();
            itemInfo.shoeSize = shoeSize.getText();
            itemInfo.clothingSize = clothingSize.getText();

            Autocheckout auto = new Autocheckout();
            UserInfo userInfo = operations.getProfileData(profile);
            auto.runAutocheckout(userInfo, itemInfo);
        });
        details.add(checkoutButton);
        return details;
    }

    public JPanel createOptions() {
        FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT, 100, 10);
        JPanel optionsPanel = new JPanel(flowLayout);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(actionEvent -> {
            optionsPanel.setVisible(false);
            cardLayout.show(mainPanel, "homePage");
        });
        optionsPanel.add(backButton);

        JCheckBox headless = new JCheckBox("Headless mode");
        headless.setSelected(false);
        optionsPanel.add(headless);

        headless.addActionListener(actionEvent -> {
            if(headless.isSelected()) {
                options.setHeadless(true);
            }

            else {
                options.setHeadless(false);
            }
        });

        return optionsPanel;
    }
}



