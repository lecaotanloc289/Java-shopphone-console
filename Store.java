package Team_7;

public class Store  extends Person{
    private String Id;
    public Store (String Id, String NameStore, String Address, String Hotline)
    {
        super (NameStore, Address, Hotline);
        this.Id = Id;

    }

    public String getId()
    {
        return Id;
    }

}
