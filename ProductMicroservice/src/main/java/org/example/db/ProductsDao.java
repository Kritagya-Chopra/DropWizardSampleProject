package org.example.db;

import java.util.List;

import org.example.entity.Product;
import org.jdbi.v3.sqlobject.SqlObject;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;


public interface ProductsDao extends SqlObject {

    @SqlQuery("select * from products;")
    public List<Product> getProducts();

    @SqlQuery("select * from products where ProductID = :id")
    public Product getProduct(@Bind("id") final int id);

    @SqlUpdate("insert into products(Name, Quantity, Rating, Code, Price, Description, CreatedBy, UpdatedBy) values(:name, :quantity, :rating, :code, :price, :description, :createdBy, :updatedBy)")
    void createProduct(@BindBean final Product product);

    @SqlUpdate("update products set Name = coalesce(:name, Name), Quantity = coalesce(:quantity, Quantity), Rating = coalesce(:rating, Rating), Code = coalesce(:code, Code), Price = coalesce(:price, Price), Description = coalesce(:description, Description), UpdatedBy = coalesce(:updatedBy, UpdatedBy), UpdatedAt = NOW() where ProductID = :productID")
    void editProduct(@BindBean final Product product);

    @SqlUpdate("delete from products where ProductID = :id")
    int deleteProduct(@Bind("id") final int id);

    @SqlQuery("select last_insert_id();")
    public int lastInsertId();

    @SqlUpdate("update products set Quantity = Quantity-:quantity where ProductID = :id;")
    void updateQuantity(@Bind("id") int id, @Bind("quantity") int quantity);
}
