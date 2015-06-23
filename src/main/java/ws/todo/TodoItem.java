package ws.todo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@XmlType(propOrder = { "summary", "description" })
@XmlAccessorType(XmlAccessType.FIELD)
public class TodoItem {

  @Id
  @XmlTransient
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String summary;
  private String description;

  @Transient
  private String link;

  public TodoItem() {}

  public TodoItem(String summary, String description, String link) {
      setSummary(summary);
      setDescription(description);
      setLink(link);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public String getLink() {
    return link;
  }

  public String getSummary() {
    return summary;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }

  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}
