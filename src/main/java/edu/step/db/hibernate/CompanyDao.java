package edu.step.db.hibernate;

import edu.step.db.Company;
import edu.step.db.Department;
import edu.step.db.Employee;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.*;


public class CompanyDao extends AbstractDao<Company> {
    @Override
    public Class<Company> getEntityClass() {
        return Company.class;
    }

    @Override
    public Company updateEntity(Company source, Company target) {
        target.setName(source.getName());
        target.setDescription(source.getDescription());
        return target;
    }

    public static void onExport(List<Company> companies, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Inițializăm YAML DumperOptions pentru a formata ieșirea
            DumperOptions options = new DumperOptions();
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

            // Inițializăm obiectul YAML cu opțiunile specificate
            Yaml yaml = new Yaml(options);

            // Creăm un container pentru datele exportate
            List<Map<String, Object>> data = new ArrayList<>();

            // Parcurgem fiecare companie și adăugăm datele în container
            for (Company company : companies) {
                Map<String, Object> companyData = new LinkedHashMap<>();
                companyData.put("Nume companie", company.getName());
                companyData.put("Descriere companie", company.getDescription());

                List<Department> departments = company.getDepartments();
                if (departments != null && !departments.isEmpty()) {
                    List<Map<String, Object>> departmentDataList = new ArrayList<>();

                    for (Department department : departments) {
                        Map<String, Object> departmentData = new LinkedHashMap<>();
                        departmentData.put("Nume departament", department.getName());
                        departmentData.put("Descriere departament", department.getDescription());

                        List<Employee> employees = department.getEmployees();
                        if (employees != null && !employees.isEmpty()) {
                            List<Map<String, Object>> employeeDataList = new ArrayList<>();

                            for (Employee employee : employees) {
                                Map<String, Object> employeeData = new LinkedHashMap<>();
                                employeeData.put("Nume angajat", employee.getName());
                                employeeData.put("Prenume angajat", employee.getSurname());
                                employeeData.put("Email angajat", employee.getEmail());

                                employeeDataList.add(employeeData);
                            }

                            departmentData.put("Angajați", employeeDataList);
                        }

                        departmentDataList.add(departmentData);
                    }

                    companyData.put("Departamente", departmentDataList);
                }

                data.add(companyData);
            }

            // Serializăm datele și le scriem în fișier
            String yamlData = yaml.dump(data);

            // Înlăturăm linii goale create pentru "Nu există departamente" sau "Nu există angajați"
            yamlData = yamlData.replaceAll("\n  - Nu există departamente pentru această companie\\.\n", "");
            yamlData = yamlData.replaceAll("\n      - Nu există angajați în acest departament\\.\n", "");

            writer.write(yamlData);

            System.out.println("Datele au fost exportate cu succes în " + filePath);
        } catch (IOException e) {
            System.err.println("A apărut o eroare la exportul datelor în " + filePath + ": " + e.getMessage());
        }
    }

    public static void onImport(String filePath,CompanyDao companyDao, DepartmentDao departmentDao,
                                EmployeeDao employeeDao) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Inițializăm obiectul YAML
            Yaml yaml = new Yaml();

            List<Map<String, Object>> dataList = yaml.load(reader);

//            List<Company> companies = new ArrayList<>();
//            List<Department> departments = new ArrayList<>();
//            List<Employee> employees = new ArrayList<>();

            Set<Company> companies = new HashSet<>();
            Set<Department> departments = new HashSet<>();
            Set<Employee> employees = new HashSet<>();

            for (Map<String, Object> companyData : dataList) {
                String companyName = (String) companyData.get("Nume companie");
                String companyDescription = (String) companyData.get("Descriere companie");

                Company company = new Company();
                company.setName(companyName);
                company.setDescription(companyDescription);

                List<Map<String, Object>> departmentDataList = (List<Map<String, Object>>) companyData.get("Departamente");
                if (departmentDataList != null) {
    //                departments = new ArrayList<>();

                    for (Map<String, Object> departmentData : departmentDataList) {
                        String departmentName = (String) departmentData.get("Nume departament");
                        String departmentDescription = (String) departmentData.get("Descriere departament");

                        Department department = new Department();
                        department.setName(departmentName);
                        department.setDescription(departmentDescription);
                        department.setCompany(company);


                        Object employeeDataObject = departmentData.get("Angajați");
                        if (employeeDataObject instanceof List) {
                            List<Map<String, Object>> employeeDataList = (List<Map<String, Object>>) employeeDataObject;

                            if (employeeDataList != null) {
    //                            employees = new ArrayList<>();

                                for (Map<String, Object> employeeData : employeeDataList) {
                                    String employeeName = (String) employeeData.get("Nume angajat");
                                    String employeeSurname = (String) employeeData.get("Prenume angajat");
                                    String employeeEmail = (String) employeeData.get("Email angajat");

                                    Employee employee = new Employee();
                                    employee.setName(employeeName);
                                    employee.setSurname(employeeSurname);
                                    employee.setEmail(employeeEmail);
                                    employee.setDepartment(department);

                                    employees.add(employee);

                                }
                                List<Employee> employeeList = new ArrayList<>(employees);
                                department.setEmployees(employeeList);
                            }
                        }
                        departments.add(department);
                    }
                    List<Department> departmentList = new ArrayList<>(departments);
                    company.setDepartments(departmentList);
                }
                companies.add(company);
            }
            for (Company company : companies) {
                companyDao.create(company);
            }
            for (Department department : departments) {
                departmentDao.create(department);
            }
            for (Employee employee : employees) {
                employeeDao.create(employee);
            }

        } catch (IOException e) {
            System.err.println("A apărut o eroare la importul datelor din " + filePath + ": " + e.getMessage());
        }
    }
}
