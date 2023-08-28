package edu.step.db;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class CoreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
