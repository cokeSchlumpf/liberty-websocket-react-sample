package ws;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;

import ws.todo.TodoService;

@ApplicationPath("ws")
public class Application extends javax.ws.rs.core.Application {

  @Override
  public Set<Class<?>> getClasses() {
    Set<Class<?>> set = new HashSet<Class<?>>();
    set.add(TodoService.class);
    return set;
  }

}