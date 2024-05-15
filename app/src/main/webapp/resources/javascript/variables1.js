class Point {
    constructor(x, y, color = "black"){
        this.x = x;
        this.y = y;
        this.color = color;
    }
}

var user_image = document.getElementById("user_image");
var unknow_dialog = document.getElementById("unknown_dialog");
var user_dialog = document.getElementById("user_dialog");

var x_field = document.getElementById("form:x");
var y_field = document.getElementById("form:y");
var r_field = document.getElementById("form:r");

var submit_button = document.getElementById("form:submit");
var reset_button = document.getElementById("form:clear");
var clear_table = document.getElementById("form:clear_table");
var show_table = document.getElementById("form:show_table");
var hidden_submit = document.getElementById("form:hidden_submit");

var x_area = document.getElementById("form:x_area");
var x_slider = document.getElementById("form:x_area_slider");
var y_area = document.getElementById("form:y_area");
var r_area = null;

var r_buttons = document.querySelectorAll(".link_choice");

const graph = document.getElementById("graphix");
const ctx = graph.getContext('2d');
const slider = document.getElementById("slider");
const scale_label = document.getElementById("scale");

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

reset_button.addEventListener("click", (event) => {
    event.preventDefault();
})