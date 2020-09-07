package autofill.com.amosk;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileOperations {
    String dir = "/Users/" + System.getProperty("user.name") + "/Desktop";
    public void writeToFile(UserInfo info) {
//        String dir = "/Users/" + System.getProperty("user.name") + "/Desktop";
        try {
            File newDir = new File(dir + "/profiles");
            boolean dirCreated = newDir.mkdir();
            if(!dirCreated) {
                System.err.println("Cannot create directory");
            }
            String fileName = dir + "/profiles/" + info.profileName;
            File newFile = new File(fileName);

            boolean fileCreated = newFile.createNewFile();
            if(!fileCreated) {
                System.err.println("File already exists");
            }

            try {
                FileWriter writer = new FileWriter(fileName);
                writer.write("First Name: " + info.firstName + "\n");
                writer.write("Last Name: " + info.lastName + "\n");
                writer.write("Address Line 1: " + info.address1 + "\n");
                writer.write("City: " + info.city + "\n");
                writer.write("Postal Code: " + info.postalCode + "\n");
                writer.write("Province: " + info.province + "\n");
                writer.write("Email: " + info.email + "\n");
                writer.write("Phone Number: " + info.phoneNumber + "\n");
                writer.write("Card Number: " + info.cardNumber + "\n");
                writer.write("Card Name: " + info.cardName + "\n");
                writer.write("Card Expiration: " + info.cardExpiration + "\n");
                writer.write("Card CVV: " + info.cardCVV + "\n");

                writer.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getProfiles(String path) {
        ArrayList<String> profiles = new ArrayList<>();
        try {
            File dir = new File(path);
            File[] files = dir.listFiles();

            if(files == null) {
                System.err.println("No files found");
            }

            else {
                for(File file: files) {
                    if(file.isFile() && file.exists()) {
                        if(!file.getName().startsWith(".")) {
                            profiles.add(file.getName());
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Directory already exists");
        }
        return profiles;
    }

    public UserInfo getProfileData(String profile) {
        UserInfo info = new UserInfo();
        try {
            File file = new File(dir + "/profiles/" + profile);
            Scanner scan = new Scanner(file);
            info.firstName = scan.nextLine().substring("First Name: ".length());
            info.lastName = scan.nextLine().substring("Last Name: ".length());
            info.address1 = scan.nextLine().substring("Address 1: ".length());
            info.city = scan.nextLine().substring("City: ".length());
            info.postalCode = scan.nextLine().substring("Postal Code: ".length());
            info.province = scan.nextLine().substring("Province: ".length());
            info.email = scan.nextLine().substring("Email: ".length());
            info.phoneNumber = scan.nextLine().substring("Phone Number: ".length());
            info.cardNumber = scan.nextLine().substring("Card Number: ".length());
            info.cardName = scan.nextLine().substring("Card Name".length());
            info.cardExpiration = scan.nextLine().substring("Card Expiration".length());
            info.cardCVV = scan.nextLine().substring("Card CVV: ".length());

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Cannot open profile");
        }

        if(!info.isFilled(info)) {
            return null;
        }

        return info;
    }

    public void deleteProfile(String profile) {
        try {
            File file = new File(dir + "/profiles/" + profile);

            boolean deleted = file.delete();
            if(!deleted) {
                System.err.println("Cannot delete profile");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
