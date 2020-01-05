package com.citylive.server.dao;

import com.citylive.server.domain.User;
import com.citylive.server.mapper.UserMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserDao {
    private final NamedParameterJdbcTemplate template;

    public UserDao(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    public List<User> findAll() {
        return template.query("select * from \"CityLiveDev\".\"User\"", new UserMapper());
    }

    public User insertUser(User user) {
        final String sql = "insert into \"CityLiveDev\".\"User\" (\"FirstName\", \"UserName\", \"LastName\", \"Email\", \"Password\") " +
                "values( :firstName, :userName,:lastName,:email, :password)";

        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("userName", user.getUserName())
                .addValue("firstName", user.getFirstName())
                .addValue("lastName", user.getLastName())
                .addValue("email", user.getEmail())
                .addValue("password", user.getPassword());
        int update = template.update(sql, param, holder);
        System.out.println(update);
        Map<String, String>mp = new HashMap<>();
        mp.put("userName",user.getUserName());
        List<User> users = template.query("select * from \"CityLiveDev\".\"User\" where \"UserName\"=:userName",mp, new UserMapper());
        return users.size() > 0 ? users.get(0) : null;
    }

}

