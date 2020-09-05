package autofill;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class GUI implements ActionListener {
    GuiElements guiElements = new GuiElements();
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
        frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }

    public JPanel createNewProfile() {
        UserInfo info = new UserInfo();
        FileOperations operations = new FileOperations();
        JPanel newProfile = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets= new Insets(10,10,10,10);

        int gridYValue = 0;
        for(JLabel label: guiElements.getNewProfileLabels()) {
            gridYValue++;
            gbc.gridx = 0;
            gbc.gridy = gridYValue;
            newProfile.add(label, gbc);
        }

        gridYValue = 0;
        for(JTextField textField: guiElements.getNewProfileTextFields()) {
            gridYValue++;
            gbc.gridx = 1;
            gbc.gridy = gridYValue;
            newProfile.add(textField, gbc);
        }

        JButton backButton = new JButton("Back");
        backButton.addActionListener(actionEvent -> {
            guiElements.resetNewProfileTextFields();
            info.resetInfo(info);
            newProfile.setVisible(false);
            cardLayout.show(mainPanel, "homePage");
        });
        gbc.gridx = 0;
        gbc.gridy = 14;
        newProfile.add(backButton, gbc);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(actionEvent -> {
            ArrayList<JTextField> textFields = guiElements.getNewProfileTextFields();
            info.profileName = textFields.get(0).getText();
            info.firstName = textFields.get(1).getText();
            info.lastName = textFields.get(2).getText();
            info.address1 = textFields.get(3).getText();
            info.city = textFields.get(4).getText();
            info.postalCode = textFields.get(5).getText();
            info.province = textFields.get(6).getText();
            info.email = textFields.get(7).getText();
            info.phoneNumber = textFields.get(8).getText();
            info.cardNumber = textFields.get(9).getText();
            info.cardName = textFields.get(10).getText();
            info.cardExpiration = textFields.get(11).getText();
            info.cardCVV = textFields.get(12).getText();
            operations.writeToFile(info);
            guiElements.resetNewProfileTextFields();
            newProfile.setVisible(false);
        });
        gbc.gridx = 1;
        gbc.gridy = 14;
        newProfile.add(saveButton, gbc);

        return newProfile;
    }

    public JPanel getExistingProfile() {
        FileOperations operate = new FileOperations();
        FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER );
        JPanel existingProfile = new JPanel(flowLayout);
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
        JPanel details = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets= new Insets(10,10,10,10);

        int gridYValue = 0;
        for(JLabel label : guiElements.getItemDetailJLabels()) {
            gridYValue++;
            gbc.gridx = 0;
            gbc.gridy = gridYValue;
            details.add(label, gbc);
        }

        gridYValue = 0;
        for(JTextField textField: guiElements.getItemDetailTextFields()) {
            gridYValue++;
            gbc.gridx = 1;
            gbc.gridy = gridYValue;
            details.add(textField, gbc);
        }

        JButton backButton = new JButton("Back");
        backButton.addActionListener(actionEvent -> {
            details.setVisible(false);
            guiElements.resetItemDetailTextFields();
            cardLayout.show(mainPanel, "existingProfile");
        });
        gbc.gridx = 0;
        gbc.gridy = 4;
        details.add(backButton, gbc);

        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.addActionListener(actionEvent -> {
            ArrayList<JTextField> textFields = guiElements.getItemDetailTextFields();
            ItemInfo itemInfo = new ItemInfo();
            itemInfo.productName = textFields.get(0).getText();
            itemInfo.shoeSize = textFields.get(1).getText();
            itemInfo.clothingSize = textFields.get(2).getText();

            Autocheckout auto = new Autocheckout();
            UserInfo userInfo = operations.getProfileData(profile);
            auto.runAutocheckout(userInfo, itemInfo);
        });
        gbc.gridx = 1;
        gbc.gridy = 4;
        details.add(checkoutButton, gbc);
        return details;
    }

    public JPanel createOptions() {
        FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER);
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



