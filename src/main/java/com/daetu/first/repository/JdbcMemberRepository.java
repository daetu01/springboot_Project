package com.daetu.first.repository;

import com.daetu.first.domain.Member;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcMemberRepository implements MemberRepository{

    private final DataSource dataSource;

    public JdbcMemberRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    // database COnnection 을 똑같은 거를 유지를 시켜줘야 해서 spring framework 쓸 떄 꼭 저거 꺼야댐
    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }

    private void close(Connection conn) throws SQLException {
        DataSourceUtils.releaseConnection(conn, dataSource);
    }


    @Override
    public Member save(Member member) {
        String sql = "INSERT INTO member(name) VALUES(?)";

        ResultSet rs = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, member.getName());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();

            if (rs.next()) {
                member.setId(rs.getLong(1));
            } else {
                throw new SQLException("id 조회 실패");
            }
            return member;
        } catch (Exception e) {

        } finally {
            try {
                rs.close();
                pstmt.close();
                close(connection);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }

        return null;
    }

    @Override
    public Optional<Member> findById(Long id) {
        String sql = "select * From member where id = ? ";

        Connection conn = null;
        PreparedStatement pstmt = null;

        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);
            } else {
                return Optional.empty();
            }
        } catch (Exception e){
            throw new IllegalStateException(e);
        } finally {
            try {
                rs.close();
                pstmt.close();
             close(conn);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }

    @Override
    public Optional<Member> findByName(String name) {
        String sql = "select * From member where name = ? ";

        Connection conn = null;
        PreparedStatement pstmt = null;

        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);
            } else {
                return Optional.empty();
            }
        } catch (Exception e){
            throw new IllegalStateException(e);
        } finally {
            try {
                rs.close();
                pstmt.close();
                close(conn);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }

    @Override
    public List<Member> findAll() {
        String sql = "select * from member";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();

            List<Member> members = new ArrayList<>();
            while (rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                members.add(member);
            }
            return members;
        } catch (Exception e){
            throw new IllegalStateException(e);
        } finally {
            try {
               close(conn);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
