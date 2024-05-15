var field = document.getElementById("form:x");
var btn1 = document.getElementById("my_form:btn1");
var btn2 = document.getElementById("my_form:btn2");

var submit_button = document.getElementById("form:submit");
var reset_button = document.getElementById("form:clear");
var clear_table = document.getElementById("form:clear_table");
var show_table = document.getElementById("form:show_table");
var hidden_submit = document.getElementById("form:hidden_submit");

var x_area = document.getElementById("form:x_area");
var x_slider = document.getElementById("form:x_area_slider");
var y_area = document.getElementById("form:y_area");
var r_area = null;

var x_value = document.getElementById("form:x");
var y_value = document.getElementById("form:y");
var r_value = document.getElementById("form:r");

var r_buttons = document.querySelectorAll(".link_choice");

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

function processSlider(){
    console.log("slider value changed")
    x_value.value = x_area.value;
    var all_valid = validateX() && validateR() && validateY();
    activateSubmitButton(all_valid);
    activateResetButton(isEmpty());
}

y_area.addEventListener("input", (event) => {
    var all_valid = validateY() && validateR() && validateX();
    activateSubmitButton(all_valid);
    activateResetButton(isEmpty());
})

function validateX(){
    setCookie("x", x_area.value);
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
            y_value.value = y_area.value;
            setCookie("y", y_area.value);
            return true;
        }
        if (y_area.value.search(re1) == 0 || y_area.value.search(re3) == 0){
            setCookie("y", "");
            return false;
        } else {
            y_area.value = y_area.value.slice(0, -1);
        }
    }
}

function validateR(){
    if (r_area != null){
        r_value.value = r_area.innerText;
        setCookie("R", r_area.innerText);
        console.log(getCookie("R"));
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

/*
window.addEventListener("load", (event) => {
    var x = getCookie("x");
    var y = getCookie("y");
    var R = getCookie("R")
    console.log(x, y, R);
    if (x != null){
        x_area.value = x;
        var all_valid = validateX() && validateR() && validateY();
        activateSubmitButton(all_valid);
        activateResetButton(isEmpty());
    }
    if (y != null){
        y_area.value = y;
        var all_valid = validateY() && validateR() && validateX();
        activateSubmitButton(all_valid);
        activateResetButton(isEmpty());
    }
    if (R != null){
        r_buttons.forEach((input) => {
            if (input.innerText == R){
                input.classList.add("link_choice_active");
                r_area = input;
                var all_valid = validateR() && validateX() && validateY();
                activateSubmitButton(all_valid);
                activateResetButton(isEmpty());
            }
        })
    }
})
*/

function getCookie(name){
    document.cookie = "";
    var answer = null;
    document.cookie.split(";").forEach((input) => {
        var pair = input.replaceAll(" ", "").split("=")
        if (pair[0] == name && pair[1] != ""){
            answer = pair[1];
        }
    })
    return answer;
}

function setCookie(name, value){
    document.cookie = `${name}=${value}`;
}

function isEmpty() {
    if (y_area.value == "" && x_area.value == "" && r_area == null){
        return true;
    } else {
        return false;
    }
}

const slider = document.getElementById("slider");
const scale_label = document.getElementById("scale");

slider.addEventListener("input", (event) => {
    scale_label.textContent = "Масштаб: " + slider.value + "px";
    drawPlot(slider.value, r_area?.innerText);
})

function completeRequest(event){
    if(event.status == "success"){
        var lastPoint = getLastPoint();
        if (lastPoint){
            points.push(lastPoint);
            drawPoint(lastPoint);
        }
        console.log(lastPoint);
        x_value.value = x_area.value;
        y_value.value = y_area.value;    
    }   
}

function getLastPoint(){
    let dt = document.getElementById("data_table");
    if (dt.rows.length > 1){
        let color = colors[dt.rows[1].cells[3].innerText == "true"];
        let lastPoint = new Point(dt.rows[1].cells[0].innerText, dt.rows[1].cells[1].innerText, color)
        return lastPoint;
    }
    
}




const graph = document.getElementById('graphix');
const ctx = graph.getContext('2d');
graph.clientHeight = graph.clientWidth;
const scaleX = graph.clientWidth / 2;
const scaleY = graph.clientHeight / 2;
var start_point = null;
var end_point = null;
var move = false;
var scale = slider.value
const colors = Array(2)
colors[false] = "red";
colors[true] = "green";
var points = [];

class Point {
    constructor(x, y, color = "black"){
        this.x = x;
        this.y = y;
        this.color = color;
    }
}

graph.addEventListener("click", (event) => {
    if (r_area == null){
        alert("Значение радиуса не определено. Для успешной обработки запроса укажите значение поля R.");
        return;
    }
    coord = graph.getBoundingClientRect()
    console.log(coord.left, coord.top);
    console.log(event.clientX - coord.left, event.clientY - coord.top);
    pnt = new Point((event.clientX - coord.left - scaleX) / slider.value, - (event.clientY - coord.top - scaleY) / slider.value);
    x_value.value = (pnt.x).toFixed(3);
    y_value.value = (pnt.y).toFixed(3);
    console.log(pnt);
    console.log("Sending request");
    hidden_submit.click();
})

function drawPoint(point){
    console.log(point.color);
    ctx.beginPath();
    ctx.fillStyle = point.color;
    ctx.arc(point.x * slider.value, -point.y * slider.value, slider.value / 20, 0, 2 * Math.PI);
    ctx.fill();
    ctx.closePath();
}

function drawPlot(scale, R) {
    clearCanvas();
    drawGrid(scale);
    drawAxis(scale);
    if (R == null){
        return;
    }

    ctx.fillStyle = "rgba(100,150,185,0.5)";

    ctx.beginPath()
    ctx.moveTo(0, 0);
    ctx.lineTo(0, -R / 2 * scale);
    ctx.lineTo(-R / 2 * scale, 0);
    ctx.fill();
    ctx.closePath();

    ctx.beginPath()
    ctx.moveTo(0, 0);
    ctx.lineTo(0, -R * scale);
    ctx.lineTo(R * scale / 2, -R * scale);
    ctx.lineTo(R * scale/ 2, 0);
    ctx.lineTo(0, 0);
    ctx.fill();
    ctx.closePath();

    ctx.beginPath();
    ctx.arc(0, 0, R * scale, Math.PI / 2, Math.PI, false);
    ctx.lineTo(0, 0);
    ctx.fill();
    ctx.closePath();

    points.forEach((point) => {
        drawPoint(point);
    })
}

function drawGrid(scale) {
    for (let i = 0; i < scaleX / scale; i++){
        ctx.beginPath();
        ctx.strokeStyle = "#c9c3c3";
        ctx.moveTo(i * scale, scaleY);
        ctx.lineTo(i * scale, -scaleY);
        ctx.moveTo(- i * scale, scaleY);
        ctx.lineTo(- i * scale, -scaleY);
        ctx.stroke();
        ctx.font = scale / 4 + "px Arial";
        ctx.textBaseline = 'top';
        ctx.textAlign = 'center';
        if (i != 0){
            ctx.fillText(i, i * scale, 0);
            ctx.fillText("-" + i, - i * scale, 0);
        }
        ctx.closePath();
    }
    for (let i = 0; i < scaleY / scale; i++){
        ctx.beginPath();
        ctx.strokeStyle = "#c9c3c3";
        ctx.moveTo(-scaleX, i * scale);
        ctx.lineTo(scaleX, i * scale);
        ctx.moveTo(-scaleX, - i * scale);
        ctx.lineTo(scaleX, - i * scale);
        ctx.stroke();
        ctx.font = scale / 4 + "px Arial";
        ctx.textAlign = 'right';
        ctx.textBaseline = 'middle';
        if (i != 0){
            ctx.fillText(i, - scale / 10, - i * scale);
            ctx.fillText("-" + i, - scale / 10, i * scale);
        }
        ctx.closePath();
    }
    ctx.textBaseline = 'top';
    ctx.fillText(0, - scale / 10, 0);
}

function drawAxis(scale) {
    ctx.beginPath();
    ctx.strokeStyle = "black";
    ctx.moveTo(-scaleX, 0);
    ctx.lineTo(scaleX, 0);
    ctx.moveTo(0, scaleY);
    ctx.lineTo(0, -scaleY);
    ctx.stroke();
    ctx.moveTo(scaleX, 0);
    ctx.lineTo(scaleX - scale / 2, - scale / 10);
    ctx.lineTo(scaleX - scale / 4, 0);
    ctx.lineTo(scaleX - scale / 2, scale / 10);
    ctx.lineTo(scaleX, 0);
    ctx.fill();
    ctx.moveTo(0, - scaleY);
    ctx.lineTo(scale / 10, - scaleY + scale / 2);
    ctx.lineTo(0, - scaleY + scale / 4);
    ctx.lineTo(- scale / 10, - scaleY + scale / 2);
    ctx.lineTo(0, - scaleY);
    ctx.fill();
    ctx.stroke();
    ctx.closePath();
}

function clearCanvas() {
    ctx.fillStyle = "white";
    ctx.fillRect(- scaleX, - scaleY, scaleX * 2, scaleY * 2);
    ctx.fillStyle = "black";
}

function draw(){
    console.log(scaleX, scaleY);
}

ctx.translate(scaleX, scaleY);
drawPlot(50, null);


