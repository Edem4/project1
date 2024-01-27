package ru.alishev.springcourse.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.alishev.springcourse.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return  jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }
    public Optional<Person> show(String email){
        return jdbcTemplate.query("select * from person where email=?",
                new BeanPropertyRowMapper<>(Person.class),
                new Object[]{email}).stream().findAny();
    }

    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?",
                        new BeanPropertyRowMapper<>(Person.class),
                        new Object[]{id})
                        .stream().findAny().orElse(null);
    }

    public void save(Person person){
        jdbcTemplate.update("INSERT INTO Person(name,age,email,address) VALUES(?,?,?,?)",
                person.getName(),
                person.getAge(),
                person.getEmail(),
                person.getAddress());
    }
    public void update(int id, Person person){
        jdbcTemplate.update("UPDATE Person SET name=?, age=?, email=?, address=? WHERE id=?",
                person.getName(),
                person.getAge(),
                person.getEmail(),
                person.getAddress(),
                id);
    }
    public void del(int id){
        jdbcTemplate.update("DELETE FROM Person WHERE id=?",id);
    }

    public void clear(){
        jdbcTemplate.update("truncate table person");
    }


    ///////////////////////////////////тест производ пакетной вставки

    public void testMultippleUpdate(){
        List<Person> people = create1000pepople();
        long before =  System.currentTimeMillis();

        for(Person person: people){
            jdbcTemplate.update("INSERT INTO Person VALUES(?,?,?,?)",
                    person.getId(),
                    person.getName(),
                    person.getAge(),
                    person.getEmail());
        }
        long after = System.currentTimeMillis();
        System.out.println(after-before);
    }
    public void testBatchUpdate(){
        List<Person> people = create1000pepople();
        long start = System.currentTimeMillis();
            jdbcTemplate.batchUpdate("INSERT INTO Person VALUES(?,?,?,?)",
                    new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                            ps.setInt(1,1);
                            ps.setString(2,people.get(i).getName());
                            ps.setInt(3,people.get(i).getAge());
                            ps.setString(4,people.get(i).getEmail());

                        }
                        @Override
                        public int getBatchSize() {
                            return people.size();
                        }
                    }
            );
        long stop = System.currentTimeMillis();
        System.out.println(stop-start);
    }

    private List<Person> create1000pepople(){
        List<Person> people = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            people.add(new Person(i,"Name" + i,30, "test" + i +"@mail.ru","erdfe"));
        }
        return people;
    }
}
