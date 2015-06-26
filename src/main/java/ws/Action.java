package ws;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Optional;
import java.util.function.Consumer;

import javax.websocket.DecodeException;
import javax.websocket.EncodeException;

import org.apache.commons.lang3.tuple.Pair;

public class Action<T> {

  private final String name;
  private final JSONCoder<T> coder;
  private final Optional<Consumer<Pair<T, AbstractSocket>>> callback;
  
  public Action(JSONCoder<T> coder, String name, Optional<Consumer<Pair<T, AbstractSocket>>> callback) {
    this.name = name;
    this.callback = callback;
    this.coder = coder;
  }
  
  public Action(JSONCoder<T> coder, String name, Consumer<Pair<T, AbstractSocket>> callback) {
    this(coder, name, Optional.of(callback));
  }
  
  public Action(JSONCoder<T> coder, String name) {
    this(coder, name, Optional.empty());
  }
  
  public Optional<Consumer<Pair<T, AbstractSocket>>> getCallback() {
    return callback;
  }
  
  public JSONCoder<T> getCoder() {
    return coder;
  }
  
  public String getName() {
    return name;
  }
  
  public Message encode(T obj) {
    StringWriter writer = new StringWriter();
    try {
      if (!(obj instanceof Void)) { 
        coder.encode(obj, writer);
      }
    } catch (EncodeException | IOException e) {
      throw new IllegalArgumentException("The passed object is not valid", e);
    }
    return new Message(name, writer.toString());
  }
  
  public T decode(Message message) {
    if (message.getData() == null) {
      return null;
    }
    
    if (message.getAction().equals(this.name)) {
      StringReader reader = new StringReader(message.getData());
      try {
        return coder.decode(reader);
      } catch (DecodeException | IOException e) {
        throw new IllegalArgumentException("The passed message is not valid for this action.", e);
      }
    } else {
      throw new IllegalArgumentException("The passed message is not valid for this action.");
    }
  }
  
  public boolean decodes(Message message) {
    return message.getAction().equals(this.name);
  }
  
}
