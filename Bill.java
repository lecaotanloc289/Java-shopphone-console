package Team_7;

public class Bill {

    public Bill (Store store, Staff staff, Product product) {

    }
    public Bill () {

    }


    public void printBill () {
        System.out.println("""
                **********CÔNG TY TNHH MỘT THÀNH VIÊN TEAM 7**********
                \t18A/1 Cộng Hòa, P13, Q.Tân Bình, TP.HCM
                """);

    }

    public static void main(String[] args) {
        Bill bill = new Bill();
        bill.printBill();

    }
}
