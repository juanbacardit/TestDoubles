using System.Collections.Generic;

namespace TestDouble
{
public interface IRepository<K,T> {
	 T getById(K id);
	 List<T> getAll();
	 bool add(T element);
	 T pop(K id);
	 bool removeById(K id);
	 bool remove(T element);
	 bool contains(K id);
	 T getNew();
}
}