fetch("http://localhost:8080/api/games", {
        method: "GET"
    }).then(function (response) {
        if (response.ok) {
            return response.json();
        }

    }).then(function (json) {


        app.gamesInfo = json.Games;



allPlayersScores();


    });


var app = new Vue({
    el:'#games',
    data: {
            gamesInfo:[]

       }});

function allPlayersScores(){
var arrayOfAllPlayersScores =[];
for(var i=0; i< app.gamesInfo.length ; i++){
// console.log(app.gamesInfo.length);
      var allScores = app.gamesInfo[i].scores

      for(j=0; j<allScores.length ; j++){
      var eachPlayerScore = app.gamesInfo[i].scores[j]
       arrayOfAllPlayersScores.push(eachPlayerScore);
      }
}
console.log(arrayOfAllPlayersScores);

var repeated = {};
for(var i=0; i< arrayOfAllPlayersScores.length; i++){

if(repeated.hasOwnProperty[playerID[i]]

}
else{
repeated[playerID[i]]={
"name": score[i]
}
}
console.log(yes);

}




//scoresForOnlyOnePlayer = []
//for(var i=0; i<arrayOfAllPlayersScores.length ; i++){
//
// var first = arrayOfAllPlayersScores[i]
//
// for(var j=i+1; j< arrayOfAllPlayersScores.length ; j++){
// if(first.playerID == arrayOfAllPlayersScores[j].playerID){
//
// scoresForOnlyOnePlayer.push(first);
// }
//
// }
//
//}





function createTh(text, targetRowToAppendTD) {
    var th = document.createElement("th")
    th.textContent = text;
    targetRowToAppendTD.appendChild(th);
}
    function calculateScores(){

 var table = document.getElementById("scoreTable");

        var tr = document.createElement("tr")
        createTh("Name", tr);
        createTh("Total", tr);
        createTh("Won", tr);
        createTh("lost", tr);
        createTh("tied", tr);

         table.appendChild(tr);

    }

calculateScores();

function createTd(text, targetRowToAppendTD) {
    var td = document.createElement("td")
    td.textContent = text;
    targetRowToAppendTD.appendChild(td);
}

function creatTableBody(){
var table = document.getElementById("gameScores");
 var tr = document.createElement("tr");

}








