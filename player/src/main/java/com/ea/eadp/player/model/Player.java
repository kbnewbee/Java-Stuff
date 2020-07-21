package com.ea.eadp.player.model;

import com.ea.eadp.player.entity.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Player {
    private String userId;
    private String name;
    private String contact;
    private String level;
    private List<Product> productList;

    public Player() {
    }

    public Player(String name, String contact, String level) {
        this.name = name;
        this.contact = contact;
        this.level = level;
    }

    public Player(String userId, String name, String contact, String level) {
        this.productList = new ArrayList<>();
        this.userId = userId;
        this.name = name;
        this.contact = contact;
        this.level = level;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void addProduct(Product product) {
        this.productList.add(product);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(userId, player.userId) &&
                Objects.equals(name, player.name) &&
                Objects.equals(contact, player.contact) &&
                Objects.equals(level, player.level) &&
                Objects.equals(productList, player.productList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, name, contact, level, productList);
    }

    @Override
    public String toString() {
        return "Player{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", contact='" + contact + '\'' +
                ", level='" + level + '\'' +
                ", product=" + productList +
                '}';
    }
}
