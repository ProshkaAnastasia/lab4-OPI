package beans;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;

@Named("validatorBean")
@ViewScoped
public class CheckValidityBean implements Serializable {
    public boolean checkValidationResult(String element){
        //System.out.println(element);
        FacesContext ctx = FacesContext.getCurrentInstance();
        
    
        
        var errorMessages = ctx.getMessageList(element);
        //errorMessages.clear();
        /* 
        System.out.println(errorMessages);
        errorMessages.forEach((message) -> {
            System.out.println(message);
        });
        */
        if (errorMessages != null && !errorMessages.isEmpty()){
            return true;
        }
        return false;
    }
}
