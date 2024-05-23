package Team_7;

public class Person {
    private String Name, Address, Phone_numbers;

    public Person (String name, String address, String phone_numbers) {
        this.Name = name;
        this.Address = address;
        this.Phone_numbers = phone_numbers;
    }

    public String getName() {
        return Name;
    }

    public String getAddress() {
        return Address;
    }

    public String getPhone_numbers() {
        return Phone_numbers;
    }

}
