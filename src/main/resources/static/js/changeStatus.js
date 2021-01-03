// <label th:for="*{pickUpDate}">
//     <input type="date" th:field="${donation.pickUpDate}"/>
// </label>
// <label th:for="*{pickUpTime}">
//     <input type="time" th:field="${donation.pickUpTime}"/>
// </label>
// document.addEventListener("DOMContentLoaded", function() {




    let status = document.getElementById("packageStatus+${donation.id}");
    let newDatePlace = document.getElementById('newDate');

    status.addEventListener('change', function (event) {
        if (event.target.value === '2') {
            let htmlInputElement = document.createElement('input');
            htmlInputElement.setAttribute('type', 'date');
            htmlInputElement.setAttribute('th:field', '${donation.pickUpDate}');

            let htmlInputElement1 = document.createElement('input');
            htmlInputElement1.setAttribute('type', 'time');
            htmlInputElement1.setAttribute('th:field', '${donation.pickUpTime}');

            let htmlLabelElement = document.createElement('label');
            htmlLabelElement.setAttribute('th:for', '*{pickUpDate}');
            let htmlLabelElement1 = document.createElement('label');
            htmlLabelElement1.setAttribute('th:for', '*{pickUpTime}');

            htmlLabelElement.innerText = 'Podaj nową datę';
            htmlLabelElement1.innerText = 'Podaj nową godzinę';

            htmlLabelElement.appendChild(htmlInputElement);
            htmlLabelElement1.appendChild(htmlInputElement1);

            newDatePlace.appendChild(htmlLabelElement);
            newDatePlace.appendChild(htmlLabelElement1);
        }
        if (event.target.value === '1') {
            let elementById = document.getElementById('newDate');
            let nodeListOf = elementById.querySelectorAll("label");
            nodeListOf.forEach(function (ele) {
                ele.remove();
            })

        }
    })







// })