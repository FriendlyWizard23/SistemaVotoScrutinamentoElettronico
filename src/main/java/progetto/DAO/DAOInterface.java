package progetto.DAO;

import java.util.List;

public interface DAOInterface<Type> {
	public Type get(String id);

	public List<Type> getAll();

	public void save(Type t);

	public void update(Type t, Type u);

	public void delete(Type t);
}