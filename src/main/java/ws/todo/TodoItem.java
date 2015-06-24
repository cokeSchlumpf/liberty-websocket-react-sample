package ws.todo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@XmlType(propOrder = { "id", "done", "description", "link" })
@XmlAccessorType(XmlAccessType.FIELD)
public class TodoItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private boolean done;
  private String description;
 

  @Transient
  private String link;

  public TodoItem() {}

  public TodoItem(boolean done, String summary, String link) {
      setDone(done);
      setDescription(summary);
      setLink(link);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
  
  public boolean isDone() {
    return done;
  }

  public String getLink() {
    return link;
  }

  public String getDescription() {
    return description;
  }

  public void setDone(boolean done) {
    this.done = done;
  }
  
  public void setLink(String link) {
    this.link = link;
  }

  public void setDescription(String summary) {
    this.description = summary;
  }

  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}
