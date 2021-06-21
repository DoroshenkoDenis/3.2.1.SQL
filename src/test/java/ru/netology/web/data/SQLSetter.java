package ru.netology.web.data;

import lombok.SneakyThrows;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.*;

public class SQLSetter {
    private final static QueryRunner runner = new QueryRunner();
    private final static Connection conn = connection();

    @SneakyThrows
    public static Connection connection() {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
    }

    @SneakyThrows
    public static void dropDataBase() {
        runner.update(conn, "DELETE FROM cards");
        runner.update(conn, "DELETE FROM auth_codes");
        runner.update(conn, "DELETE FROM users");
    }

    @SneakyThrows
    public static String getVerificationCode(DataHelper.AuthInfo authInfo) {
        val userId = runner.query(conn, "SELECT id FROM users WHERE login = '" + authInfo.getLogin() + "'", new ScalarHandler<>());
        return runner.query(conn, "SELECT code FROM auth_codes WHERE user_id = '" + userId + "' ORDER BY created DESC LIMIT 1", new ScalarHandler<>());
    }

    @SneakyThrows
    public static String getUserStatus(DataHelper.AuthInfo authInfo){
        return runner.query(conn, "SELECT status FROM users WHERE login = '" + authInfo.getLogin() + "'", new ScalarHandler<>());
    }

}
