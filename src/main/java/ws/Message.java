package ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;


@XmlType(propOrder = { "action", "data" })
@XmlAccessorType(XmlAccessType.FIELD)
public class Message {

  public static class MessageCoder extends JSONCoder<Message> {}

  private String action;

  private String data;
  
  public Message() {}
  
  public Message(String action, String data) {
    this.action = action;
    this.data = data;
  }

  public String getAction() {
    return action;
  }

  public String getData() {
    return data;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public void setData(String data) {
    this.data = data;
  }
  
  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
  

}
