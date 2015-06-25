package rest;

import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "code", "message" })
public class WebApplicationExceptionDetail {

  private String code;
  private String message;
  
  public WebApplicationExceptionDetail() {}
  
  public WebApplicationExceptionDetail(String code, String message) {
    setCode(code);
    setMessage(message);
  }
  
  public String getCode() {
    return code;
  }
  
  public String getMessage() {
    return message;
  }
  
  public void setCode(String code) {
    this.code = code;
  }
  
  public void setMessage(String message) {
    this.message = message;
  }
  
}
