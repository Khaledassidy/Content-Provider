package com.example.contentproviders;

public class ContactItem {
    String id,name,number,email,profilephoto,otherdetails;

    public ContactItem(String id, String name, String number, String email, String profilephoto, String otherdetails) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.email = email;
        this.profilephoto = profilephoto;
        this.otherdetails = otherdetails;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilephoto() {
        return profilephoto;
    }

    public void setProfilephoto(String profilephoto) {
        this.profilephoto = profilephoto;
    }

    public String getOtherdetails() {
        return otherdetails;
    }

    public void setOtherdetails(String otherdetails) {
        this.otherdetails = otherdetails;
    }
}
