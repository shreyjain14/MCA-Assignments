package me.shreyjain.phone.impl;

import java.util.ArrayList;
import java.util.Objects;

import me.shreyjain.phone.Contact;
import me.shreyjain.phone.MobilePhone;

public class MobilePhoneImpl implements MobilePhone {

    private String myNumber;
    private ArrayList<Contact> myContacts;

    public MobilePhoneImpl(String myNumber) {
        this.myNumber = myNumber;
        this.myContacts = new ArrayList<>();
    }


    @Override
    public boolean addNewContact(Contact contact) {
        if (myContacts.contains(contact)) {
            return false;
        } else {
            myContacts.add(contact);
            return true;
        }
    }

    @Override
    public boolean updateContact(Contact oldContact, Contact newContact) {
        if (myContacts.contains(oldContact)) {
            myContacts.remove(oldContact);
            myContacts.add(newContact);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean removeContact(Contact contact) {
        if (myContacts.contains(contact)) {
            myContacts.remove(contact);
            return true;
        } else {
            myContacts.add(contact);
            return false;
        }
    }

    @Override
    public int findContact(Contact contact) {
        if (myContacts.contains(contact)) {
            return myContacts.indexOf(contact);
        } else {
            myContacts.add(contact);
            return -1;
        }
    }

    @Override
    public int findContact(String name) {
        for (int i=0; i<myContacts.size(); i++) {
            if (Objects.equals(name, myContacts.get(i).getName())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Contact queryContact(String name) {
        for (Contact contact : myContacts) {
            if (Objects.equals(name, contact.getName())) {
                return contact;
            }
        }
        return null;
    }

    @Override
    public void printContacts() {
        System.out.println("Contact List: ");
        for (int i=0; i<myContacts.size(); i++) {
            System.out.println((i + 1) + ". " + myContacts.get(i).getName());
        }
    }
}
