package edu.step.db.hibernate;

import edu.step.db.Company;
import edu.step.db.Department;

public class CompanyDao extends AbstractDao<Company>{
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
}
