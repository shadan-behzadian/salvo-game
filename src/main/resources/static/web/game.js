
//these following 4 lines you found them online to get "how to get a url variable in javascript"
//variable is gp=1 in the link and you want to find what gp equals to everytime.
var data;
var gridRows = ["","A","B","C","D","E","F","G","H","I","J","H","I","J","k","L","M"];
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


//       app1.data = json;
        console.log(json);
        data = json.gamePlayers;
     var ships = json.ships;

    for (let ship of ships){
    console.log(ship);
    app1.shipsInfo.push(ship);
    }

   console.log(app1.shipsInfo);
   positionShips();

   app1.you = json.gamePlayers[0].player.email;

    app1.opponent = json.gamePlayers[1].player.email;

    var salvoes = json.salvoes;

    for(let salvo of salvoes){
    console.log(salvo);
    app2.salvoInfo.push(salvo)}

    positionSalvos()

    });



var app1 = new Vue({
    el:'#grid',
    data: {
            shipsInfo :[],
            you:"",
            opponent:""

       }});

var app2 = new Vue({
    el:'#salvoGrid',
    data:{
    salvoInfo:[]

    }
})


// creating the grid with a pure javascript code
  function makeGrid(id){
     var table = document.getElementById(id);
     for(var i=0; i<11 ; i++){

        var tr = document.createElement("tr")

        for(var j=0; j< 11 ; j++){
        //becareful you wrote if(i=0) you were assigning i= 0 everytime
            if(i==0){
            var td = document.createElement("td");
                    td.textContent = j;
                    tr.appendChild(td);
            }else {
                if(j==0){
               var td = document.createElement("td");
               if(id=="grid"){
                                  td.textContent = gridRows[i];
                                  tr.appendChild(td);
                              }else{
                               td.textContent = gridRows[i].toLowerCase();
                               tr.appendChild(td);
                              }

                }else{
                //add id to each grid for later use e.g. "A1"
                 var td = document.createElement("td");
                 //for comparision you need == signes you made this mistake 10000 times
                 if(id == "grid"){
                 td.setAttribute("id", gridRows[i]+j);
                                    tr.appendChild(td);
                                 }else{
                                 td.setAttribute("id", gridRows[i].toLowerCase() + j);
                                                         tr.appendChild(td);
                                 }

                }
            }


        }

        table.appendChild(tr);
     }

     }






makeGrid("grid");
makeGrid("salvoGrid");


//you had to call this function the fetch because it is getting info from json and fetch is asynchornized so you need to
//call all the functions using those info in the fetch

function positionShips(){
//you fogot puttng the length and the positions in each of them
    for(i=0; i< app1.shipsInfo.length ; i++){
        var shipLocations = app1.shipsInfo[i].location
        for(j=0; j< shipLocations.length ; j++){
        //you have already created tds that have ids so now you just get them by their id and set classes for them
        //classes are already made in css
           document.getElementById(shipLocations[j]).setAttribute("class", app1.shipsInfo[i].type)
}
}
}



function positionSalvos() {

    for(i=0; i<app2.salvoInfo.length ; i++){
    var players = app2.salvoInfo[i]
    //this id is comeing from top of the page you are comparin if you are on the page with gp=1 or gp=2 ,...
    //    var url_string = window.location.href;
    //    var url = new URL(url_string);
    //    var id = url.searchParams.get("gp");
    //    console.log(id);
    if(players.hasOwnProperty(id)){

    console.log("found", id);
    }else{
    console.log("nothing")}
    console.log(players);


    }


}














