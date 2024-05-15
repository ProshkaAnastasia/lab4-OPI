graph.addEventListener("click", (event) => {
    if (r_area == null){
        alert("Значение радиуса не определено. Для успешной обработки запроса укажите значение поля R.");
        return;
    }
    coord = graph.getBoundingClientRect()
    console.log(coord.left, coord.top);
    console.log(event.clientX - coord.left, event.clientY - coord.top);
    pnt = new Point((event.clientX - coord.left - scaleX) / slider.value, - (event.clientY - coord.top - scaleY) / slider.value);
    x_field.value = (pnt.x).toFixed(3);
    y_field.value = (pnt.y).toFixed(3);
    console.log(pnt);
    console.log("Sending request");
    hidden_submit.click();
})

slider.addEventListener("input", (event) => {
    scale_label.textContent = "Масштаб: " + slider.value + "px";
    drawPlot(slider.value, r_area?.innerText);
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



