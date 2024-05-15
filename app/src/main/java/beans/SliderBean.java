package beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;

@Named("slider")
@SessionScoped
public class SliderBean implements Serializable{
    private String value = "0";
    public String getValue(){
        return value;
    }
    public void setValue(String value){
        this.value = value;
    }
    public String changeSliderValue(){
        System.out.println("Slider value changed");
        return "redirect";
    }
}
