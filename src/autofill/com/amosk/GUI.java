package autofill.com.amosk;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUI implements ActionListener {
    GuiElements guiElements = new GuiElements();
    FileOperations operations = new FileOperations();
    Image applicationImage = new ImageIcon(getClass().getResource("/hotbot.jpg")).getImage();
    JFrame frame = new JFrame("Hot Bot (Developed by Amos Ko)");
    JPanel mainPanel = new JPanel();
    JPanel homePage = new JPanel();
    JPanel itemDetails;
    JPanel newProfilePanel;
    JPanel existingProfilePanel;
    JPanel optionsPanel;
    JPanel manageProfilesPanel;
    JPanel manageProfilePanel;
    JPanel editProfilePanel;
    CardLayout cardLayout = new CardLayout();
    Options options = Options.getInstance();

    private static int CHECKOUTOPTION = 1;
    private static int EDITPROFILEOPTION = 2;

    GUI() {
        options.setHeadless(false);
        mainPanel.setLayout(cardLayout);
        homePage.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        mainPanel.add(homePage, "homePage");

        newProfilePanel = createNewProfile(false, null);
        mainPanel.add(newProfilePanel, "newProfile");

        existingProfilePanel = getExistingProfile(CHECKOUTOPTION);
        mainPanel.add(existingProfilePanel, "existingProfile");

        optionsPanel = createOptions();
        mainPanel.add(optionsPanel, "options");

        manageProfilesPanel = getExistingProfile(EDITPROFILEOPTION);
        mainPanel.add(manageProfilesPanel, "manageProfiles");

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
            mainPanel.remove(newProfilePanel);
            newProfilePanel = createNewProfile(false, null);
            mainPanel.add(newProfilePanel, "newProfile");
            cardLayout.show(mainPanel, "newProfile");
        });

        gbc.gridy = 3;
        JButton existingProfileButton = new JButton("Use existing profile");
        homePage.add(existingProfileButton, gbc);
        existingProfileButton.addActionListener(actionEvent -> {
            mainPanel.remove(existingProfilePanel);
            mainPanel.add(getExistingProfile(1), "existingProfile");
            cardLayout.show(mainPanel, "existingProfile");
        });

        gbc.gridy = 4;
        JButton manageProfilesButton = new JButton("Manage profiles");
        homePage.add(manageProfilesButton, gbc);
        manageProfilesButton.addActionListener(actionEvent -> {
            mainPanel.remove(manageProfilesPanel);
            mainPanel.add(getExistingProfile(2), "manageProfiles");
            cardLayout.show(mainPanel, "manageProfiles");
        });


        JButton optionsButton = new JButton("Options");
        gbc.gridy = 5;
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

    public JPanel createNewProfile(boolean editProfile, UserInfo profileToEdit) {
        UserInfo info = new UserInfo();
        JPanel newProfile = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets= new Insets(10,10,10,10);
        ArrayList<JTextField> textFields = guiElements.getNewProfileTextFields();

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
           if(!editProfile) {
               guiElements.resetNewProfileTextFields();
               info.resetInfo(info);
               newProfile.setVisible(false);
               cardLayout.show(mainPanel, "homePage");
           }

           else {
                newProfile.setVisible(false);
                cardLayout.show(mainPanel, "existingProfile");
           }
        });
        gbc.gridx = 0;
        gbc.gridy = 14;
        newProfile.add(backButton, gbc);

        if(!editProfile && profileToEdit == null) {
            guiElements.resetNewProfileTextFields();
        }

        else if(editProfile && profileToEdit != null) {
            textFields.get(0).setText(profileToEdit.profileName);
            textFields.get(1).setText(profileToEdit.firstName);
            textFields.get(2).setText(profileToEdit.lastName);
            textFields.get(3).setText(profileToEdit.address1);
            textFields.get(4).setText(profileToEdit.city);
            textFields.get(5).setText(profileToEdit.postalCode);
            textFields.get(6).setText(profileToEdit.province);
            textFields.get(7).setText(profileToEdit.email);
            textFields.get(8).setText(profileToEdit.phoneNumber);
            textFields.get(9).setText(profileToEdit.cardName);
            textFields.get(10).setText(profileToEdit.cardNumber);
            textFields.get(11).setText(profileToEdit.cardExpiration);
            textFields.get(12).setText(profileToEdit.cardCVV);
        }


        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(actionEvent -> {
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
                info.cardName = textFields.get(9).getText();
                info.cardNumber = textFields.get(10).getText();
                info.cardExpiration = textFields.get(11).getText();
                info.cardCVV = textFields.get(12).getText();

                if(!guiElements.isNumber(info.phoneNumber) || !guiElements.isNumber(info.cardNumber) ||
                        !guiElements.isNumber(info.cardCVV)) {
                    ImageIcon errorImage =  new ImageIcon(getClass().getResource("/error1.png"));
                    JOptionPane.showMessageDialog(newProfile, "Please fix highlighted fields.", "Error"
                            , JOptionPane.ERROR_MESSAGE, errorImage);
                }

                else {
                    operations.writeToFile(info);
                    guiElements.resetNewProfileTextFields();
                    newProfile.setVisible(false);

                    if(editProfile) {
                        cardLayout.show(mainPanel, "existingProfile");
                    }
                }

            }
        });
        gbc.gridx = 1;
        gbc.gridy = 14;
        newProfile.add(saveButton, gbc);
        return newProfile;
    }


    public JPanel getExistingProfile(int option) {
        JPanel existingProfile = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        ArrayList<String> profiles = operations.getProfiles("/Users/" + System.getProperty("user.name") + "/Desktop/profiles");

        JLabel profileTitle = new JLabel();
        if(option == CHECKOUTOPTION) {
            profileTitle = new JLabel("Use existing profiles");
        }

        else if(option == EDITPROFILEOPTION) {
            profileTitle = new JLabel("Manage profiles");
        }

        profileTitle.setFont(new Font("Helvetica Nue", Font.PLAIN, 20));
        gbc.gridy = 0;
        existingProfile.add(profileTitle, gbc);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(actionEvent -> {
            existingProfile.setVisible(false);
            cardLayout.show(mainPanel, "homePage");
        });
        gbc.gridy = 1;
        existingProfile.add(backButton, gbc);

        int gridYValue = 2;
        for(String profile: profiles) {
            JButton button = new JButton(profile);
            gbc.gridy = gridYValue;
            gbc.gridx = 0;
            existingProfile.add(button, gbc);
            gridYValue++;
            button.addActionListener(actionEvent -> {
                if(option == CHECKOUTOPTION) {
                    itemDetails = setItemDetails(profile);
                    mainPanel.add(itemDetails, "itemDetails");
                    existingProfile.setVisible(false);
                    cardLayout.show(mainPanel, "itemDetails");
                }

                else if(option == EDITPROFILEOPTION) {
                    manageProfilePanel = manageProfile(profile);
                    mainPanel.add(manageProfilePanel, "existingProfile");
                    existingProfile.setVisible(false);
                    cardLayout.show(mainPanel, "existingProfile");
                }
            });
        }

        return existingProfile;
    }

    public JPanel setItemDetails(String profile) {
        JPanel details = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets= new Insets(10,10,10,10);

        JLabel profileTitle = new JLabel(profile);
        profileTitle.setFont(new Font("Helvetica Nue", Font.BOLD, 20));
        gbc.gridx = 0;
        details.add(profileTitle, gbc);

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

                if(!itemInfo.shoeSize.isEmpty() && !itemInfo.clothingSize.isEmpty()) {
                    ImageIcon errorImage =  new ImageIcon(getClass().getResource("/error1.png"));
                    JOptionPane.showMessageDialog(details, "Please only fill one size field for your item.",
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

    public JPanel manageProfile(String profile) {
        JPanel managedProfile = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel profileTitle = new JLabel(profile);
        profileTitle.setFont(new Font("Helvetica Nue", Font.BOLD, 20));
        gbc.gridy = 0;
        managedProfile.add(profileTitle, gbc);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(actionEvent -> {
            mainPanel.remove(manageProfilePanel);
            existingProfilePanel = getExistingProfile(2);
            mainPanel.add(manageProfilesPanel);
            managedProfile.setVisible(false);
            cardLayout.show(mainPanel, "manageProfiles");
        });
        gbc.gridy = 1;
        managedProfile.add(backButton, gbc);

        JButton editProfileButton = new JButton("Edit profile");
        gbc.gridy = 2;
        managedProfile.add(editProfileButton, gbc);
        editProfileButton.addActionListener(actionEvent -> {
            UserInfo userInfo = operations.getProfileData(profile);
            editProfilePanel = createNewProfile(true, userInfo);
            mainPanel.add(editProfilePanel, "editProfile");
            managedProfile.setVisible(false);
            cardLayout.show(mainPanel, "editProfile");
        });

        JButton deleteProfileButton = new JButton("Delete profile");
        gbc.gridy = 3;
        managedProfile.add(deleteProfileButton, gbc);
        deleteProfileButton.addActionListener(actionEvent -> {

            int result = JOptionPane.showConfirmDialog(managedProfile, "Do you want to delete " + profile + "?",
                    "Delete profile", JOptionPane.YES_NO_OPTION);

            if(result == JOptionPane.YES_OPTION) {
                operations.deleteProfile(profile);
                managedProfile.setVisible(false);
                cardLayout.show(mainPanel, "homePage");
            }

        });

        return managedProfile;
    }

    public JPanel createOptions() {
        JPanel optionsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);

        JLabel profileTitle = new JLabel("Options");
        profileTitle.setFont(new Font("Helvetica Nue", Font.PLAIN, 20));
        gbc.gridy = 0;
        optionsPanel.add(profileTitle, gbc);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(actionEvent -> {
            optionsPanel.setVisible(false);
            cardLayout.show(mainPanel, "homePage");
        });
        gbc.gridy = 1;
        optionsPanel.add(backButton, gbc);

        JCheckBox headless = new JCheckBox("Headless mode");
        headless.setSelected(false);
        gbc.gridy  = 2;
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



