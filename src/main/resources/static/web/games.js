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

var firstName = arrayOfAllPlayersScores[i].firstname
var score = arrayOfAllPlayersScores[i].score
var playerIDs = arrayOfAllPlayersScores[i].playerID

if(repeated.hasOwnProperty(playerIDs)){
    if(score == 1){
    repeated[playerIDs].totalWin += 1;
//    repeated[playerIDs].totalMatches +=1;
    repeated[playerIDs].totalScore +=1;



    }
        if(score == 0){
        repeated[playerIDs].totalLost += 1;
//        repeated[playerIDs].totalMatches +=1;

        }
            if(score == 0.5){
              repeated[playerIDs].totalTied += 1;
//              repeated[playerIDs].totalMatches +=1;
              repeated[playerIDs].totalScore += 0.5;
            }

}

else{

if(score == 1){
repeated[playerIDs]={
"firstName": firstName,
"totalWin":1,
"totalLost":0,
"totalTied":0,
//"totalMatches":1,
"totalScore": score
}
}
if(score == 0.5){
repeated[playerIDs]={
"firstName": firstName,
"totalWin":0,
"totalLost":0,
"totalTied":1,
//"totalMatches":1,
"totalScore": score
}
}
if(score == 0){
repeated[playerIDs]={
"firstName": firstName,
"totalWin":0,
"totalLost":1,
"totalTied":0,
//"totalMatches":1,
"totalScore": score
}
}


}


}

console.log(repeated);
//using another function in this function as it uses the result of this

//to sort the leaderboard table based on scores you need to change the big object to ann array becouase you can not sort
//an object but you can sort and array thats why you created an empthy array called sortable=[] and you are pushing all the
//small objects in to it then you are soritng : saying if a>b return -1 which means put a stament first if a<b return 1 which means
//order by putting the second statement first and then the smaller
//if they were equal (retures 0 ) which is the else statment add the other ones and order based on them
var sortable = [];
for (var key in repeated) {
    sortable.push(repeated[key]);
}
console.log(sortable)

sortable.sort(function(a,b){
if(a.totalScore > b.totalScore){
return -1;
}

else if(a.totalScore < b.totalScore){
return 1;
}
else {
//this is when they are equal then you order it based on the other ones
var aSum = a.totalWin + a.totalLost + a.totalTied;
var bSum = b.totalWin + b.totalLost + b.totalTied;
return aSum - bSum

}
});
console.log(sortable)


createTableBody(sortable);
}














scoresTable();

function createTh(text, targetRowToAppendTD) {
    var th = document.createElement("th")
    th.textContent = text;
    targetRowToAppendTD.appendChild(th);
}
function scoresTable(){

 var table = document.getElementById("scoreTable");

        var tr = document.createElement("tr")
        createTh("Name", tr);

        createTh("Won", tr);
        createTh("lost", tr);
        createTh("tied", tr);
//        createTh("Total number of matches", tr);
    createTh("Total Score", tr);
         table.appendChild(tr);

    }

function createTd(text, targetRowToAppendTD) {
    var td = document.createElement("td")
    td.textContent = text;
    targetRowToAppendTD.appendChild(td);
}

function createTableBody(scoreObject){

var table = document.getElementById("gameScores");

//*****to get legnth of an object::::::
//console.log(Object.keys(scoreObject).length);
 for(var i=0; i< scoreObject.length; i++){
 //you created inner obejct to not use the lines that you commented and use a varibale instead.
 var innerObject = scoreObject[i];
 var tr = document.createElement("tr");
 for(key in innerObject){
 createTd(innerObject[key],tr);
 }

 table.appendChild(tr);

// var tr = document.createElement("tr");
// console.log(key, scoreObject[key])
// createTd(scoreObject[key].firstName,tr);
// createTd(scoreObject[key].totalScore,tr);
// createTd(scoreObject[key].totalWin,tr);
// createTd(scoreObject[key].totalLost,tr);
// createTd(scoreObject[key].totalTied,tr);
// table.appendChild(tr);
 }

}










