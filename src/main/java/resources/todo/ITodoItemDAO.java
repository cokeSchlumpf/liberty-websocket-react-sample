package resources.todo;

import java.util.List;
import java.util.Observer;

public interface ITodoItemDAO {

  public abstract TodoItem add(TodoItem item);

  public abstract List<TodoItem> list();

  public abstract TodoItem get(Long id);

  public abstract TodoItem update(Long id, TodoItem updatedItem);

  public abstract void remove(Long id);
  
  public abstract void addObserver(Observer o);
  
  public abstract void deleteObserver(Observer o);

}