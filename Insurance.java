package Team_7;

import java.util.Scanner;
import java.util.zip.DeflaterOutputStream;

public class Insurance {
    private String Name, PhoneNumber, TypeOfService;
    private int numberOfCustomer = 0;

    public Insurance (String name, String phoneNumber, String typeOfService) {
        this.Name = name;
        this.PhoneNumber = phoneNumber;
        this.TypeOfService = typeOfService;
        numberOfCustomer++;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getNumberOfCustomer() {
        return numberOfCustomer;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public String getTypeOfService() {
        return TypeOfService;
    }


}