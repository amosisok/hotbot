package autofill.com.amosk;

public class UserInfo {
    String profileName;
    String firstName;
    String lastName;
    String address1;
    String address2;
    String city;
    String postalCode;
    String province;
    String email;
    String phoneNumber;
    String cardNumber;
    String cardName;
    String cardExpiration;
    String cardCVV;

    public void resetInfo(UserInfo info) {
        info.profileName = "";
        info.firstName = "";
        info.lastName = "";
        info.address1 = "";
        info.address2 = "";
        info.city = "";
        info.postalCode = "";
        info.province = "";
        info.email = "";
        info.phoneNumber = "";
        info.cardNumber = "";
        info.cardName = "";
        info.cardExpiration = "";
        info.cardCVV = "";
    }

    public boolean isFilled(UserInfo info) {
        if(info.firstName.isEmpty() || info.lastName.isEmpty() || info.address1.isEmpty() ||
        info.city.isEmpty() || info.postalCode.isEmpty() || info.province.isEmpty() || info.email.isEmpty()
                || info.phoneNumber.isEmpty() || info.cardNumber.isEmpty() || info.cardName.isEmpty()
                || info.cardExpiration.isEmpty() || info.cardCVV.isEmpty()) {
            return false;
        }

        return true;
    }
}
