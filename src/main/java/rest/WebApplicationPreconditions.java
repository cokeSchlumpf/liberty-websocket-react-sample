package rest;

import java.util.function.Consumer;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.common.base.Preconditions;

public class WebApplicationPreconditions {

  private WebApplicationPreconditions() {}

  private static void check(Consumer<Void> f, String code, String message) {
    try {
      f.accept(null);
    } catch (Exception e) {
      WebApplicationExceptionDetail detail = new WebApplicationExceptionDetail(code, message);
      throw new WebApplicationException(e, Response.status(Response.Status.BAD_REQUEST).entity(detail).type(MediaType.APPLICATION_JSON).build());
    }
  }
  
  public static void checkNotNull(Object notNullObj, String code, String message) {
    check(v -> Preconditions.checkNotNull(notNullObj), code, message);
  }
  
  public static void checkArgument(boolean expression, String code, String message) {
    check(v -> Preconditions.checkArgument(expression), code, message);
  }

}
