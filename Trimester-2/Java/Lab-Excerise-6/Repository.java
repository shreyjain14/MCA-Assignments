import java.util.List;

public interface Repository<T, ID> {
    void add(T entity);
    T findById(ID id);
    List<T> findAll();
    void remove(ID id);
}
