package rest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractService {

  @PersistenceContext(unitName = "TodoApp")
  protected EntityManager em;
  
  protected Logger log = LoggerFactory.getLogger(this.getClass());

}
