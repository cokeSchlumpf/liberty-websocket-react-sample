package resources.test;

public class Cat implements Animal {

  private String name;
  
  private Cat() { }

  public Cat(String name) {
    this();
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String makeNoise() {
    return "MIAU";
  }

  
  
}
