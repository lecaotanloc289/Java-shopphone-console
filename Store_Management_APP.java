package Team_7;

import java.io.*;
import java.util.Scanner;

public class Store_Management_APP {
    static Store_Management SM = new Store_Management();
    static Scanner typing = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        Read_data("Staff");
        Read_data("Product");
        Read_data("Supplier");
        if (SM.Login()) {
            Menu();
        }
        writeData("Product");
    }
    public static void Read_data (String document_type) {
        try {
            FileReader FR = new FileReader( document_type+".txt");
            BufferedReader BR = new BufferedReader(FR);
            String line = "";
            String [] temp;

            while ((line = BR.readLine()) != null) {
                temp = line.split(",");
                switch (document_type) {
                    case "Staff" -> {
                        int id = Integer.parseInt(temp[0]);
                        String password = temp[1];
                        String name = temp[2];
                        String address = temp[3];
                        String phone_number = temp[4];
                        String cmt = temp[5];
                        Staff staff = new Staff(id, password, name, phone_number, cmt, address);
                        SM.add(staff);
                        break;
                    }
                    case "Supplier" -> {
                        int id = Integer.parseInt(temp[0]);
                        String brand = temp[1],
                                name = temp[2],
                                address = temp[3],
                                phone_number = temp[4],
                                country = temp[5],
                                import_file = temp[6];
                        Supplier supplier = new Supplier(id, brand, name, address, phone_number, country, import_file);
                        SM.add(supplier);
                        break;
                    }
                    case "Product" -> {
                        int id = Integer.parseInt(temp[0]);
                        String product_name = temp[1];
                        String brand = temp[2];
                        int quantity = Integer.parseInt(temp[3]);
                        double cost = Double.parseDouble(temp[4]);
                        String country = temp[5];
                        Product product = new Product(id, product_name, brand, quantity, cost, country);
                        SM.add(product);
                        break;
                    }
                }
            }
            FR.close();
            BR.close();
        }
        catch (Exception e)
        {
            System.out.print("");
            //System.out.println("Lỗi NumberFormatException đã được xử lý.");
        }
    }
    public static void writeData(String Datatype) {
        try {
            FileWriter fw = new FileWriter(Datatype + ".txt");
            BufferedWriter bw = new BufferedWriter(fw);

            int id;

            switch (Datatype) {
                case "Staff":
                    for (var staff : SM.personList) {
                        if (staff instanceof Staff) {
                            id = ((Staff) staff).getStaffID();
                            bw.write("" + id + "," + ((Staff) staff).getPassword() + ", "
                                    + staff.getName() + ", " + staff.getPhone_numbers() + ", " + ((Staff) staff).getCMT()
                                    + "," + staff.getAddress());
                            bw.newLine();
                        }
                    }
                    break;
                case "Supplier":
                    for (var supplier : SM.suppliers) {
                        if (supplier != null) {
                            id = ((Supplier) supplier).getSupplier_id();
                            bw.write("" + id + ", " + ((Supplier) supplier).getBrand() + ", "
                                    + supplier.getName() + ", " + supplier.getAddress() + ", " + supplier.getPhone_numbers()
                                    + "," + ((Supplier) supplier).getCountry() + ", " + ((Supplier) supplier).getImport_file());
                            bw.newLine();
                        }
                    }
                    break;
                case "Product":
                    for (var product : SM.products) {
                        bw.write(product.getId_product()+ "," + product.getProduct_name() +
                                "," + product.getBrand() + "," + product.getQuantity() + ","
                                + product.getCost() + "," + product.getCountry());
                        bw.newLine();
                    }

                    break;
            }

            bw.close();
            fw.close();
        } catch (IOException e) {
            System.out.println("File not found or writing error");
        }
    }



        public static void Menu () throws IOException {
        typing.nextLine();
        int choice;
        do {
            System.out.print("""
                    \n======= MENU CHỨC NĂNG =======
                    1. Thông tin nhân viên.
                    2. Thông tin nhà cung cấp.
                    3. Thông tin sản phẩm.
                    4. Bán hàng.
                    5. Bảo hành và sửa chữa.
                    6. Nhập hàng.
                    7. Chính sách đổi trả.
                    8. Báo cáo kết quả cuối ngày.
                    9. Đăng xuất.
                    Vui lòng chọn chức năng: 
                    """);
            choice = typing.nextInt();

            switch (choice) {
                case 1 -> SM.listStaff();
                case 2 -> SM.listSupplier();
                case 3 -> SM.listProducts();
                case 4 -> System.out.println(SM.Sale());
                case 5 -> System.out.println(SM.RepairAndInsurance_Services());
                case 6 -> {
                    SM.listSupplier();

                    System.out.print("Bạn muốn nhập hàng từ id nào: ");
                    var id = typing.nextInt();

                    System.out.print("Thương hiệu: ");typing.nextLine();
                    var brand = typing.nextLine();
                    SM.Import_product(id, brand);
                }
                case 7 -> SM.Return_Policy();
                case 8 -> System.out.println(SM.Report());
                case 9 -> {}
            }
        } while (choice != 9);
    }
}
