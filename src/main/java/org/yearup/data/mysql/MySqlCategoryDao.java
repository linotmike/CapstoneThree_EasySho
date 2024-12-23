package org.yearup.data.mysql;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;
import org.yearup.data.CategoryDao;
import org.yearup.models.Category;
import org.yearup.models.Product;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class MySqlCategoryDao extends MySqlDaoBase implements CategoryDao {
    public MySqlCategoryDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        String query = "SELECT * FROM categories";
        try (
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
        ) {
            while (resultSet.next()) {
                Category category = mapRow(resultSet);
                categories.add(category);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    @Override
    public Category getById(int categoryId) {
        String query = "SELECT * FROM categories WHERE category_id = ?";
        try (
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setInt(1, categoryId);
            try (
                    ResultSet resultSet = preparedStatement.executeQuery();
            ) {
                if (resultSet.next()) {
                    return mapRow(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Product> getProductsById(int categoryId) {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products WHERE category_id = ?";
        try (
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ) {
            preparedStatement.setInt(1,categoryId);
            try (
                    ResultSet resultSet = preparedStatement.executeQuery();
                    ) {
                while(resultSet.next()){
                    int productId = resultSet.getInt("product_id");
                    String name = resultSet.getString("name");
                    BigDecimal price =resultSet.getBigDecimal("price");
//                    int categoryId =resultSet.getInt("category_id");
                    String description = resultSet.getString("description");
                    String color = resultSet.getString("color");
                    int stock = resultSet.getInt("stock");
                    boolean isFeatured = resultSet.getBoolean("featured");
                    String imageUrl = resultSet.getString("image_url");

                    Product product = new Product(productId, name, price, categoryId, description, color, stock, isFeatured, imageUrl);
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    ;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Category create(Category category) {
        String query = "INSERT INTO categories (name,description) VALUES (?,?)";
        try (
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ) {
            preparedStatement.setString(1, category.getName());
            preparedStatement.setString(2, category.getDescription());

            int rowsCreated = preparedStatement.executeUpdate();
            if (rowsCreated > 0) {
                System.out.println("Rows created " + rowsCreated);
                try (
                        ResultSet resultSet = preparedStatement.getGeneratedKeys();
                ) {
                    if (resultSet.next()) {
                        category.setCategoryId(resultSet.getInt(1));
                        return category;
                    }
                }
            } else {
                System.out.println("No rows created");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void update(int categoryId, Category category) {
        String query = "UPDATE categories SET name = ?, description = ? WHERE category_id = ?";
        try (
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setString(1, category.getName());
            preparedStatement.setString(2, category.getDescription());
            preparedStatement.setInt(3, categoryId);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Rows updated " + rowsUpdated);
            } else {
                System.out.println("No rows updated");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(int categoryId) {
        String query = "DELETE FROM categories WHERE category_id = ?";
        try (
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setInt(1, categoryId);
            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Rows deleted " + rowsDeleted);
            } else {
                System.out.println("No rows deleted");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private Category mapRow(ResultSet resultSet) throws SQLException {
        int categoryId = resultSet.getInt("category_id");
        String name = resultSet.getString("name");
        String description = resultSet.getString("description");

        Category category = new Category() {{
            setCategoryId(categoryId);
            setName(name);
            setDescription(description);
        }};

        return category;
    }

}
