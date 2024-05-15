function processSlider(){
    console.log("slider value changed")
    var all_valid = validateX() && validateR() && validateY();
    activateSubmitButton(all_valid);
    activateResetButton(isEmpty());
}

y_area.addEventListener("input", (event) => {
    var all_valid = validateY() && validateR() && validateX();
    activateSubmitButton(all_valid);
    activateResetButton(isEmpty());
})

r_buttons.forEach((input) => {
    input.addEventListener("click", (event) => {
        event.preventDefault();
        console.log("Pressed");
        points = [];
        if (input.classList.contains("link_choice_active")){
            input.classList.remove("link_choice_active");
            r_area = null;
            drawPlot(slider.value, null);
            activateSubmitButton(validateR());
            activateResetButton(isEmpty());
        } else {
            r_buttons.forEach((button) => {
                button.classList.remove("link_choice_active");
            })
            input.classList.add("link_choice_active");
            r_area = input;
            var all_valid = validateR() && validateX() && validateY();
            drawPlot(slider.value, r_area.innerText);
            activateSubmitButton(all_valid);
            activateResetButton(isEmpty());
        }
    })
})

function validateX(){
    x_field.value = x_area.value;
    //setCookie("x", x_area.value);
    return true;
}

function validateY() {
    var re1 = RegExp("^-?$");
    var re2 = RegExp("^((-[0-5])|([0-5]))$");
    var re3 = RegExp("^((-[0-5])|([0-5]))\\.$");
    var re = RegExp("^((-[0-4])|([0-4]))(\\.[0-9]+)?$");
    while (true){
        console.log("validating y");
        if (y_area.value.search(re2) == 0 || y_area.value.search(re) == 0){
            console.log("y is valid");
            y_field.value = y_area.value;
            //setCookie("y", y_area.value);
            return true;
        }
        if (y_area.value.search(re1) == 0 || y_area.value.search(re3) == 0){
            //setCookie("y", "");
            return false;
        } else {
            y_area.value = y_area.value.slice(0, -1);
        }
    }
}

function validateR(){
    if (r_area != null){
        r_field.value = r_area.innerText;
        //setCookie("R", r_area.innerText);
        //console.log(getCookie("R"));
    }
    return r_area != null;
}

function activateSubmitButton(valid) {
    if (valid){
        submit_button.disabled = false;
    } else {
        submit_button.disabled = true;
    }
}

function activateResetButton(empty) {
    if (empty){
        reset_button.disabled = true;
    } else {
        reset_button.disabled = false;
    }
}

function isEmpty() {
    if (y_area.value == "" && x_area.value == "0" && r_area == null){
        return true;
    } else {
        return false;
    }
}

reset_button.addEventListener("click", (event) => {
    if (r_area){
        r_area.classList.remove("link_choice_active");
        r_area = null;
    }
    y_area.value = "";
    console.log(x_area.value);
    points = [];
    drawPlot(slider.value, null);
    //x_slider.setValue("0");
    //x_area.change("0");
    activateSubmitButton(false);
    activateResetButton(true);
})

/*
show_table.addEventListener("click", (event) => {

})
*/

clear_table.addEventListener("click", (event) => {
    points = [];
    if (r_area){
        drawPlot(slider.value, r_area.innerText);
    } else {
        drawPlot(slider.value, null);
    }
})

window.addEventListener("load", (event) => {
    console.log("Loading");
    submit_button.disabled = true;
    reset_button.disabled = true;
    let dt = document.getElementById("data_table");
})