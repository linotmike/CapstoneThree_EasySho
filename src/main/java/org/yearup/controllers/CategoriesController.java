package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yearup.data.CategoryDao;
import org.yearup.data.ProductDao;
import org.yearup.models.Category;
import org.yearup.models.Product;

import java.util.List;

// add the annotations to make this a REST controller
@RestController
// add the annotation to make this controller the endpoint for the following url
// http://localhost:8080/categories
@RequestMapping("categories")
// add annotation to allow cross site origin requests
@CrossOrigin()
public class CategoriesController {
    private CategoryDao categoryDao;
    private ProductDao productDao;

    @Autowired
    public CategoriesController(CategoryDao categoryDao, ProductDao productDao) {
        this.categoryDao = categoryDao;
        this.productDao = productDao;
    }

    @GetMapping
    public List<Category> getAll() {

        return categoryDao.getAllCategories();
    }


    @GetMapping("{id}")
    public Category getById(@PathVariable int id) {

        return categoryDao.getById(id);
    }

    // the url to return all products in category 1 would look like this
    // https://localhost:8080/categories/1/products
    @GetMapping("{categoryId}/products")
    public List<Product> getProductsById(@PathVariable int categoryId) {
        // get a list of product by categoryId
        return null;
    }


    @PostMapping
    public Category addCategory(@RequestBody Category category) {

        return categoryDao.create(category);
    }


    @PutMapping("{id}")
    public void updateCategory(@PathVariable int id, @RequestBody Category category) {
        categoryDao.update(id,category);
    }



    @DeleteMapping("{id}")
    public void deleteCategory(@PathVariable int id) {

        categoryDao.delete(id);
    }
}
