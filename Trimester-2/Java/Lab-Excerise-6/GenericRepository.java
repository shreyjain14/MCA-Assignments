import java.util.ArrayList;
import java.util.List;

public class GenericRepository<T, ID> implements Repository<T, ID> {
    private final List<T> entities = new ArrayList<>();

    @Override
    public void add(T entity) {
        entities.add(entity);
    }

    @Override
    public T findById(ID id) {
        for (T entity : entities) {
            if (entity.toString().contains(id.toString())) {
                return entity;
            }
        }
        return null;
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(entities);
    }

    @Override
    public void remove(ID id) {
        entities.removeIf(entity -> entity.toString().contains(id.toString()));
    }
}
