package net.leanix.springtest.dao;

import java.util.List;
import net.leanix.springtest.configuration.jdbi.JdbiDao;
import net.leanix.springtest.model.Person;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

@JdbiDao
public interface PersonDao {

    @SqlQuery("SELECT * FROM person")
    List<Person> getAll();

    @SqlUpdate("DELETE FROM person WHERE name = ?")
    int deleteByName(String name);

    @SqlUpdate("INSERT INTO person (name, surname) VALUES (?, ?)")
    int add(String name, String surname);

}
