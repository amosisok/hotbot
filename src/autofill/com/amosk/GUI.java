package autofill.com.amosk;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUI implements ActionListener {
    GuiElements guiElements = new GuiElements();
    JFrame frame = new JFrame("Hot Bot (Developed by Amos Ko)");
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
        homePage.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        mainPanel.add(homePage, "homePage");

        newProfilePanel = createNewProfile();
        mainPanel.add(newProfilePanel, "newProfile");

        existingProfilePanel = getExistingProfile();
        mainPanel.add(existingProfilePanel, "existingProfile");

        optionsPanel = createOptions();
        mainPanel.add(optionsPanel, "options");

        JLabel mainImage = new JLabel(new ImageIcon(getClass().getResource("/hotbot.jpg")));
        gbc.gridy = 0;
        homePage.add(mainImage, gbc);

        JLabel title = new JLabel("Hot Bot");
        gbc.gridy = 1;
        title.setFont(new Font("Helvetica Neue", Font.PLAIN, 25));
        homePage.add(title, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        JButton newProfileButton = new JButton("Create new profile");
        gbc.gridy = 2;
        homePage.add(newProfileButton, gbc);

        newProfileButton.addActionListener(actionEvent -> {
            cardLayout.show(mainPanel, "newProfile");
        });

        gbc.gridy = 3;
        JButton existingProfileButton = new JButton("Use existing profile");
        homePage.add(existingProfileButton, gbc);

        existingProfileButton.addActionListener(actionEvent -> {
            mainPanel.remove(existingProfilePanel);
            mainPanel.add(getExistingProfile(), "existingProfile");
            cardLayout.show(mainPanel, "existingProfile");
        });

        JButton optionsButton = new JButton("Options");
        gbc.gridy = 4;
        homePage.add(optionsButton, gbc);
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

            if(guiElements.areTextFieldsEmpty(textFields)) {
                ImageIcon errorImage =  new ImageIcon(getClass().getResource("/error1.png"));
                JOptionPane.showMessageDialog(newProfile, "Fill in all fields.", "Error"
                        , JOptionPane.ERROR_MESSAGE, errorImage);
            }

            else {
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
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 14;
        newProfile.add(saveButton, gbc);

        return newProfile;
    }

    public JPanel getExistingProfile() {
        FileOperations operate = new FileOperations();
        JPanel existingProfile = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        ArrayList<String> profiles = operate.getProfiles("/Users/" + System.getProperty("user.name") + "/Desktop/profiles");

        JButton backButton = new JButton("Back");
        backButton.addActionListener(actionEvent -> {
            existingProfile.setVisible(false);
            cardLayout.show(mainPanel, "homePage");
        });
        gbc.gridx = 1;
        existingProfile.add(backButton, gbc);

        int gridYValue = 1;
        for(String profile: profiles) {
            JButton button = new JButton(profile);
            gbc.gridy = gridYValue;
            gbc.gridx = 1;
            existingProfile.add(button, gbc);
            gridYValue++;
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
        for(JLabel label : guiElements.getItemDetailLabels()) {
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

            if(textFields.get(0).getText().isEmpty() && (textFields.get(1).getText().isEmpty()) &&
            textFields.get(2).getText().isEmpty()) {
                ImageIcon errorImage =  new ImageIcon(getClass().getResource("/error1.png"));
                JOptionPane.showMessageDialog(details, "Fill in product link field and at least one size.", "Error"
                        , JOptionPane.ERROR_MESSAGE, errorImage);
            }

            else {
                ItemInfo itemInfo = new ItemInfo();
                itemInfo.productName = textFields.get(0).getText();
                itemInfo.shoeSize = textFields.get(1).getText();
                itemInfo.clothingSize = textFields.get(2).getText();

                Autocheckout auto = new Autocheckout();
                UserInfo userInfo = operations.getProfileData(profile);

                if(userInfo == null) {
                    ImageIcon errorImage =  new ImageIcon(getClass().getResource("/error1.png"));
                    JOptionPane.showMessageDialog(details, "Profile is incomplete. Edit the profile or create a new one.",
                            "Error", JOptionPane.ERROR_MESSAGE, errorImage);
                }

                else {
                    auto.runAutocheckout(userInfo, itemInfo);
                }
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 4;
        details.add(checkoutButton, gbc);
        return details;
    }

    public JPanel createOptions() {
        JPanel optionsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        JButton backButton = new JButton("Back");
        backButton.addActionListener(actionEvent -> {
            optionsPanel.setVisible(false);
            cardLayout.show(mainPanel, "homePage");
        });
        gbc.gridy = 0;
        optionsPanel.add(backButton, gbc);

        JCheckBox headless = new JCheckBox("Headless mode");
        headless.setSelected(false);
        gbc.gridy  = 1;
        optionsPanel.add(headless, gbc);

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



