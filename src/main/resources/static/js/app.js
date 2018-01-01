$(function() {
    let date = {};
    let token = $('#_csrf').attr('content');
    let header = $('#_csrf_header').attr('content');
    let startDate, endDate;
    $("input[type=button]#calc").on("click", function() {
        startDate = $("input[type=date]#start").val();
        endDate = $("input[type=date]#end").val();
        date['firstDate'] = startDate;
        date['secondDate'] = endDate;
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/calculateDate",
            data: JSON.stringify(date),
//            data: date
            dataType: "json",
            cache: false,
            timeout: 600000,
            beforeSend: function(xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function(data) {
                let msg = "<h4>Ajax response</h4><pre>" +
                data['msg'] + "</pre>";
                $("div#feedback").html(msg);
            },
            error: function(er) {
            let json = "<h4>Ajax response</h4><pre>" +
                er.responseText + "</pre>";
                $("div#feedback").html(json);
            }

        });
    })
})