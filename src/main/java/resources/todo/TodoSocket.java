package resources.todo;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.websocket.CloseReason;
import javax.websocket.DecodeException;
import javax.websocket.EncodeException;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ws.AbstractSocket;
import ws.Action;
import ws.Message;

@Stateless
@ServerEndpoint(value = "/todo", encoders = { Message.MessageCoder.class }, decoders = { Message.MessageCoder.class })
public class TodoSocket extends AbstractSocket implements Observer {

  protected final Logger log = LoggerFactory.getLogger(this.getClass());
  
  Session currentSession = null;
  Message.MessageCoder coder = new Message.MessageCoder();
  Actions actions = new Actions();

  @EJB
  private TodoItemDAO dao;

  @OnOpen
  public void onOpen(Session session, EndpointConfig ec) {
    currentSession = session;
    coder.init(ec);
    actions.init(ec);
  }

  @OnMessage
  public void receiveMessage(String message, final Session session) {    
    try {
      Message msg = coder.decode(new StringReader(message));
      
      log.debug("Received message " + message);

      for (Action<?> action : actions.values()) {
        if (action.decodes(msg)) {
          if (action.equals(Actions.LIST)) {
            dao.addObserver(this);
            sendMessage(Actions.LIST, new TodoItem.TodoItems(dao.list()));
          }
        }
      }
    } catch (DecodeException | IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Send a message to all clients in the same room as this client.
   * 
   * @param message
   */
  public <T> void sendMessage(Action<T> action, T obj) {
    try {
      if (this.currentSession.isOpen()) {
        this.currentSession.getBasicRemote().sendObject(action.encode(obj));
      }
    } catch (IOException ioe) {
      ioe.printStackTrace();
    } catch (EncodeException e) {
      e.printStackTrace();
    }
  }

  @OnClose
  public void onClose(Session session, CloseReason reason) {
    log.debug("Session closed.");
    try {
      this.dao.deleteObserver(this);
    } catch (Exception e) {
      // ignore ...
    }
  }

  @OnError
  public void onError(Throwable t) {
    // no error processing will be done for this sample
    t.printStackTrace();
  }

  @Override
  @SuppressWarnings("unchecked")
  public void update(Observable o, Object object) {
    if (object instanceof List) {
      log.debug("Update List on session...");
      this.sendMessage(Actions.LIST, new TodoItem.TodoItems((List<TodoItem>) object));
    }
  }

}
