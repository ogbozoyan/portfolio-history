package ru.gubber.portfoliohistory.account.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "accounts")
public class Account {
    @Id
    private UUID id;
    private String name;
    private String broker;
    private String number;
    private double currentBalance;

    public Account() {
    }

    public Account(UUID id, String name, String broker, String number, double currentBalance) {
        this.id = id;
        this.name = name;
        this.broker = broker;
        this.number = number;
        this.currentBalance = currentBalance;
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

    public double getCurrentBalance() {
        return currentBalance;
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

    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", broker='" + broker + '\'' +
                ", number='" + number + '\'' +
                ", currentBalance='" + currentBalance + '\'' +
                '}';
    }
}
