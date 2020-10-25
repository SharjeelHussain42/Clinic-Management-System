public class RegistrationModel {
    String full_name;
    String phone;
    String email;
    String address;

    public RegistrationModel(String full_name, String phone, String email, String address) {
        this.full_name = full_name;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
