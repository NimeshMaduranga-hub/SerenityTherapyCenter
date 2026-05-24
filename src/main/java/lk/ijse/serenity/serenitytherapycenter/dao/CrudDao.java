package lk.ijse.serenity.serenitytherapycenter.dao;

import java.util.List;

public interface CrudDao<T> extends SuperDao {

    boolean save(T entity);
    boolean update(T entity);
    boolean delete(int id);
    List<T> search(String text);
    List<T> getAll();
}
