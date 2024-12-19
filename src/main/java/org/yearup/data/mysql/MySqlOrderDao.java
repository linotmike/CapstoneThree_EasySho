package org.yearup.data.mysql;

import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;
import org.yearup.data.OrderDao;
import org.yearup.models.*;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class MySqlOrderDao extends MySqlDaoBase implements OrderDao {

    public MySqlOrderDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Order createOrder(Order order, ShoppingCart shoppingCart) {
//        Order order = new Order();
        if (order.getDate() == null) {
            order.setDate(LocalDate.now());
        }
        String orderQuery = "INSERT INTO orders (user_id,date,address,city,state,zip,shipping_amount) VALUES (?,?,?,?,?,?,?)";
        String query = "INSERT INTO order_line_items(order_id,product_id,sales_price,quantity,discount) VALUES (?,?,?,?,?)";

        try (
                Connection connection = getConnection();) {
            connection.setAutoCommit(false);
            try (
                    PreparedStatement orderPreparedStatement = connection.prepareStatement(orderQuery, Statement.RETURN_GENERATED_KEYS)) {

                orderPreparedStatement.setInt(1, order.getUserId());
                orderPreparedStatement.setDate(2, Date.valueOf(order.getDate()));
                orderPreparedStatement.setString(3, order.getAddress());
                orderPreparedStatement.setString(4, order.getCity());
                orderPreparedStatement.setString(5, order.getState());
                orderPreparedStatement.setString(6, order.getZip());
                orderPreparedStatement.setBigDecimal(7, order.getShipping_amount());
                int rowsAdded = orderPreparedStatement.executeUpdate();

                if (rowsAdded > 0) {
                    System.out.println("Rows added " + rowsAdded);
                    try (
                            ResultSet resultSet = orderPreparedStatement.getGeneratedKeys()) {
                        if (resultSet.next()) {
//                        for()
                            order.setOrderId(resultSet.getInt(1));
                        }
                    }
                }
            }
            try (
                    PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//                ShoppingCart shoppingCart
                for (ShoppingCartItem shoppingCartItem : shoppingCart.getItems().values()) {
                    preparedStatement.setInt(1, order.getOrderId());
                    preparedStatement.setInt(2, shoppingCartItem.getProduct().getProductId());
                    preparedStatement.setBigDecimal(3, shoppingCartItem.getProduct().getPrice());
                    preparedStatement.setInt(4, shoppingCartItem.getQuantity());
                    preparedStatement.setBigDecimal(5, shoppingCartItem.getDiscountPercent());
                    preparedStatement.addBatch();
                }
                int[] rowsAdded = preparedStatement.executeBatch();
                System.out.println("order line Rows added " + rowsAdded.length);
            }

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        return null;
        return order;
    }

    private List<OrderLineItems> getOrderLineItems(int orderId, Connection connection) throws SQLException {
        List<OrderLineItems> orderLineItems = new ArrayList<>();
        String query = "SELECT oli.product_id, p.name AS product_name,oli.sales_price, oli.quantity, oli.discount " +
                "FROM order_line_items oli " +
                "JOIN products p ON oli.product_id = p.product_id " +
                "WHERE oli.order_id = ?";

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ) {
            preparedStatement.setInt(1,orderId);
            try (
                    ResultSet resultSet = preparedStatement.executeQuery();
                    ) {
                while (resultSet.next()){
                    OrderLineItems orderLineItem = new OrderLineItems();
                    orderLineItem.setProductId(resultSet.getInt("product_id"));
                    orderLineItem.setSales(resultSet.getBigDecimal("sales_price"));
                    orderLineItem.setQuantity(resultSet.getInt("quantity"));
                    orderLineItem.setDiscount(resultSet.getBigDecimal("discount"));
                    orderLineItems.add(orderLineItem);
                }
            }
        }

        return orderLineItems;
    }


}
