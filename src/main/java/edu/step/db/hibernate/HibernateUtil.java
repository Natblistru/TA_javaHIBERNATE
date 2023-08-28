package edu.step.db.hibernate;

import edu.step.db.Company;
import edu.step.db.Department;
import edu.step.db.Employee;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.File;

public class HibernateUtil {

    public final static SessionFactory sessionFactory = buildSessionFactory();


    private static SessionFactory buildSessionFactory() {
        try{
            Configuration hibernateConfiguration = new Configuration();
            hibernateConfiguration.configure(new File("src\\main\\resources\\hibernate.cfg.xml"));

            hibernateConfiguration.addAnnotatedClass(Employee.class);
            hibernateConfiguration.addAnnotatedClass(Department.class);
            hibernateConfiguration.addAnnotatedClass(Company.class);

            return hibernateConfiguration.buildSessionFactory();
        } catch (Exception ex) {
            System.err.println("Nu am putut initializa hibernate. Motivul: " + ex.getMessage());
            throw new RuntimeException();
        }
    }


}
