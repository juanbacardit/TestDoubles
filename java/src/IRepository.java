import java.util.List;

public interface IRepository<K,T> {
	public T getById(K id);
	public List<T> getAll();
	public boolean add(T element);
	public T pop(K id);
	public boolean removeById(K id);
	public boolean remove(T element);
	public boolean contains(K id);
	public T getNew();
}
