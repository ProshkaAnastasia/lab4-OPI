package validator;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;
import service.UserService;

@FacesValidator("loginValidator")
public class LoginValidator implements Validator<Object> {
    UserService uService = new UserService();
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        // TODO Auto-generated method stub
        var ctx = FacesContext.getCurrentInstance();
        var messages = ctx.getMessages(component.getClientId());
        System.out.println("start messages: " + component.getClientId());
        while (messages.hasNext()){
            var message = messages.next();
            System.out.println(message.getSummary());
        }
        System.out.println("stop messages");
        String inputText = value.toString();
        if (inputText.isEmpty()){
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Поле не может быть пустым", null));
        } else if (uService.containsUser(inputText)){
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Пользователь с таким именем уже существует", null));
        }
    }
    
}
