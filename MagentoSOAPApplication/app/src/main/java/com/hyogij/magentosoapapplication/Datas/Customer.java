package com.hyogij.magentosoapapplication.datas;

import org.ksoap2.serialization.SoapObject;

/**
 * A class for Customer data.
 */
public class Customer {
    private String customer_id = null;
    private String created_at = null;
    private String updated_at = null;
    private String store_id = null;
    private String website_id = null;
    private String created_in = null;
    private String email = null;
    private String firstname = null;
    private String lastname = null;
    private String group_id = null;
    private String password_hash = null;

    public Customer(SoapObject property) {
        customer_id = property.getProperty("customer_id").toString();
        created_at = property.getProperty("created_at").toString();
        updated_at = property.getProperty("updated_at").toString();
        store_id = property.getProperty("store_id").toString();
        website_id = property.getProperty("website_id").toString();
        created_in = property.getProperty("created_in").toString();
        email = property.getProperty("email").toString();
        firstname = property.getProperty("firstname").toString();
        lastname = property.getProperty("lastname").toString();
        group_id = property.getProperty("group_id").toString();
        password_hash = property.getProperty("password_hash").toString();
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getStore_id() {
        return store_id;
    }

    public String getWebsite_id() {
        return website_id;
    }

    public String getCreated_in() {
        return created_in;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getGroup_id() {
        return group_id;
    }

    public String getPassword_hash() {
        return password_hash;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customer_id=" + customer_id +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", store_id=" + store_id +
                ", website_id=" + website_id +
                ", created_in='" + created_in + '\'' +
                ", email='" + email + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", group_id=" + group_id +
                ", password_hash='" + password_hash + '\'' +
                '}';
    }
}
