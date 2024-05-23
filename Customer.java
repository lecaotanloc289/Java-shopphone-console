package Team_7;

public class Customer extends Person {

    protected static int numberOfCustomer = 0;
    public Customer (String name, String phone_number, String address) {
        super(name, address, phone_number);
        ++numberOfCustomer;
    }

     public int getNumberOfCustomer() {
        return numberOfCustomer;
     }

    @Override
    public String toString () {
        return getName() + "\n"
                + getAddress() + "\n"
                + getPhone_numbers() + "\n"
                + getNumberOfCustomer();

    }

}



