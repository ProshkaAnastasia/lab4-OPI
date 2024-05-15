package normal_beans;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;

import beans.DataBaseCDIBean;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIInput;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.ValidatorException;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import models.UPRecord;
import models.User;

@FacesValidator("authorizationValidator")
@Named("authorization")
@RequestScoped
public class AuthorizationBean implements Serializable {
    @Inject
    UserBean userBean;

    @Inject
    DataBaseCDIBean dataBase;

    String login;
    String password;

    public String getLogin(){
        return login;
    }
    public void setLogin(String login){
        this.login = login;
    }

    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }

    public String signIn(){
        User user = dataBase.getUser(login, password);
        Collections.sort(user.getRecords(), Comparator.comparing(UPRecord::getTime).reversed());
        //user.getRecords().stream().sorted(Comparator.comparing(UPRecord::getTime));
        System.out.println("Sign up");
        System.out.println("Login: " + login);
        System.out.println("Password: " + password);
        if (user != null){
            userBean.changeUser(user);
        }
        return "redirect";
    }

    public String quit(){
        userBean.changeUser(null);
        return "redirect";
    }

    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        UIInput field1 = (UIInput) context.getViewRoot().findComponent("form:login");
        String login = (String) field1.getValue();
        String password = value.toString();
        if (!dataBase.containsUser(login)){
            context.addMessage("form:login", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Неверное имя пользователя", null));
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Неверное имя пользователя", null));
        } else if (dataBase.getUser(login, password) == null){
            context.addMessage("form:password", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Неверный пароль", null));
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Неверный пароль", null));
        }
    }
}
