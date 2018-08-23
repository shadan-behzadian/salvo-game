
//these following 4 lines you found them online to get "how to get a url variable in javascript"
//variable is gp=1 in the link and you want to find what gp equals to everytime.

var url_string = window.location.href;
var url = new URL(url_string);
var id = url.searchParams.get("gp");
console.log(id);

fetch("http://localhost:8080/api/game_view/" + id, {
        method: "GET"
    }).then(function (response) {
        if (response.ok) {
            return response.json();
        }

    }).then(function (json) {

       console.log(json);

    });


var app1 = new Vue({
    el:'#grid',
    data: {
            gridRows:[],
            gridColumns:["","A","B","C","D","E","F","G","H","I","J"]

       }});

       console.log(app1.gridRows);




       function makeRow(){
       for(var i=1; i<11 ; i++){
       app1.gridRows.push(i)
       }
       }

       makeRow();

