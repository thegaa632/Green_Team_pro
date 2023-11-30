function execDaumPostcode() {
    console.log("우편번호가 실행됨");
    new daum.Postcode({
        oncomplete: function (data) {
// 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('zipcode').value = data.zonecode; //5자리 새우편번호 사용
            document.getElementById('address').value = data.address;
        }
    }).open();
}

function checkLength(inputElement, maxLength) {
    var inputValue = inputElement.value;

    if (inputValue.length > maxLength) {
        // 입력값의 길이가 최대 길이를 초과하면 최대 길이로 잘라냄
        inputElement.value = inputValue.slice(0, maxLength);
    }
}

// function fn_process_pay_order() {
//         let confirm_result = confirm("결제 하시겠습니까?");
//
//     if (confirm_result) {
//         document.getElementById('active').submit();
//     }
// }
