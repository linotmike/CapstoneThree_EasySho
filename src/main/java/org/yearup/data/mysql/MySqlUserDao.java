package org.yearup.data.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yearup.data.ShoppingCartDao;
import org.yearup.data.UserDao;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.models.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class MySqlUserDao extends MySqlDaoBase implements UserDao, ShoppingCartDao {
    @Autowired
    public MySqlUserDao(DataSource dataSource) {
        super(dataSource);
    }


    @Override
    public User create(User newUser) {
        String sql = "INSERT INTO users (username, hashed_password, role) VALUES (?, ?, ?)";
        String hashedPassword = new BCryptPasswordEncoder().encode(newUser.getPassword());

        try (Connection connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, newUser.getUsername());
            ps.setString(2, hashedPassword);
            ps.setString(3, newUser.getRole());

            ps.executeUpdate();

            User user = getByUserName(newUser.getUsername());
            user.setPassword("");

            return user;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();

        String sql = "SELECT * FROM users";
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet row = statement.executeQuery();

            while (row.next()) {
                User user = mapRow(row);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return users;
    }

    @Override
    public User getUserById(int id) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet row = statement.executeQuery();

            if (row.next()) {
                User user = mapRow(row);
                return user;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public User getByUserName(String username) {
        String sql = "SELECT * " +
                " FROM users " +
                " WHERE username = ?";

        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);

            ResultSet row = statement.executeQuery();
            if (row.next()) {

                User user = mapRow(row);
                return user;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return null;
    }

    @Override
    public int getIdByUsername(String username) {
        User user = getByUserName(username);

        if (user != null) {
            return user.getId();
        }

        return -1;
    }

    @Override
    public boolean exists(String username) {
        User user = getByUserName(username);
        return user != null;
    }

    private User mapRow(ResultSet row) throws SQLException {
        int userId = row.getInt("user_id");
        String username = row.getString("username");
        String hashedPassword = row.getString("hashed_password");
        String role = row.getString("role");

        return new User(userId, username, hashedPassword, role);
    }

    @Override
    public ShoppingCart getByUserId(int userId) {
        ShoppingCart shoppingCart = new ShoppingCart();
//        ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
//        String query = "SELECT oli.product_id, oli.quantity, oli.sales_price, oli.discount, " +
//                "p.product_id, p.name, p.price, p.category_id, p.description, p.color, p.stock, p.image_url, p.featured " +
//                "FROM order_line_items oli " +
//                "JOIN products p ON oli.product_id = p.product_id " +
//                "WHERE sc.user_id = ?";
        String query = "SELECT sc.product_id, sc.quantity, p.name, p.price, p.category_id, " +
                "p.description, p.color, p.stock, p.image_url, p.featured " +
                "FROM shopping_cart sc " +
                "JOIN products p ON sc.product_id = p.product_id " +
                "WHERE sc.user_id = ?";
        try (
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)

        ) {
            preparedStatement.setInt(1, userId);
            try (
                    ResultSet resultSet = preparedStatement.executeQuery();
            ) {
                while (resultSet.next()) {
                    int productId = resultSet.getInt("product_id");
                    String name = resultSet.getString("name");
                    BigDecimal price = resultSet.getBigDecimal("price");
                    int categoryId = resultSet.getInt("category_id");
                    String description = resultSet.getString("description");
                    String color = resultSet.getString("color");
                    int stock = resultSet.getInt("stock");
                    String imageUrl = resultSet.getString("image_url");
                    boolean featured = resultSet.getBoolean("featured");

                    Product product = new Product(productId, name, price, categoryId, description, color, stock, featured, imageUrl);
                    ShoppingCartItem shoppingCartItem = new ShoppingCartItem();

                    shoppingCartItem.setProduct(product);
                    shoppingCartItem.setQuantity(resultSet.getInt("quantity"));


                    shoppingCart.add(shoppingCartItem);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching shopping cart" + userId + e);
        }
        return shoppingCart;
    }

    @Override
    public void addToCart(int userId, int productId) {
        String query = "INSERT INTO shopping_cart (user_id,product_id,quantity) VALUES (?,?,1) " +
                "ON DUPLICATE KEY UPDATE quantity = quantity + 1";
        try (
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, productId);
            int rowsCreated = preparedStatement.executeUpdate();
            if (rowsCreated > 0) {
                System.out.println("Rows created " + rowsCreated);
            } else {
                System.out.println("No rows created");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deleteCart(int id) {
        String query = "DELETE FROM shopping_cart WHERE user_id = ?";
        try (
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ) {
            preparedStatement.setInt(1,id);

            int rowsDeleted = preparedStatement.executeUpdate();
            if(rowsDeleted > 0){
                System.out.println("Rows deleted " + rowsDeleted);
            } else{
                System.out.println("No rows deleted");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
