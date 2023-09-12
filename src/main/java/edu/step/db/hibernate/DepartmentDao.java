package edu.step.db.hibernate;

import edu.step.db.Department;
import edu.step.db.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class DepartmentDao extends AbstractDao<Department>{

    @Override
    public Class<Department> getEntityClass() {
        return Department.class;
    }
    @Override
    public Department updateEntity(Department source, Department target) {
        target.setName(source.getName());
        target.setDescription(source.getDescription());
        target.setCompany(source.getCompany());
        return target;
    }

 }
