package autofill;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Scanner;

public class FileOperations {
    public void writeToFile(UserInfo info) {
//        String dir = "/Users/amosk/Desktop";
        String dir = "/Users/" + System.getProperty("user.name") + "/Desktop";
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
                writer.write(info.firstName + "\n");
                writer.write(info.lastName + "\n");
                writer.write(info.address1 + "\n");
                writer.write(info.city + "\n");
                writer.write(info.postalCode + "\n");
                writer.write(info.province + "\n");
                writer.write(info.email + "\n");
                writer.write(info.phoneNumber + "\n");
                writer.write(info.cardNumber + "\n");
                writer.write(info.cardName + "\n");
                writer.write(info.cardExpiration + "\n");
                writer.write(info.cardCVV + "\n");

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

            for(File file: files) {
                if(file.isFile() && file.exists()) {
                    if(!file.getName().startsWith(".")) {
                        profiles.add(file.getName());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Cannot find directory");
        }
        return profiles;
    }

    public UserInfo getProfileData(String profile) {
        UserInfo info = new UserInfo();
        try {
            File file = new File("/Users/amosk/Desktop/profiles/" + profile);
            Scanner scan = new Scanner(file);
            info.firstName = scan.nextLine();
            info.lastName = scan.nextLine();
            info.address1 = scan.nextLine();
            info.city = scan.nextLine();
            info.postalCode = scan.nextLine();
            info.province = scan.nextLine();
            info.email = scan.nextLine();
            info.phoneNumber = scan.nextLine();
            info.cardNumber = scan.nextLine();
            info.cardName = scan.nextLine();
            info.cardExpiration = scan.nextLine();
            info.cardCVV = scan.nextLine();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Cannot open profile");
        }

        return info;
    }
}
