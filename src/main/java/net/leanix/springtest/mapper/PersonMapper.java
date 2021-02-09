package net.leanix.springtest.mapper;


import java.sql.ResultSet;
import java.sql.SQLException;
import net.leanix.springtest.model.Person;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import org.springframework.stereotype.Component;

@Component
public class PersonMapper implements RowMapper<Person> {

    @Override
    public Person map(ResultSet rs, StatementContext ctx) throws SQLException {
        var name = rs.getString("name");
        var surname = rs.getString("surname");
        return new Person(name, surname);
    }
}
