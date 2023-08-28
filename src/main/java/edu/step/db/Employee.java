package edu.step.db;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "employee") // acme - a company that makes everything
public class Employee extends CoreEntity{

    @Column(nullable = false, length = 50)
    private String name;
    @Column(nullable = false, length = 50)
    private String surname;
    @Column(unique = true, nullable = false, length = 100)
    private String email;

    private LocalDate birthdate;

    @ManyToOne
    private Department department;

    @Transient
    private String value;

    public static String getTableName() {
        return "employee";
    }

    public Employee() {
    }

    public Employee(String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public Employee(int id, String name, String surname, String email, Department department) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.department = department;
    }
    public Employee(String name, String surname, String email, Department department, LocalDate birthdate) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.department = department;
        this.birthdate = birthdate;
    }

    public Employee(int id, String name, String surname, String email) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public Employee(String name, String surname, String email, Department department) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }


}
