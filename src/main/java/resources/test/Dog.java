package resources.test;

public class Dog implements Animal {

  private String name;
  
  public Dog(String name) {
    this.name = name;
  }
  
  @Override
  public void setName(String name) {
    this.name = name;
  }
  
  @Override
  public String getName() {
    return name;
  }
  
  @Override
  public String makeNoise() {
    return "Wau, wau";
  }
  
}
