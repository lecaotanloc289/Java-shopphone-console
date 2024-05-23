package Team_7;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Store_Management {

    protected List <Person> personList;
    // Sử dụng để lưu đối tượng Staff, Supplier,
    // protected để có thể truy cập ở các hàm bên Store_Management_APP
    protected List <Product> products;
    // Sử dụng để lưu danh sách các đối tượng là sản phẩm mình sẽ bán
    private List <Product> buy_list, products_import;
    // Sử dụng để lưu danh sách các đối tượng là sản phẩm người dùng mua: Buy_list
    // Sản phẩm nhập hàng: products_import

    // Giải thích tại sao phải tạo danh sách như thế này: Mình phải tạo các đội tươợng theo danh sách
    // mua để tính tổng tiền của tất cả sản phẩm và lấy tên, thông tin mua hàng của sản phẩm
    // khách cần mua nếu không sẽ không tính được tổng tiền hoặc lưu lại được danh sách sp mà ngta chọn
    protected List<Supplier> suppliers;
    // Sử dụng để lưu nhà cung cấp, mỗi nhà cung cấp sẽ có những thông tin cần thiết cho nhập hàng
    private int login_times = 0;
    // Sử dụng để khai báo số lần đăng nhập, khi đăng nhâập sai mật khẩu sẽ tăng lên 1, 3 lần sẽ xuất ra
    // khóa tài khoản vì đăng nhập sai
    private static final double TARGET = 100000;
    // Mục tiêu kinh doanh của cửa hàng, dùng để tính báo cáo cuối ngày
    private double TOTAL = 0;
    // Tổng tiền bán được, thông qua cộng dồn các hóa đơn, biến này sẽ lưu giá trị cho đến khi đăng xuất

    Store store = new Store ("101", "CUA HANG IT02"
            ,"So 7 Dong Xoai, P13, Tan Binh, TP.HCM"
            , "1900 1100" );
    // Đối tượng cửa hàng do mình khai báo thông tin, mình muốn trong hóa đơn
    // ghi địa chỉ của mình ở đâu thì mình ghi ở đó, rồi dựa vào các biến thuộc tính của nó
    // để truy cập đến những biến mà mình cần, vd getAddress
    Staff staff;
    // Đối tượng này tạo ra để khi so sánh id mình tìm thấy đối tượng mình cần trong danh sách personList
    // rồi cho địa chỉ của đối tượng đó bằng đối tượng này để có thể truy cập tên và id trong bill
    Customer customer;
    // Sử đụng để tạo một đối tượng khách hàng, cần thiết cho cái bill luôn
    // Mình tạo đối tượng xong thì hỏi thông tin khách hàng rồi thêm vào đối tượng customer này
    // Sau đó truy cập các thông tin qua phương thức get được viết trong class Customer
    Insurance insurance;
    // Tương tự như Customer, mình cũng dùng nó cho đối tượng khách hàng
    // Có thể dùng customer cho bảo hành và sửa chữa luôn, mình sẽ dùng cái trường địa chỉ của customer
    // để lưu loại dịch vụ, cái này tách ra để cho nó đủ class th :>
    Scanner typing = new Scanner(System.in);
    // Nhập chuẩn của Java
    public Store_Management ()  {
        personList = new ArrayList<>();
        products = new ArrayList<>();
        buy_list = new ArrayList<>();
        suppliers = new ArrayList<>();
        products_import = new ArrayList<>();
        // Tạo danh sách các đối tượng thông qua array list
    }

    // Tính đa hình, một phương thức có các biến đầu vào khác nhau
    // sẽ tùy theo biến đó thuộc kiểu dữ liệu nào mà xử lý theo kiểu dữ liệu đó
    public void add (Supplier supplier){
        suppliers.add(supplier);
    }

    public void add (Person person) {
        personList.add(person);
    }

    public void add (Product product) {
        products.add(product);
    }

    public boolean remove (int id) {
        for (var person: personList) {
            if (person instanceof Staff)
            {
                if (((Staff) person).getStaffID() == id) {
                    personList.remove(person);
                    return true;
                }
            }
            else if (person instanceof Supplier)
            {
                if (((Supplier) person).getSupplier_id() == id) {
                    personList.remove(person);
                    return true;
                }
            }

        }
        return false;
    }
    public  boolean remove (int id_product, String brand) {
        for (var product : products)
        {
            if (product.getId_product() == id_product && brand.equals(product.getBrand())) {
                products.remove(product);
                return true;
            }
        }
        return false;
    }

    public boolean update (int id, Person person) {
        if (remove(id)) {
            add(person);
            return true;
        }
        return false;
    }

    public boolean update (int id_product, Product product) {
        if (remove(id_product, product.getBrand())) {
            add(product);
            return true;
        }
        return false;
    }
    public boolean update (Product sale_product, Product buy_product) {
        if (sale_product.getId_product() == buy_product.getId_product()) {
            sale_product.setQuantity(sale_product.getQuantity() - buy_product.getQuantity());
            return true;
        }
        return false;
    }

    public void listStaff () {
        for (var person: personList)
        {
            if (person instanceof Staff)
            {
                System.out.println(person);
            }
        }
    }

    public void listSupplier () {
        for (var person: suppliers)
        {
            if (person != null)
            {
                System.out.println(person);
            }
        }
    }

    public void listProducts () {
        for (var product: products)
        {
            if (product != null)
            {
                System.out.println(product);
            }
        }
    }

    public boolean search_staff_id (int id) {
        // Tìm trong danh sách có id nhân viên mà mình cần hay không
        //nếu có trả về true, ko trả về false
        for (var person : personList) {
            if (person instanceof Staff && ((Staff) person).getStaffID() == id) {
                this.staff = (Staff) person;
                return true;
            }
        }
        return false;
    }
    public String getTime () {
        // Lấy thời gian hiện tại
        LocalDateTime Date_and_time_now = LocalDateTime.now();

        // Format lại thời gian theo cách mình muốn
        DateTimeFormatter Date_time_format = DateTimeFormatter.ofPattern("dd-MM-yyyy              HH:mm:ss");

        // Trả về thời gian là chuỗi theo kiểu đã định dạng.
        return Date_and_time_now.format(Date_time_format);
    }
    public void Get_customer_information () {
        String customer_name, phone_number, address;

        System.out.print("Khách hàng: ");
        typing.nextLine();
        customer_name = typing.nextLine();

        System.out.print("Số điện thoại: ");
        phone_number = typing.nextLine();

        System.out.print("Địa chỉ: ");
        address = typing.nextLine();

        customer = new Customer(customer_name,phone_number,address);
    }
    public boolean search_product_id (int id, int quantity) {
        // Sử dụng dể tìm id sản phẩm và số lượng sản phẩm cần mua
        for (var products: products) {
            // cho biến chạy từ sản phẩm đầu tiên tới sản phẩm cuối cùng
            // trong danh sách những sản phẩm mà mình bán
            if (id == products.getId_product()) {
                // nếu id sản phẩm của mình có trong danh sách sp thì tiếp tục so sánh số lượng
                if (quantity > products.getQuantity()) {
                    return false;
                    //nếu không đủ số lượng, trả về false và ko làm gì hết
                }
                else {
                    Product pd = new Product(products.getId_product(),
                            products.getProduct_name(),
                            products.getBrand(),
                            quantity,
                            products.getCost(),
                            products.getCountry());
                    // Cách để xử lý số lượng hàng hóa sau khi mua/ xuất hóa đơn là mình sẽ
                    // tạo một đối tượng mới chỉ có số lượng sau khi đặt điều kiện < quantity của sản phẩm đang
                    // có thì mình sẽ thêm nó vào một danh sách khác, rồi set lại số lượng của sản phẩm
                    // có là xong :>
                    products.setQuantity(products.getQuantity() - quantity);

                    // Lúc hoàn tât cần triển khai xem nó có sửa số lượng của product bên danh sách bán hay không
                    // Sau khi thực hiện in hóa đơn thành công phải sửa lại số lượng hàng hóa còn lại.
                    // Nếu hàng hóa nào có số lượng bằng không phải set trạng thái hết hàng.
                    buy_list.add(pd);
                    return true;
                }
            }
        }
        System.out.println("Không tìm thấy sản phẩm!");
        return false;
    }

    public void Return_Policy () {
        System.out.println("""
                BẢO HÀNH CÓ CAM KÊT TRONG VÒNG 12 THÁNG
                  *  Chỉ áp dụng cho sản phẩm chính, KHÔNG áp dụng cho phụ kiện đi kèm sản phẩm chính.
                  *  Bảo hành trong vòng 15 ngày (tính từ ngày CH nhận máy ở trạng thái lỗi và đến ngày
                    gọi khách hàng ra lấy lại máy đã bảo hành).
                  *  Sản phẩm không bảo hành lại lần 2 trong 30 ngày kể từ ngày máy được bảo hành xong.
                  *  Nếu CH vi phạm cam kết (bảo hành quá 15 ngày hoặc phải bảo hành lại sản phẩm lần nữa
                    trong 30 ngày kể từ lần bảo hành trước), Khách hàng được áp dụng phương thức
                  *  Hư gì đổi nấy ngay và luôn hoặc Hoàn tiền với mức phí giảm 50%.
                  *  Từ tháng thứ 13 trở đi không áp dụng bảo hành cam kết, chỉ áp dụng bảo hành hãng (nếu có).
                """);
    }
    public void Import_product (int id, String brand)
            // Nhập hàng
    {
        // dùng vòng for tìm id

        // Khởi tạo biến lưu tên file nhập hàng
        String import_file = null;

        // tìm trong danh sách nhà cung cấp nếu có id mình muốn nhập hàng và thương hiệu
        // giôống thương hiệu mình nhập hàng thì gán biến string bằng tên file
        for (var supplier : suppliers) {
            if (id == supplier.getSupplier_id() && brand.equalsIgnoreCase(supplier.getBrand())) {
                import_file = supplier.getImport_file();

                // THông báo tìm thấy file nhập hàng
                System.out.println("Tìm thấy file nhập hàng");
                System.out.println("file nhập hàng là: " + import_file);
            }
        }
        // tạo một cái try catch để khi đọc file không có hoặc không tồn tại thì nó ko bị lỗi.
        try {
            assert import_file != null;
            FileReader FR = new FileReader(import_file);
            BufferedReader BR = new BufferedReader(FR);
            String line = "";
            String[] temp;

            // Giống như các đọc file thầy hướng dẫn

            while ((line = BR.readLine()) != null) {
                temp = line.split(",");
                int ID = Integer.parseInt(temp[0]);
                String product_name = temp[1];
                String Brand = temp[2];
                int quantity = Integer.parseInt(temp[3]);
                double cost = Double.parseDouble(temp[4]);
                String country = temp[5];

                // tạo một đối tượng product và thêm các biến của nó vào có thứ tự
                var product_import = new Product(ID, product_name, Brand,quantity, cost, country);

                // sau đó thêm đối tượng này vào danh sách hàng hóa nhập vào
                products_import.add(product_import);
            }

            //  nêếu sản phẩm nhập vào có id giống id sản phẩm mình đang bán thì set số lượng và giá lại
            for (var a: products_import) {
                int count = 0;
                for (var b: products) {

                    if (a.getId_product() == b.getId_product() && a.getBrand().equals(b.getBrand())) {
                        count++;
                        b.setQuantity(b.getQuantity() + a.getQuantity());
                        b.setCost(a.getCost());
                    }
                }
                // nếu sản phẩm không giống thì thêm nó vào danh sách bán hàng hóa mà mình sẽ bán.
                if (count == 0) products.add(a);
            }
            // Dùng một cái trạng thái cho nhập hàng, nếu mà chưa nhập thi trạng thái của
            // supplier laf true, mình chỉ cần if status là xong,
            // trạng thái là true thì mới nhập, nhập xong set false
            // tìm có bắt đầu nhập hàng và thông báo nhập thành công

            // tìm không có thông bóa cho người dùng biết.
            System.out.println("Nhập hàng thành công");
            //return true;

        }
        catch (Exception e) {
            System.out.println("Nhập hàng không thành công");
        }

        //return false;
    }
    public boolean Login () {
        // So sánh mật khẩu người dùng nhập vào mật khẩu từ file
        // Tìm hiểu cách mật khẩu chuyển thành dấu *

        // Nếu mật khẩu giống thì Thông báo đăng nhập thành công.
        // và có thể lựa chọn và truy cập các chức
        // năng như Bán hàng, nhập hàng, xem lịch sử bán hàng, xem lịch sử nhập hàng

        // Trong chức năng in hóa đơn mình sẽ phải thêm id
        // của người đăng nhập thành công vào trong đó để
        // nó truy cập vào tên và id của nhân viên bán hàng.

        System.out.println("VUI LÒNG ĐĂNG NHẬP");
        // Khai báo ở class nhân viên một trường mật khẩu nữa.

        // Yêu cầu người dùng nhập id nhân viên
        System.out.print("Mã nhân viên: ");
        var id = typing.nextInt();

        // Biến nhận giá trị nhập mật khẩu lại nếu nhập sai.
        String answer = null;
        typing.nextLine();

        // Tìm kiếm mã nhân viên nếu không có thì thông báo ra mã nhân viên không tồn tại
        if (search_staff_id(id))
        {
            do {
                // Yêu cầu nhập mật khẩu để đăng nhập, nếu quá 3 lần khóa tài khoản
                if (login_times >= 3)
                {
                    System.out.println("Tài khoản của bạn đã bị khóa do nhập mật khẩu sai quá 3 lần :(( ");
                    return false;
                }

                System.out.print("Mật khẩu: ");
                var password = typing.nextLine();
                // so sánh mật khẩu nhập vào với mật khẩu mặc định của nhân viên
                // nếu đúng thì thông báo đăng nhập thành công và trả về true
                if (password.equals(staff.getPassword()))
                {
                    System.out.println("Đăng nhập thành công ><");
                    return true;
                }
                else // Sai thì tăng lần đăng nhập lên một và thông báo đăng nhập sai
                // hỏi người dùng có muốn đăng nhập lại ko
                {
                    login_times++;
                    System.out.print("""
                        Sai mật khẩu :<
                        Bạn có muốn nhập lại không?
                        """);
                    answer = typing.nextLine();
                }
            } while (answer.equalsIgnoreCase("Có"));
        }
        else System.out.println("Mã nhân viên không tồn tại :V");
        return false;
    }
    public String Report ()  {
        try {
            LocalDate report_date = LocalDate.now();
            DateTimeFormatter Date_format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            // In ra ngày tháng năm
            String report = "BÁO CÁO DOANH THU NGÀY " + report_date.format(Date_format) + "\n";

            // Chỉ tiêu ngày
            report += "Mục tiêu: " + TARGET + "\n"

                    // Tổng số tiền bán được
                    + "Tổng doanh thu: " + TOTAL + "\n"

                    // Phần trăm chỉ tiêu đạt được
                    + "Đạt " + String.format("%.2f", TOTAL * 100 / TARGET) + "%" + "\n";

            // Luu vào lịch sử báo cáo - ghi thêm ko ghi đè
            saveReport(report);

            // Trả về kết quả báo cáo
            return report;
        }
         catch (IOException e) {
             System.out.println("Chưa có khách hàng");;
        }
        return null;
    }

    public String  RepairAndInsurance_Services () throws  IOException{
        // Tương tự như Sale

        // tạo biến dùng để nhận vào số lượng sp bảo hành hoặc số phần sửa chữa
        int number = 0;

        // Tạo chứa tên các sp bảo hành hoặc tên các bộ phận sửa chữa
        String [] temp = new String[number];
        Double [] cost = new Double[number];

        // biến để lưu bill bảo hành/ sửa chữa
        String repair_bill;

        // tạo các biến lưu tiền nhận, tổng tiền, và tiền thừa trả lại
        double total = 0, money_received, money_return;

        // Lấy thông tin khách hàng
        System.out.print("Tên khách hàng: ");
        typing.nextLine();
        var name = typing.nextLine();

        System.out.print("Số điện thoại: ");
        var phonenumber = typing.nextLine();

        System.out.print("Dịch vụ: ");
        var service = typing.nextLine();

        // Tạo một đối tượng mới có các trường của khách hàng
        insurance = new Insurance(name, phonenumber, service);

        System.out.print("Số thiết bị bảo hành/ bộ phận sửa chữa: ");
        number = typing.nextInt();

        // So sánh if else để thực hiện dịch vụ khác nhau
        if (service.equalsIgnoreCase("Sửa chữa"))
        {
            System.out.println("Chi tiết sửa chữa & giá tiền:");
            for (int i = 0; i < number; i++) {
                System.out.print("Bộ phận " + (i + 1) + ": ");
                typing.nextLine();
                temp [i] = typing.nextLine();

                System.out.print("Giá tiền: ");
                cost [i] = Double.parseDouble(typing.nextLine());
                total += cost[i];
            }
        }
        else if (service.equalsIgnoreCase("Bảo hành")) {
            System.out.println("Chi tiết bảo hành:");
            for (int i = 0; i < number; i++) {
                System.out.print("Thiết bị " + (i + 1) + ": ");
                typing.nextLine();
                temp [i] = typing.nextLine();

                System.out.print("Giá tiền: ");
                cost [i] = Double.parseDouble(typing.nextLine());
                total += cost[i];
            }
        }
        else {
            System.out.println("Dịch vụ hiện chưa được hỗ trợ");
            return null;
        }

        System.out.println("Tổng tiền: " + total);
        System.out.print("Tiền nhận: ");
        money_received = typing.nextDouble();
        if (money_received < total) {
            System.out.println("Xin lỗi quý khách không đủ tiền để thực hiện giao dịch!");
            return null;
        }
        money_return = money_received - total;
        TOTAL += total;
        // Tạo một hóa đơn sửa chữa
        repair_bill = "Số thiết bị sữa chữa hoặc bảo hành: " + number + "\n";
        for (int i = 0; i < number; i++)
        {
            repair_bill += temp[i] + ": " + cost[i] + "\n";
        }

        repair_bill += "===============================\n" +
                "Tổng tiền: " + total + "\n" +
                "Khách đưa: " + money_received + "\n" +
                "Tiền thừa: " + money_return + "\n" +
                "===============================\n";

        repair_bill = "\t******* " + store.getName() +" *******\n"+ store.getAddress() + "\n"
                + "Khách hàng: " + insurance.getName() + " - " + insurance.getPhoneNumber() + "\n\n"
                + "\t\t\tHÓA ĐƠN " + (insurance.getTypeOfService()).toUpperCase() + "\n\n"
                + getTime() + "\n"
                + repair_bill
                + "Số hóa đơn: "+ insurance.getNumberOfCustomer()+ "-" + hashCode() + "\n"
                + "Thu ngân: " + staff.getStaffID() + " - " + staff.getName() + "\n"
                + "Phiếu tính tiền chỉ có giá trị đổi xuất hóa đơn trong ngày" + "\n"
                + "Quý khách liên hệ cửa hàng để xuất hóa đơn điện tử";

        // Lưu vào lịch sử giao dịch và cập nhật biến tổng tiền.
        saveTransaction(repair_bill);
        return repair_bill;
    }
    public String Sale () throws IOException {

        // Lấy thông tin khách hàng,
        // bấm ctrol + chuột lick vào phương thức để coi phương thức đó là cái gì
        Get_customer_information();

        // In ra danh sách hàng hóa mà mình có sau đó cho khách hàng chọn
        listProducts();

        // Tạo biến nhận id product và số lượng (mình chưa biết khach mua bao nhiêu món
        int number, id_product, quantity;
        // Làm sao để không phải giới hạn trước số lần mà mình nhập món hàng đây?

        // ==> Mình sẽ kiểu bán trước, ý là hỏi người ta mua nhiều ít trước rồi mới chốt
        // cho nên là sẽ nhập number rồi đưa nó vào for sau

        double total = 0, money_received, money_return;
        // Khai báo biến total để tính tổng tiền mỗi lượt bán
        // Money_received để nhận tiền khác đưa
        // money_return để nhận giá trị tiền thừa trả lại khách

        System.out.print("Số lượng sản phẩm: ");
        number = typing.nextInt();

        for (int i = 0; i < number; i++) {
            System.out.print("ID sản phẩm " + (i + 1) + ": ");
            id_product = typing.nextInt();

            System.out.print("Số lượng mua: ");
            quantity = typing.nextInt();

            if (search_product_id(id_product, quantity))
            {
                // nếu tìm thấy sản phẩm thì sẽ tạo một đối tượng sp mới có số lượng mà khách haàng muốn mua
                // rồi sau đó thêm đối tượng đó vào danh sách mua
                // set lại số lượng hàng hóa sau khi bán
                System.out.println("");
            }
            else {
                // Nếu sp ko đủ thì in ra thông báo không đủ, và trả lại số lượng nhu cũ
                System.out.println("Số lượng sản phẩm không đủ!");

                /*
                tìm lần lượt các sản phẩm trong danh sách mua
                nếu id sản phẩm đã mua = id sản phẩm đang bán
                set số lượng sp đang bán = sp đang bán cộng sp trong danh sách mua
                (do lúc nãy mình thêm số lượng vào danh sách đã mua nên bây giờ phải
                trả lại số lượng nếu không bán
                 */
                for (var product: buy_list) {
                    for (var products: products) {
                        if (product.getId_product() == products.getId_product()) {

                            // Trả về lại số lượng
                            products.setQuantity(products.getQuantity() + product.getQuantity());

                        }
                    }
                }
                return null;
            }
        }

        // Tạo một danh sách product mà khách hàng mua nữa
        // nếu mà họ mua món nào thì thêm vào danh sách món đó thông qua id

        String product_list = "===============================\n" +
                "\t" + "Tên sản phẩm"+ "\t" + "Số lượng"+ "  " + "Giá" + "  \t" +"Thành tiền\n";
        // Sd for để tính tổng số tiền khách phải trả và
        // cộng tên sp số lượng, giá thành tiền vào trong một biến string
        for (var product: buy_list) {
            product_list += (product.getProduct_name() + "\t"
                    + product.getQuantity() + "\t"
                    + product.getCost() + "\t"
                    + (product.getQuantity() * product.getCost()) + "\n");
            total += product.getCost() * product.getQuantity();
        }


        System.out.println("Tổng tiền: " + total);

        // Tạo một biến nhận vào số tiền khách đưa và tính toán tiền thừa
        System.out.print("Tiền nhận: ");
        money_received = typing.nextDouble();

        if (money_received < total) {
            System.out.println("Xin lỗi quý khách không đủ tiền để thực hiện giao dịch!");

            // nếu không đủ tiền sẽ không thực hiện giao dịch,
            // do đó cũng phải trả lại số lượng hàng hóa đã thêm vào danh sách mua
            for (var product: buy_list) {
                for (var products: products) {
                    if (product.getId_product() == products.getId_product()) {
                        // Trả về lại số lượng
                        products.setQuantity(products.getQuantity() + product.getQuantity());
                    }
                }
            }

            // Cho buy_list: danh sách hàng đã mua là một mảng mới
            // để lần sau mình bán nó sẽ không lưu các đôi tượng mà mình đã bán ở lần đầu.
            // Nếu không nó sẽ bị lỗi logic, có nghĩ là sản phẩm sẽ được cộng dồn từ ngươời mua trước
            // tới người mua sau và tính tiền cho người mua sau
            buy_list = new ArrayList<>();
            return null;
        }

        // biến tính tổng tiền để báo cáo sẽ thực hiện tổng tiền của các hóa đơn đã bán thành công
        TOTAL += total;

        // lúc này bán thành công rồi phai set buy_list
        // về danh sách rỗng nếu không nó bị lỗi giống ở trên
        buy_list = new ArrayList<>();

        // tính tiền thừa
        money_return = money_received - total;

        product_list += "===============================\n" +
                        "Tổng tiền: " + total + "\n" +
                        "Khách đưa: " + money_received + "\n" +
                        "Tiền thừa: " + money_return + "\n" +
                        "===============================\n";

        // in các thông tin khác của chăm sóc khách hàng ... và thông tin của hóa đơn
        // lưu tất cả thông tin vào một cái biến String
        // để ý rằng các đối tượng mà mình đã làm được gọi hết trong class này

        product_list = "******* " + store.getName() +" *******\n"+ store.getAddress() + "\n"
                + "\t\t HÓA ĐƠN BÁN HÀNG \n"
                + "Khách hàng: " + customer.getName() + " - " + customer.getPhone_numbers() + "\n"
                + getTime() + "\n"
                + product_list
                + "Số hóa đơn: "+ customer.getNumberOfCustomer()+ "-" + hashCode() + "\n"
                + "Thu ngân: " + staff.getStaffID() + " - " + staff.getName() + "\n"
                + "Phiếu tính tiền chỉ có giá trị đổi xuất hóa đơn trong ngày" + "\n"
                + "Quý khách liên hệ cửa hàng để xuất hóa đơn điện tử";
        // lưu lịch sử giao dịch
        saveTransaction(product_list);
        return product_list;
    }
    public void saveTransaction (String transaction) throws IOException {
        // Tương tự như saveReport

        File file = new File("Transaction history.txt");
        if (!file.exists()) file.createNewFile();

        FileWriter FW = new FileWriter(file, true);
        BufferedWriter BW = new BufferedWriter(FW);

        PrintWriter PW = new PrintWriter(BW);
        PW.println("");

        if (transaction != null) {
            PW.println(transaction);
            PW.println("*********************************************");
            PW.flush();
            PW.close();
        }
    }
    public void saveReport (String report) throws IOException {
        // Sử dụng để lưu báo cáo, nếu báo cáo chưa có sẽ tạo file mới
        File file = new File("Report history.txt");
        if (!file.exists()) file.createNewFile();

        // Khai báo file ghi thêm, ko phải file ghi đè, nên append của nó là true.
        FileWriter FW = new FileWriter(file, true);
        BufferedWriter BW = new BufferedWriter(FW);

        PrintWriter PW = new PrintWriter(BW);
        PW.println("");
        // Nếu báo cáo là null, là không có gì gì mình không ghi vào.
        // Còn nếu bao cáo nó có dữ liệu bên trong đó rồi thì mình sẽ ghi nó vào
        if (report != null) {
            PW.println(report);
            PW.println("==============================");
            PW.flush();
            PW.close();
        }
    }
}
