package ws.todo;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import ws.AbstractService;
import ws.WebApplicationPreconditions;

@Stateless
@Path("todo")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TodoService extends AbstractService {

  @Context
  private UriInfo uriInfo;

  @Context
  private HttpServletResponse servletResponse;

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
  public TodoItem add(TodoItem item) {
    WebApplicationPreconditions.checkNotNull(item, "001", "TodoItem is not allowed to be null.");
    WebApplicationPreconditions.checkArgument(item.getId() == null, "002", "Id is not allowed to be set.");

    em.persist(item);
    return item;
  }

  @GET
  public List<TodoItem> list() {
    CriteriaQuery<TodoItem> criteria = em.getCriteriaBuilder().createQuery(TodoItem.class);
    criteria.select(criteria.from(TodoItem.class));
    List<TodoItem> result = em //
        .createQuery(criteria)//
        .getResultList()//
        .stream()//
        .map(item -> {
          setLinkToItem(item);
          return item;
        })//
        .collect(Collectors.toList());
    return result;
  }

  @GET
  @Path("{id}")
  public TodoItem get(@PathParam("id") Long id) {
    TodoItem item = em.find(TodoItem.class, id);
    WebApplicationPreconditions.checkNotNull(item, "003", "No Item found with id `" + id + "`");
    return item;
  }

  @POST
  @Path("{id}")
  public TodoItem update(@PathParam("id") Long id, TodoItem updatedItem) {
    TodoItem item = get(id);

    item.setDescription(updatedItem.getDescription());
    item.setSummary(updatedItem.getSummary());

    return item;
  }

  @DELETE
  @Path("{id}")
  public void remove(@PathParam("id") Long id) {
    TodoItem item = get(id);
    em.remove(item);
  }

}