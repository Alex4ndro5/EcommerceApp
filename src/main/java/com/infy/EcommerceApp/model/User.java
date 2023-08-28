package com.infy.EcommerceApp.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.infy.EcommerceApp.enums.UserGender;
import com.infy.EcommerceApp.enums.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "users"
)
public class User {
    @Id
    @Column(
            name = "user_id"
    )
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    private Integer userId;
    @Column(
            name = "user_name",
            nullable = false
    )
    private String userName;
    @Column(
            name = "user_password"
    )
    private String userPassword;
    @Column(
            name = "user_birthdate"
    )
    @JsonFormat(
            pattern = "dd/MM/yy"
    )
    private LocalDate userBirthdate;
    @Column(
            name = "user_email",
            nullable = false,
            unique = true
    )
    private @Email(
            message = "Email should be valid"
    ) String userEmail;
    @Column(
            name = "user_address",
            nullable = false
    )
    private String userAddress;
    @Column(
            name = "user_gender"
    )
    private UserGender userGender;
    @Column(
            name = "user_role"
    )
    private UserRole userRole;
    @Column(
            name = "user_orders"
    )
    @OneToMany(
            mappedBy = "user"
    )
    private ArrayList<Order> userOrders;

    public User(String userName, String userPassword, LocalDate userBirthdate, String userEmail, String userAddress) {
        this.userRole = UserRole.USER;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userBirthdate = userBirthdate;
        this.userEmail = userEmail;
        this.userAddress = userAddress;
    }

    public String toString() {
        return "User{userId=" + this.userId + ", userName='" + this.userName + "', userEmail='" + this.userEmail + "', userAddress='" + this.userAddress + "'}";
    }
}
