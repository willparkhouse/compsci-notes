public class Customer {
    //attributes
    private String name;
    private String phone;
    private String emailAddress;
    public Customer(String name, String phone, String emailAddress) { //2 marks
        //complete the constructor
        this.name = name;
        this.phone = phone;
        this.emailAddress = emailAddress;
    }

    //complete the getters()
    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

}
