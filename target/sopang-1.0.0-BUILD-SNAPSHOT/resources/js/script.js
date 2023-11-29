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