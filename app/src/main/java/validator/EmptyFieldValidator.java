package validator;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;

@FacesValidator("emptyValidator")
public class EmptyFieldValidator implements Validator<Object> {
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        // TODO Auto-generated method stub
        String inputText = value.toString();
        String currentClass = (String) component.getAttributes().get("styleClass");
        System.out.println(currentClass.getClass());
        if (inputText.isEmpty()){
            component.getAttributes().put("styleClass", currentClass + " invalid_input");
            //context.addMessage(component.getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Field can not be empty", null));
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Поле не может быть пустым", null));
        }
        System.out.println(currentClass);
        currentClass = currentClass.replaceAll("invalid_input", "");
        System.out.println(currentClass);
        component.getAttributes().put("styleClass", currentClass);
    }
}
