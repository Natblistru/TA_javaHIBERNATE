package edu.step.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Objects;

@Entity
public class Company extends CoreEntity{
    @Column(unique = true, length = 70)
    private String name;
    private String description;

    @OneToMany(mappedBy = "company", fetch = FetchType.EAGER)
    private List<Department> departments;


    public Company() {
    }

    public Company(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Company(int id, String name, String description) {
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

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company that = (Company) o;
        return id == that.id && Objects.equals(name, that.name) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
