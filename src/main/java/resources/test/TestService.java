package resources.test;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("tests")
@Produces(MediaType.APPLICATION_XML)
public class TestService {

  @GET
  public Animal get() {
    return new Dog("Nobi");
  }
  
  @POST
  public String test(Animal animal) {
    return animal.makeNoise();
  }
  
}
