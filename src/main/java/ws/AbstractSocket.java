package ws;


public abstract class AbstractSocket {

  public abstract <T> void sendMessage(Action<T> action, T obj);
  
  
  
}
