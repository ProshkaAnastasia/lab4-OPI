package beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;

@Named("userData")
@SessionScoped
public class TestBean implements Serializable{
    private static final long serialVersionUID = 1L;
   private String name;
   
   public String getName() {
      return name;
   }
   
   public void setName(String name) {
      this.name = name;
   }

   public String getWelcomeMessage() {
      return "Hello " + name;
   }
}
