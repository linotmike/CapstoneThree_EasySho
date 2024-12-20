# E-Commerce API
![project license](https://img.shields.io/badge/license-MIT-blue.svg)

## Description
The E-Commerce API is a Java-based backend application built with Spring Boot. It supports a full-featured e-commerce system by providing RESTful endpoints to manage products, categories, user profiles, shopping carts, and orders. The API integrates with a MySQL database for persistent data storage and ensures efficient handling of API requests.

This backend API interacts with a frontend project that uses JavaScript, jQuery and HTML to interact with the endpoints.
## Table of Contents

- [Usage](#usage)
    - [Screenshot](#Endpoints)
- [Features](#features)
- [How It Works](#How-It-Works)
- [Prerequisites](#prerequisites)
- [License](#license)

------------------

## Usage
To use this application:

1. Clone or download the project to your local machine. 
2. Ensure you have a MySQL database and configure it in the application properties.
3. Compile and run the application## Screenshot

# EndPoints
## Categories
<ul>
<li>GET /categories - Fetch all categories. </li>
## Home Screen
<li>GET /categories/{id} - Fetch a specific category.</li>
<li>POST /categories - Add a new category.</li>
<li>PUT /categories/{id} - Update an existing category.</li>
<li>DELETE /categories/{id} - Delete a category.</li>
</ul>

### Get all categories
![Screenshot ](./src/demo/getAllCategories.png)
### Get categories by id
![Screenshot ](./src/demo/getCategoriesById.png)
### Post categories
![Screenshot ](./src/demo/postCategories.png)
### update categories
![Screenshot ](./src/demo/updateCategories.png)
### Delete Categories
![Screenshot ](./src/demo/deleteCategories.png)

## Products
<ul>
<li>GET /products - Fetch all products, with optional filters (e.g., price, color, category) </li>

<li>GET /products/{id} - Fetch a product.</li>
<li>GET categories/{id}/products - Get products by category id</li>
<li>POST /products - Add a new product.</li>
<li>PUT /products/{id} - Update product details.</li>
<li>DELETE /products/{id} - Remove a product.</li>
</ul>

### Get all products
![Screenshot ](./src/demo/getAllProducts.png)
### Get products by id
![Screenshot ](./src/demo/getProductById.png)
### Get products by category id
![Screenshot ](./src/demo/getProductsById.png)
### Post products
![Screenshot ](./src/demo/sortProducts.png)
### Sort products
![Screenshot ](./src/demo/sortProducts.png)


## Shopping Cart
<ul>
<li>GET /cart - View current cart.</li>
<li>POST /cart/products/{id} - Add a product to the cart.</li>
<li>PUT /cart/products/{id} - Update the quantity of a product.</li>
<li>DELETE /cart - Clear the cart.</li>
</ul>

### Get current shopping cart
![Screenshot ](./src/demo/getCart.png)
### Add a product to a shopping cart
![Screenshot ](./src/demo/postProductToCart.png)
### update the quantity of a shopping cart
![Screenshot ](./src/demo/updateCart.png)
### Delete cart
![Screenshot ](./src/demo/clearCart.png)
### Delete an item from a product
![Screenshot](./src/demo/removeAnItemFromCart.png)

## Profiles
<ul>
<li>GET /profile - View user profile.</li>
<li>PUT /profile - Update user profile.</li>
</ul>

### Get user profile
![Screenshot ](./src/demo/getprofile.png)
### update user profile
![Screenshot ](./src/demo/updateProfile.png)

## Order
<ul>
<li>POST /orders - Place an order.</li>
</ul>

### Add an order
![Screenshot ]()



------------------

# Features
<ul> <li> <b>Manage Categories:</b> Add, update, or delete product categories.</li> <li> <b>Product Catalog with Filters:</b> Retrieve products based on filters like category, price range, and color.</li> <li> <b>Shopping Cart:</b> Add products, update quantities, or clear the cart.</li> <li> <b>User Profiles:</b> View and update user profiles, with support for image uploads via Cloudinary.</li> <li> <b>Order Placement:</b> Finalize and place orders, triggering order details.</li> <li> <b>Database Persistence:</b> All data is stored in a MySQL database to ensure long-term persistence.</li> <li> <b>Cloudinary Integration:</b> Allows users to upload profile images seamlessly.</li> </ul>

# How It Works
1. RESTful API Design:
   Each resource (e.g., products, categories, carts) is accessed via HTTP methods like GET, POST, PUT, and DELETE.
2. Data Persistence:
   The backend integrates with MySQL to store and retrieve data.
3. Cart and Order Management:
   Users can interact with a shopping cart, update product quantities, and finalize orders
4. Cloudinary Integration:
   Profile images are uploaded and stored on Cloudinary, with links saved in the database.
5. Validation and Error Handling:
   The system validates inputs, ensuring data integrity and smooth user experience.


------------------

# Prerequisites
<ul> <li>Java Development Kit (JDK 8+).</li> <li>Maven for dependency management.</li> <li>MySQL database setup.</li> <li>Insomnia or similar API testing tool for manual testing.</li> <li>Cloudinary account (for image uploads).</li> </ul>


--------------------


# License
MIT License
