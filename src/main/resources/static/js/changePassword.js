// let passwordQuestion = document.getElementById('changePassword');
// let passwordPlace = document.getElementById('passwordPlace');
// let currentPassword = document.getElementById('currentPassword');
// let currentPasswordInput = document.getElementById('currentPasswordInput');
//
// passwordQuestion.addEventListener("change", function (event){
//     if(passwordQuestion.checked === true){
//         currentPassword.remove();
//         let htmlInputElement = document.createElement('input');
//         htmlInputElement.setAttribute('name', 'password');
//         htmlInputElement.setAttribute('type','password')
//         htmlInputElement.setAttribute('placeholder', 'Podaj nowe hasło');
//         let htmlLabelElement = document.createElement('label');
//         htmlLabelElement.setAttribute('th:for', '${userDTO.password}');
//         htmlLabelElement.appendChild(htmlInputElement);
//
//         let htmlInputElement1 = document.createElement('input');
//         htmlInputElement1.setAttribute('name', 'passwordConfirmation');
//         htmlInputElement1.setAttribute('type','password')
//         htmlInputElement1.setAttribute('placeholder', 'Powtórz nowe hasło');
//         let htmlLabelElement1 = document.createElement('label');
//         htmlLabelElement1.setAttribute('th:for', '${userDTO.password}');
//         htmlLabelElement1.appendChild(htmlInputElement1);
//
//         let htmlInputElement2 = document.createElement('input');
//         htmlInputElement2.setAttribute('name', 'currentPassword');
//         htmlInputElement2.setAttribute('type','password')
//         htmlInputElement2.setAttribute('placeholder', 'Wprowadź stare hasło');
//         let htmlLabelElement2 = document.createElement('label');
//         htmlLabelElement2.setAttribute('th:for', '${userDTO.currentUserPassword}');
//         htmlLabelElement2.appendChild(htmlInputElement2);
//
//         let htmlDivElement = document.createElement('div');
//         htmlDivElement.setAttribute("th:object", "${userDTO}");
//         htmlDivElement.setAttribute('id','newPassword')
//
//         htmlDivElement.appendChild(htmlLabelElement);
//         htmlDivElement.appendChild(htmlLabelElement1);
//         htmlDivElement.appendChild(htmlLabelElement2);
//
//         passwordPlace.appendChild(htmlDivElement);
//     }else {
//         let elementById = document.getElementById('newPassword');
//         if(elementById!==null){
//             elementById.remove();
//         }
//     }
// })