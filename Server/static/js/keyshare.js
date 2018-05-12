$(document).ready(function () {
   $("#docbody").hide()
    $("#go").bind("click",function () {
       var key = $("#input").val()
        if (key == ""){
            $("#input").css("border" , "2px solid red");
        }else {
           $.get("getbyck?cnkey=" + $("#input").val(),function (data) {
               if (data != "0"){
                   $("#input").css("border" , "2px solid green");
                   $("#docbody").show()

                   var obj = JSON.parse(data);
                   var doc = obj["doc"]
                   doc = doc.replace("\n","<br><br>")
                   $("#text").html(doc)
               }
           })
        }
    });
});