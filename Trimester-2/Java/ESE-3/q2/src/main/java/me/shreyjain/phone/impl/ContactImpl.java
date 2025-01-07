package me.shreyjain.phone.impl;

import me.shreyjain.phone.Contact;

public class ContactImpl implements Contact {

    private String name;
    private String number;

    public ContactImpl(String name, String number) {
        this.name = name;
        this.number = number;
    }


    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getPhoneNumber() {
        return this.number;
    }

    public static Contact createContact(String name, String phoneNumber) {
        return new ContactImpl(name, phoneNumber);
    }
}
