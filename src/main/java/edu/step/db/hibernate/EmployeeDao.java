package edu.step.db.hibernate;

import edu.step.db.Department;
import edu.step.db.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class EmployeeDao extends AbstractDao<Employee>{ // Dao - data access object

    @Override
    public Class<Employee> getEntityClass() {
        return Employee.class;
    }
    @Override
    public Employee updateEntity(Employee source, Employee target) {
        target.setBirthdate(source.getBirthdate());
        target.setName(source.getName());
        target.setSurname(source.getSurname());
        target.setDepartment(source.getDepartment());
        target.setBirthdate(source.getBirthdate());
        return target;
    }
}
