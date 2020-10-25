
$("#hesaplaButton").click(function() {

     // Get tckimlik
    var tckimlik = $('#tckimlikInput').val();

    // Get ad soyad
    var adsoyad = $('#adsoyadInput').val();

    // Get aylık gelir
    var gelir = $('#gelirInput').val();

    // Get telefon
    var telefon = $('#telefonInput').val();

    if(!tckimlik || !gelir){
        alert("TC Kimlik Numarası ve Aylık Gelir girişi zorunludur!");
        return false;
    } else if(tckimlik.length !== 11){
        alert("TC Kimlik Numarası 11 basamaklı olmalıdır");
        return false;
    }

    // Make the http request using Ajax
    $.ajax({
        type: "POST",
        url: "/creditscore",
        data: JSON.stringify({
            "tc_id_number": tckimlik,
            "fullname": adsoyad,
            "monthly_salary": gelir,
            "phone_number": telefon
        }),
        contentType: "application/json;",
        success: function(data) {
            console.log(data);
            $("#sonucHesapla").text(data);
        },
        error: function(data) {
            alert(tckimlik + ' Tc Kimlik Numaralı Müşteri bulunamadı!');
        }
    });

});