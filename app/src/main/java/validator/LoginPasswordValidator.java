package validator;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;
import service.UserService;

@FacesValidator("userValidator")
public class LoginPasswordValidator implements Validator<Object> {
    UserService uService = new UserService();
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        // TODO Auto-generated method stub
        System.out.println("validating user");
        String password = (String) value;
        String login = (String) component.getAttributes().get("login");
        System.out.println("user: " + login + " " + password);
        if (!uService.containsUser(login)){
            context.addMessage("form:login", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Неверное имя пользователя", null));
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Неверное имя пользователя", null));
        } else if (uService.getUserByLoginAndPassword(login, password) == null){
            context.addMessage("form:password", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Неверный пароль", null));
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Неверный пароль", null));
        }
        
    }
    
}
