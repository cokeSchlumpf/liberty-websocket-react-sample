package resources.todo;

import java.net.URI;
import java.util.List;
import java.util.Observer;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import rest.AbstractService;

@Stateless
@Path("todos")
@Produces(MediaType.APPLICATION_JSON)
public class TodoService extends AbstractService implements ITodoItemDAO {

  @Context
  private UriInfo uriInfo;

  @Context
  private HttpServletResponse servletResponse;

  @EJB(beanName = "TodoItemDAO")
  private ITodoItemDAO dao;

  @AroundInvoke
  protected Object handleAroundInvoke(InvocationContext ctx) throws Exception {
    Object result = ctx.proceed();

    // If the response is a TodoItem, add location header and link to response.
    if (result instanceof TodoItem) {
      TodoItem item = (TodoItem) result;
      em.flush();
      setLinkToItem(item);
      servletResponse.addHeader("Location", item.getLink());
    }

    servletResponse.addHeader("Access-Control-Allow-Origin", "*");

    return result;
  }

  private void setLinkToItem(TodoItem item) {
    URI uri;
    if (uriInfo.getPathParameters().size() == 0) {
      uri = uriInfo.getAbsolutePathBuilder().path(item.getId().toString()).build();
    } else {
      uri = uriInfo.getAbsolutePathBuilder().build();
    }

    item.setLink(uri.toString());
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public TodoItem add(TodoItem item) {
    return dao.add(item);
  }

  @GET
  @Consumes(MediaType.MEDIA_TYPE_WILDCARD)
  public List<TodoItem> list() {
    return dao.list();
  }

  @GET
  @Path("{id}")
  @Consumes(MediaType.MEDIA_TYPE_WILDCARD)
  public TodoItem get(@PathParam("id") Long id) {
    return dao.get(id);
  }

  @PUT
  @Path("{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  public TodoItem update(@PathParam("id") Long id, TodoItem updatedItem) {
    return dao.update(id, updatedItem);
  }

  @DELETE
  @Path("{id}")
  @Consumes(MediaType.MEDIA_TYPE_WILDCARD)
  public void remove(@PathParam("id") Long id) {
    dao.remove(id);
  }
  
  @Override
  public void addObserver(Observer o) {
    // TODO Auto-generated method stub
  }
  
  @Override
  public void deleteObserver(Observer o) {
    // TODO Auto-generated method stub
    
  }

}