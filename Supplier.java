package Team_7;

import java.util.Scanner;

public class Supplier  extends Person implements Set{
    private int Supplier_id;
    private String Brand, Country, Import_file;


    public Supplier (int supplier_id, String brand, String name, String address, String phone_number,  String country, String import_file) {
        super(name, address, phone_number);
        Supplier_id = supplier_id;
        Brand = brand;
        Country = country;
        Import_file = import_file;

    }
    public String getBrand() {
        return Brand;
    }
    public String getCountry() {
        return Country;
    }
    public int getSupplier_id() { return Supplier_id; }
    public String getImport_file() {
        return Import_file;
    }
    public void setImport_file (String import_file) {
        Import_file = import_file;
    }

    @Override
    public String toString () {
        return  getSupplier_id() + ", "
                + getBrand() + ", "
                + getName() + ", "
                + getAddress() + ", "
                + getCountry() + ", "
                + getPhone_numbers();
    }

    @Override
    public void add(Object object) {
        object = (Supplier) object;
    }

    @Override
    public void update(Object object) {
        object = (Staff) object;
    }

    @Override
    public void remove(Object object) {
        object = (Staff) object;
    }
}
