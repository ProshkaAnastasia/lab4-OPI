user_image.addEventListener("click", (event) => {
    if (unknow_dialog){
        if (unknow_dialog.classList.contains("shown")){
            unknow_dialog.classList.remove("shown")
        } else {
            unknow_dialog.classList.add("shown")
        }
    }
    if (user_dialog){
        if (user_dialog.classList.contains("shown")){
            user_dialog.classList.remove("shown")
        } else {
            user_dialog.classList.add("shown")
        }
    }
})