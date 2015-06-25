package resources.todo;

import java.util.Arrays;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder.In;
import javax.websocket.EndpointConfig;

import org.apache.commons.lang3.tuple.Pair;

import ws.AbstractSocket;
import ws.Action;
import ws.JSONCoder;

public final class Actions {
  
  private static class VoidCoder extends JSONCoder<Void> {}
  
  public static final Action<TodoItem.TodoItems> LIST = new Action<TodoItem.TodoItems>(new TodoItem.TodoItemsCoder(), "LIST");
  
  public static final Action<Void> REGISTER = new Action<>(new VoidCoder(), "REGISTER");
  
  public List<Action<?>> values() {
    return Arrays.asList(LIST, REGISTER);
  }
  
  public void init(EndpointConfig cfg) {
    for (Action<?> action : this.values()) {
      action.getCoder().init(cfg);
    }
  }
  
}
