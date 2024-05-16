package ru.gubber.portfoliohistory.account.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "accounts")
public class Account {
    @Id
    UUID id;
    String name;
    String broker;
    String number;

    public Account() {
    }

    public Account(UUID id, String name, String broker, String number) {
        this.id = id;
        this.name = name;
        this.broker = broker;
        this.number = number;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBroker() {
        return broker;
    }

    public String getNumber() {
        return number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBroker(String broker) {
        this.broker = broker;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", broker='" + broker + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
