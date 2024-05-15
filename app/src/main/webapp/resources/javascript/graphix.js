var b1 = document.getElementById("form1:button1");
var b2 = document.getElementById("form1:button2");

console.log(b1);


b1.addEventListener("click", (event) => {
    event.preventDefault();
    console.log("Click button 2");
    b1.value = "djfkdjfdkjf";
    b2.click();
})



/*
<!---
    <h:form id="form1">
        <h:commandButton id="button1" value="#{pointList.name}"/>
        <h:commandButton id="button2" value="#{pointList.name}" action="#{pointList.processAreaRequest()}"/>
    </h:form>
    <h:outputScript library="javascript" name="graphix.js"/>
-->
*/