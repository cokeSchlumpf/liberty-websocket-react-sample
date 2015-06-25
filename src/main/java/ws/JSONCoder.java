package ws;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;

public abstract class JSONCoder<T> implements Encoder.TextStream<T>, Decoder.TextStream<T> {

  private Class<T> type;

  private ThreadLocal<ObjectMapper> mapper = new ThreadLocal<ObjectMapper>() {

    @Override
    protected ObjectMapper initialValue() {
      ObjectMapper mapper = new ObjectMapper();
      AnnotationIntrospector introspector = new JaxbAnnotationIntrospector(TypeFactory.defaultInstance());
      mapper.setAnnotationIntrospector(introspector);
      return mapper;
    }
  };

  @Override
  @SuppressWarnings("unchecked")
  public void init(EndpointConfig endpointConfig) {
    ParameterizedType $thisClass = (ParameterizedType) this.getClass().getGenericSuperclass();
    Type $T = $thisClass.getActualTypeArguments()[0];
    if ($T instanceof Class) {
      type = (Class<T>) $T;
    } else if ($T instanceof ParameterizedType) {
      type = (Class<T>) ((ParameterizedType) $T).getRawType();
    }
  }

  @Override
  public void encode(T object, Writer writer) throws EncodeException, IOException {
    mapper.get().writeValue(writer, object);
  }

  @Override
  public T decode(Reader reader) throws DecodeException, IOException {
    return mapper.get().readValue(reader, type);
  }

  @Override
  public void destroy() {

  }

}
