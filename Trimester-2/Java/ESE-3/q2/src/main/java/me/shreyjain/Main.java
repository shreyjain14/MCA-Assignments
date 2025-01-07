package me.shreyjain;

// import java.util.Scanner;

import me.shreyjain.phone.Contact;
import me.shreyjain.phone.MobilePhone;
import me.shreyjain.phone.impl.ContactImpl;
import me.shreyjain.phone.impl.MobilePhoneImpl;


public class Main {
    public static void main(String[] args) {

        MobilePhone mobilePhone = new MobilePhoneImpl("1234567890");

        Contact contact1 = ContactImpl.createContact("shrey0", "123");
        Contact contact2 = ContactImpl.createContact("shrey1", "111");
        Contact contact3 = ContactImpl.createContact("shrey2", "112");
        Contact contact4 = ContactImpl.createContact("shrey3", "113");

        System.out.println(mobilePhone.addNewContact(contact1));
        System.out.println(mobilePhone.addNewContact(contact1));
        System.out.println(mobilePhone.addNewContact(contact2));
        System.out.println(mobilePhone.addNewContact(contact3));
        System.out.println(mobilePhone.addNewContact(contact4));

        System.out.println(mobilePhone.findContact(contact3));
        System.out.println(mobilePhone.findContact("shrey2"));

        mobilePhone.printContacts();
        
        System.out.println(mobilePhone.removeContact(contact4));
        System.out.println(mobilePhone.updateContact(contact1, contact4));
        
        mobilePhone.printContacts();

    }
}