package ws;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public abstract class AbstractService {

  @PersistenceContext(unitName = "TodoApp")
  protected EntityManager em;
  
  protected Logger log = LoggerFactory.getLogger(this.getClass());

}
