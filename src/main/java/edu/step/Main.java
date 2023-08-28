package edu.step;

import edu.step.db.*;
import edu.step.db.hibernate.CompanyDao;
import edu.step.db.hibernate.DepartmentDao;
import edu.step.db.hibernate.EmployeeDao;
import edu.step.db.hibernate.HibernateUtil;
import org.hibernate.SessionFactory;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EmployeeDB db = new EmployeeDB();
//        for (int i = 0; i < 20; i++) {
//            db.create(new Department("IT " + i, "description " + i));
//        }
//        db.delete(15);
//        List<Employee> employees = db.selectAll();
//        for (Employee emp: employees) {
//            System.out.println("Employee: " + emp.getName() + " department" + emp.getDepartment().getName());
//        }

//        LocalDate test = LocalDate.of(2000, 10,10);
//        LocalDate now = LocalDate.now();
//        String x = "10-10-2000";
//        test.getDayOfWeek();
//        boolean before = test.isBefore(now);
//        LocalDate plusDays = now.plusDays(100);
//        System.out.println(plusDays.getDayOfWeek());
//        System.out.println(now.toEpochDay());
//        System.out.println(now.toString());
//        LocalTime time = LocalTime.now();
//        time.toNanoOfDay();

        SessionFactory sessionFactory = HibernateUtil.sessionFactory;
        System.out.println("Success");

        Company company = new Company("Bucuria", "Bucuria SA");
        Employee employee = new Employee("Pete2", "Smith41", "pete222@gmail.com");
        EmployeeDao dao = new EmployeeDao();
        DepartmentDao dao_d = new DepartmentDao();
        CompanyDao dao_c = new CompanyDao();
//        dao_c.create(company);
        dao.create(employee);
//
        List<Employee> employees = dao.findAll();
        for(Employee emp: employees) {
            System.out.println(emp.getId());
       }
        List<Department> departments = dao_d.findAll();
        for(Department dep: departments) {
            System.out.println(dep.getId());
        }

        // update the employee
        Employee updatedEmployee = employees.get(2);
        updatedEmployee.setDepartment(departments.get(0));
        dao.update(updatedEmployee);
////        dao.delete(1);

//        Department department = new Department("Vanzari", "departament Vanzari");
//        dao_d.create(department);
    }
}