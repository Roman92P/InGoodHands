let passwordOne = document.getElementById('password');
let passwordTwo = document.getElementById('passwordRep');

let message = document.getElementById('passwordMessage');

let logbtn = document.getElementById('loginButton');

passwordTwo.addEventListener('input', function (event) {
    let value = passwordOne.value;
    let value1 = passwordTwo.value;
    if (value === value1) {
        message.innerText = '';
        message.innerText = 'password matches'
        message.style.color='green';
        logbtn.style.display='inline-block'
    }
    if (value !== value1) {
        message.innerText = '';
        message.innerText = 'password do not match';
        message.style.color='red';
        logbtn.style.display='none'


    }
})

