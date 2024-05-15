function completeRequest(event){
    if(event.status == "success"){
        var lastPoint = getLastPoint();
        if (lastPoint){
            points.push(lastPoint);
            drawPoint(lastPoint);
        }
        console.log(lastPoint);
        x_field.value = x_area.value;
        y_field.value = y_area.value;    
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

