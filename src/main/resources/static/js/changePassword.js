let passwordQuestion = document.getElementById('changePassword');
let passwordPlace = document.getElementById('passwordPlace');
let currentPassword = document.getElementById('currentPassword');
let currentPasswordInput = document.getElementById('currentPasswordInput');

passwordQuestion.addEventListener("change", function (event){
    if(passwordQuestion.checked == true){
        currentPassword.remove();
        let htmlInputElement = document.createElement('input');
        htmlInputElement.setAttribute('name', 'password');
        htmlInputElement.setAttribute('type','password')
        htmlInputElement.setAttribute('placeholder', 'Podaj nowe hasło');
        let htmlLabelElement = document.createElement('label');
        htmlLabelElement.setAttribute('th:for', '*{password}');
        htmlLabelElement.setAttribute('id','newPassword')
        htmlLabelElement.appendChild(htmlInputElement);
        passwordPlace.appendChild(htmlLabelElement);
    }else {
        let elementById = document.getElementById('newPassword');
        if(elementById!==null){
            elementById.remove();
        }
    }
})