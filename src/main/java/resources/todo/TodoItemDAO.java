package resources.todo;

import java.util.List;
import java.util.Observable;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rest.WebApplicationPreconditions;

@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class TodoItemDAO extends Observable {

  @PersistenceContext(unitName = "TodoApp")
  protected EntityManager em;

  protected Logger log = LoggerFactory.getLogger(this.getClass());

  @AroundInvoke
  protected Object handleAroundInvoke(InvocationContext ctx) throws Exception {
    Object result = ctx.proceed();

    if (ctx.getMethod().isAnnotationPresent(Lock.class) && ctx.getMethod().getAnnotation(Lock.class).value().equals(LockType.WRITE)) {
      log.debug("notify observers");
      em.flush();
      this.setChanged();
      this.notifyObservers(this.list());
    }

    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see resources.todo.ITodoItemDAO#add(resources.todo.TodoItem)
   */
  @Lock(LockType.WRITE)
  public TodoItem add(TodoItem item) {
    WebApplicationPreconditions.checkNotNull(item, "001", "TodoItem is not allowed to be null.");
    WebApplicationPreconditions.checkArgument(item.getId() == null, "002", "Id is not allowed to be set.");

    em.persist(item);
    return item;
  }

  /*
   * (non-Javadoc)
   * 
   * @see resources.todo.ITodoItemDAO#list()
   */
  @Lock(LockType.READ)
  public List<TodoItem> list() {
    CriteriaQuery<TodoItem> criteria = em.getCriteriaBuilder().createQuery(TodoItem.class);
    criteria.select(criteria.from(TodoItem.class));
    return em.createQuery(criteria).getResultList();
  }

  /*
   * (non-Javadoc)
   * 
   * @see resources.todo.ITodoItemDAO#get(java.lang.Long)
   */
  @Lock(LockType.READ)
  public TodoItem get(Long id) {
    TodoItem item = em.find(TodoItem.class, id);
    WebApplicationPreconditions.checkNotNull(item, "003", "No Item found with id `" + id + "`");
    return item;
  }

  /*
   * (non-Javadoc)
   * 
   * @see resources.todo.ITodoItemDAO#update(java.lang.Long,
   * resources.todo.TodoItem)
   */
  @Lock(LockType.WRITE)
  public TodoItem update(Long id, TodoItem updatedItem) {
    TodoItem item = get(id);

    item.setDone(updatedItem.isDone());
    item.setDescription(updatedItem.getDescription());

    return item;
  }

  /*
   * (non-Javadoc)
   * 
   * @see resources.todo.ITodoItemDAO#remove(java.lang.Long)
   */
  @Lock(LockType.WRITE)
  public void remove(Long id) {
    TodoItem item = get(id);
    em.remove(item);
  }

}
