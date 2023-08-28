package edu.step.db;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "department")
public class Department extends CoreEntity{

    @Column(unique = true, length = 70)
    private String name;
    private String description;
    @ManyToOne
    private Company company;

    public Department() {
    }

    public Department(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Department(String name, String description, Company company) {
        this.name = name;
        this.description = description;
        this.company = company;
    }

    public Department(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }


    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
