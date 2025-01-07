package me.shreyjain.phone;

public interface MobilePhone {

    boolean addNewContact(Contact contact);
    boolean updateContact(Contact oldContact, Contact newContact);
    boolean removeContact(Contact contact);

    int findContact(Contact contact);
    int findContact(String name);

    Contact queryContact(String name);
    void printContacts();

}
