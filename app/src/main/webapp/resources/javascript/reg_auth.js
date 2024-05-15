function redirectAuth(event){
    if (event.status == "success"){
        let redirect = document.getElementById("form:redirect");
        var r = redirect.innerText;
        if (r == "false"){
            redirect.click();
        }
    }
}
