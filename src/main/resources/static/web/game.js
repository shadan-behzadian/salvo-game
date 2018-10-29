
//these following 4 lines you found them online to get "how to get a url variable in javascript"
//variable is gp=1 in the link and you want to find what gp equals to everytime.
var data;
var test;
var gridRows = ["","A","B","C","D","E","F","G","H","I","J","H","I","J","k","L","M"];
var url_string = window.location.href;
var url = new URL(url_string);
var id = url.searchParams.get("gp");
console.log(id);

fetch("/api/game_view/" + id, {
        method: "GET"
    }).then(function (response) {
        if (response.ok) {
            return response.json();
        }

    }).then(function (json) {


//       app1.data = json;
        console.log(json);
        test = json;
        data = json.gamePlayers;
        console.log(data);
     var ships = json.ships;

    for (let ship of ships){
    console.log(ship);
    app1.shipsInfo.push(ship);
    }

   console.log(app1.shipsInfo);
   positionShips();


if(data[0].Id == id){
app1.you = data[0].player.email;
if(data.length == 2){
app1.opponent = data[1].player.email;
}
}else{
app1.you = data[1].player.email
if(data.length == 2){
app1.opponent = data[0].player.email}
}


//   app1.you = json.gamePlayers[0].player.email;
//if(  data.length == 2){
//    app1.opponent = json.gamePlayers[1].player.email;
//}
    var salvoes = json.salvoes;

    for(let salvo of salvoes){
    console.log(salvo);
    app2.salvoInfo.push(salvo)}

    positionSalvos(salvoes);

//
//    app3.MyHitsOnOpponent = json.MyHitsOnOpponent;
//console.log(app3.MyHitsOnOpponent)

showHits();


    })
    .catch(function(ex) {
           console.log('parsing failed', ex)
           console.log("dont cheat")
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
});

var app3 = new Vue({
el:'#hitTable',
data:{
MyHitsOnOpponent:[],
allTheHits:{}

}
});







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
                                    td.setAttribute("data-only","ships");
                                 }else{
                                 //you had to make these ids lowercase because in html the ids should be unique and before
                                 //you already have capital letter ids
                                 td.setAttribute("id", gridRows[i].toLowerCase() + j);
                                                         tr.appendChild(td);
                                                         td.setAttribute("data-salvo","places");
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



function positionSalvos(salvo) {
console.log("hi we are hereee");
    for(i=0; i<salvo.length ; i++){
//data is a global variableon top of the page   data = json.gamePlayers;
//here you are getting the gameplayer id of each positon of salvo
 var playersId = salvo[i][data[i].Id]
                      console.log(playersId);


    for(j=0; j<playersId.length; j++){


        for(let turn in playersId[j]){
            console.log(playersId[j][turn])
            console.log(turn);
           for(k=0; k<playersId[j][turn].length; k++){

           console.log(playersId[j][turn][k])

//this line says if the id of the player equals the id of the page then show the shots on the shots table
if(data[i].Id == id){
document.getElementById(playersId[j][turn][k].toLowerCase()).setAttribute("class","salvoShots");
document.getElementById(playersId[j][turn][k].toLowerCase()).textContent = turn;
//otherwise show them on the main table(the shots of enemmy)
}else{
//if the posotion of shots == the positin of already existing ships show the shots: so you check if that position
//already has an attribute "class"  to make sure there was a ship there to show the shots
if(document.getElementById(playersId[j][turn][k].toUpperCase()).hasAttribute("class")){
document.getElementById(playersId[j][turn][k].toUpperCase()).setAttribute("class","salvoShots");
//to show the turn on each of shots
document.getElementById(playersId[j][turn][k].toUpperCase()).textContent = turn;
}
}

           }

        }


  }}}



    //this id is comeing from top of the page you are comparin if you are on the page with gp=1 or gp=2 ,...
    //    var url_string = window.location.href;
    //    var url = new URL(url_string);
    //    var id = url.searchParams.get("gp");
    //    console.log(id);
//    if(players.hasOwnProperty(id)){
//
//    console.log("found", id);
//    }else{
//    console.log("nothing")}
//    console.log(players);





// beginingjavascript code for implementing teh darg and drop ship//





var tdInUse;
//the id of currently selected ship in drag start
var selectedShips;
var shipss = [];
var currentTdJustEmptied;

var carrier = document.getElementById("carrier");
shipss.push(carrier);
var battleship = document.getElementById("battleship");
shipss.push(battleship);
var patrolBoat = document.getElementById("patrolBoat");
shipss.push(patrolBoat);
var submarine = document.getElementById("submarine");
shipss.push(submarine);
var destroyer = document.getElementById("destroyer");
shipss.push(destroyer);

//creating the button to click on to rotate
//to rotate the ships when clicked on them
for (var i = 0; i < shipss.length; i++) {
    var divClick = document.createElement("div");
    divClick.setAttribute("id", "r" + i);
    //you call the function rotate ship here becouse in the function you explain that when the div inside is clicked
    //you get the parent div id and you change that id at the end of that function
    divClick.addEventListener('click', rotateShip);
    divClick.textContent = "O";
    console.log(i)
    shipss[i].appendChild(divClick);

}


//var width = document.querySelectorAll("div[data-width]")
var alltd = document.querySelectorAll('[data-only]');

console.log(alltd);

battleship.addEventListener('dragstart', dragStart);
battleship.addEventListener('dragend', dragEnd);

carrier.addEventListener('dragstart', dragStart);
carrier.addEventListener('dragend', dragEnd);

patrolBoat.addEventListener('dragstart', dragStart);
patrolBoat.addEventListener('dragend', dragEnd);

submarine.addEventListener('dragstart', dragStart);
submarine.addEventListener('dragend', dragEnd);

destroyer.addEventListener('dragstart', dragStart);
destroyer.addEventListener('dragend', dragEnd);


var mainShip;


function dragStart(event) {
    mainShip = this;

console.log(mainShip);
    console.log('start');
    //target is used so it gived you what is currently used based on event listener
    selectedShips = event.target.id;
    height = document.getElementById(event.target.id).getAttribute('data-height');
    width = document.getElementById(event.target.id).getAttribute('data-width');
    this.className += ' hold';
    setTimeout(() => (this.className = 'invisible'), 0);

    //  console.log(document.getElementsByClassName(selectedShips).length);
    //    if(document.getElementsByClassName(selectedShips).length != 1) {
    //     removeShipFromTd(tdInUse);
    //    }
}



function dragEnd(event) {
    console.log('End');

    mainShipDisappear(this);

}






for (var td of alltd) {
    td.addEventListener('dragover', dragOver);
    td.addEventListener('dragenter', dragEnter);
    td.addEventListener('dragleave', dragLeave);
    td.addEventListener('drop', beforeDrop);
}


function dragOver(e) {
    console.log(e);
    e.preventDefault();
    if (document.getElementsByClassName(selectedShips).length != 1 && tdInUse) {
        removeShipFromTd(tdInUse);
    }

}






function dragEnter(e) {
    e.preventDefault();
    console.log(e.target.id);
    console.log(this);



    if (height == 1) {
        hoverHorizental(this, " allowed")

    } else {
        hoverVertical(this, " allowed")
    }






}

function dragLeave() {
    console.log("Leave");

    if (height == 1) {

        removeHoverOrShipHorizental(this, "allowed");
    } else {

        removeHoverOrShipVertical(this, "allowed");
    }

}


function beforeDrop() {

//    if (!conflict(this)) {
        dragDrop(this, event);
//    } else {
//        console.log("error");
//    }
}


function dragDrop(el, event) {

    console.log(event.target.id);
    console.log("drop");
    el.setAttribute("class", selectedShips);




    if (height == 1) {

        printHorizentalShip(el, selectedShips);
    } else {

        printVerticalShip(el, selectedShips);
    }


    tdInUse = el;
    console.log(tdInUse);



    console.log(mainShip);






}






function rotateShip(event) {

    selectedShip = document.getElementById(event.target.id).parentElement.id;

    var height = document.getElementById(selectedShip).getAttribute('data-height');
    var width = document.getElementById(selectedShip).getAttribute('data-width');

    document.getElementById(selectedShip).setAttribute('data-height', width);
    document.getElementById(selectedShip).setAttribute('data-width', height);


}





function printHorizentalShip(thisTD, currentShipSelected) {
    console.log(currentShipSelected)

var shipData={
"type":"",
"location":[]
}


var tdHorizentalids = [];
    var theId = thisTD.getAttribute("id");
    thisTD.setAttribute("data-ship", "true")
    //to push the id of the current cell as well , before adding this line you would only get the id of the next
    //cells ad not the actual cell that you dropped something on
    tdHorizentalids.push(theId);
    console.log(theId);
    document.getElementById(theId).setAttribute("draggable", "true");
    var width = document.getElementById(selectedShips).getAttribute('data-width');


    for (var i = 0; i < (width - 1); i++) {
        var tdNext = document.getElementById(theId).nextSibling;
        tdNext.setAttribute("draggable", "true");
        tdNext.setAttribute("class", currentShipSelected);
        tdNext.setAttribute("data-ship", "true");

        var tdId = tdNext.getAttribute("id");
        tdHorizentalids.push(tdId);
        theId = tdId;


    }
    console.log(tdHorizentalids);
    shipData["type"]= currentShipSelected;
    shipData["location"] = tdHorizentalids;
    shipsPlaces.push(shipData);


}

function printVerticalShip(thisTD, currentShipSelected) {

var shipData={
"type":"",
"location":[]
}
    var height = document.getElementById(selectedShips).getAttribute('data-height');
var idsOfVerticalCells = [];
    var theId = thisTD.getAttribute("id");
    console.log(theId);
    idsOfVerticalCells.push(theId);
    //you need to make the actual td draggable to be able to move the ship on the grid
    thisTD.setAttribute("draggable", "true");
    thisTD.setAttribute("data-ship", "true");

    //console.log(typeof(theId));
    var splitId = theId.split("");
    //console.log(splitId);
    var splittedAlphabetToString = ([splitId[0]].toString());
    var index = (gridRows.indexOf(splittedAlphabetToString));
    //    console.log(index);




    for (i = 0; i < (height - 1); i++) {
        index = index + 1;
        console.log(index);
        var valueOfCurrentIndex = gridRows[index];
        console.log(valueOfCurrentIndex);
        console.log(splitId);
        //for the last colum becouse the id has a length of three when splited "A" "1" "0"
        if (splitId.length == 3) {
            var tdOfNextCell = document.getElementById(gridRows[index] + splitId[1] + splitId[2])
        } else {
            var tdOfNextCell = document.getElementById(gridRows[index] + splitId[1]);
            console.log(tdOfNextCell);
        }
        tdOfNextCell.setAttribute("class", currentShipSelected);
        //you need to make the actual td draggable to be able to move the ship on the grid
        //if you comment this line you can drag the ships over in the grid
        tdOfNextCell.setAttribute("draggable", "true");
        tdOfNextCell.setAttribute("data-ship", "true");


        var idOfNextCells = tdOfNextCell.getAttribute("id");
        console.log(idOfNextCells);
        idsOfVerticalCells.push(idOfNextCells);

    }
    console.log(idsOfVerticalCells);
     shipData["type"]= currentShipSelected;
        shipData["location"] = idsOfVerticalCells;
        shipsPlaces.push(shipData);


}


function hoverHorizental(thisTD, currentClass) {
    var width = document.getElementById(selectedShips).getAttribute('data-width');
    var theId = thisTD.getAttribute("id");
    thisTD.className += currentClass;

    // thisTD.classList.remove("hovered");

    for (var i = 0; i < (width - 1); i++) {
        var tdNext = document.getElementById(theId).nextSibling;
        tdNext.className += currentClass;
        var tdId = tdNext.getAttribute("id");
        theId = tdId;
    }



}

function hoverVertical(thisTD, currentClass) {
    var height = document.getElementById(selectedShips).getAttribute('data-height');

    var theId = thisTD.getAttribute("id");
    thisTD.className += currentClass;

    var splitId = theId.split("");

    var splittedAlphabetToString = ([splitId[0]].toString());
    var index = (gridRows.indexOf(splittedAlphabetToString));

    for (i = 0; i < (height - 1); i++) {
        index = index + 1;

        var valueOfCurrentIndex = gridRows[index];

        if (splitId.length == 3) {
            var tdOfNextCell = document.getElementById(gridRows[index] + splitId[1] + splitId[2])
        } else {
            var tdOfNextCell = document.getElementById(gridRows[index] + splitId[1]);
        }

        tdOfNextCell.className += currentClass;
        tdOfNextCell.removeAttribute("data-ship");

        var idOfNextCells = tdOfNextCell.getAttribute("id");

    }

}

function removeHoverOrShipHorizental(thisTD, currentClass) {

    var width = document.getElementById(selectedShips).getAttribute('data-width');
    var theId = thisTD.getAttribute("id");
    thisTD.classList.remove(currentClass);

    for (var i = 0; i < (width - 1); i++) {
        var tdNext = document.getElementById(theId).nextSibling;
        tdNext.classList.remove(currentClass);
        var tdId = tdNext.getAttribute("id");
        tdNext.removeAttribute("data-ship");
        theId = tdId;
    }


}

function removeHoverOrShipVertical(thisTD, currentClass) {

    var height = document.getElementById(selectedShips).getAttribute('data-height');

    var theId = thisTD.getAttribute("id");
    thisTD.classList.remove(currentClass);

    var splitId = theId.split("");

    var splittedAlphabetToString = ([splitId[0]].toString());
    var index = (gridRows.indexOf(splittedAlphabetToString));

    for (i = 0; i < (height - 1); i++) {
        index = index + 1;

        var valueOfCurrentIndex = gridRows[index];

        if (splitId.length == 3) {
            var tdOfNextCell = document.getElementById(gridRows[index] + splitId[1] + splitId[2])
        } else {
            var tdOfNextCell = document.getElementById(gridRows[index] + splitId[1]);
        }

        tdOfNextCell.classList.remove(currentClass);

        var idOfNextCells = tdOfNextCell.getAttribute("id");

    }

}


function removeShipFromTd(thisTD) {

    thisTD.classList.remove(selectedShips);
    thisTD.removeAttribute("data-ship");
    var theId = thisTD.getAttribute("id");

    if (width > 1) {
        removeHoverOrShipHorizental(thisTD, selectedShips);
    }
    if (height > 1) {

        removeHoverOrShipVertical(thisTD, selectedShips);

    }


}

function mainShipDisappear(realShip) {
    var tdOccupied = document.getElementsByClassName(selectedShips);
    console.log(tdOccupied);
    console.log(selectedShips);

    if (tdOccupied.length != 0) {
        realShip.style.display = "none";
        console.log("disappear");
    } else {


        realShip.setAttribute("class", selectedShips);
        console.log("reappear");

    }

}




//function conflict(thisTD) {
//
//    var conflict;
//
//    if (width > 1) {
//        var tdHorizental = [];
//        var theId = thisTD.getAttribute("id");
//        tdHorizental.push(thisTD);
//        for (var i = 0; i < (width - 1); i++) {
//            var tdNext = document.getElementById(theId).nextSibling;
//            var tdId = tdNext.getAttribute("id");
//            tdHorizental.push(tdNext);
//            theId = tdId;
//        }
//        for (i = 0; i < tdHorizental.length; i++) {
//
//
//            if (tdHorizental[i].getAttribute("id") != null) {
//console.log(tdHorizental[i].hasAttribute("data-ship"));
//                if (tdHorizental[i].hasAttribute("data-ship")) {
//                        console.log('true');
//                    conflict = true;
//                } else {
//                    console.log('false');
//                    conflict = false;
//                }
//            } else {
//                conflict = true;
//            }
//
//        }
//    }
//
//    return conflict;
//
//}


//end of javascript code for darg and drop implementing ships





var shipsPlaces =[];


document.getElementById("addShips").addEventListener("click",function(){


placeShips();

});

//hard coded this to test if fetch is working
//var shipsPlaces =[
//{"type":"submarine",
//"location":["A1","A2","A3"]},
//{"type":"carrier",
//"location":["B1","B2","B3"]},
//{"type":"battleship",
//"location":["G4","G5","G6"]},
//{"type":"destroyer",
//"location":["C1","C2","C3"]},
//{"type":"patrolBoat",
//"location":["E1","E2","E3"]}
//
//];




function placeShips(){

//you are puting the id of the person on the page instead of this variable (gameplayerId)) and you get this from the
//top of the page.
fetch('/api/games/players/'+id+'/ships' , {
       credentials: 'include',
       method: 'POST',
       headers: {

           'Content-Type': 'application/json'
       },
       body: JSON.stringify(shipsPlaces)
   }).then(function(response) {
       return response.json();
   }).then(function(json) {
    positionShips();
    location.reload();
       console.log('parsed json', json)
   }).catch(function(ex) {
       console.log('parsing failed', ex)
   });

   }



//to add salvos in the second table dinamically
var salvoPlacementTDs = document.querySelectorAll('[data-salvo]')

    for(var td of salvoPlacementTDs){
   td.addEventListener("click",addSalvo);
    }




var salvosForEachTurn =[]

function addSalvo(e){

if(e.target.className == "salvoShots"){
e.target.classList.remove("salvoShots");
console.log(e.target.id);
//position zero becouse there is only one object inside it
//locaton is an array inside that obeject its the key and tehvalue is an object
var theIndex = salvosForEachTurn.indexOf(e.target.id);

console.log(theIndex);
if(theIndex > -1){
salvosForEachTurn.splice(theIndex,1);
}


}else{
        if(salvosForEachTurn.length < 5){
          e.target.className = "salvoShots";
          salvosForEachTurn.push(e.target.id);
        }else{

        console.log("no more please");

        var modal = document.getElementById('myModal');

            modal.style.display = "block";


         // Get the <span> element that closes the modal
         var span = document.getElementsByClassName("close")[0];

         span.addEventListener("click",function(){
          modal.style.display = "none";})

        // When the user clicks anywhere outside of the modal, close it
        window.onclick = function(event) {
            if (event.target == modal) {
             modal.style.display = "none";
    }
}

        }
   }


console.log(salvosForEachTurn);
}











   document.getElementById("addSalvos").addEventListener("click",function(){

//   salvosPlaces=[
//   {"turn":1 , "location":["A1","B2"]},
//   {"turn":2 , "location":["C1","D2"]},
//   {"turn":3 , "location":["E3","F5"]},
//   {"turn":4 , "location":["D4","C7"]},
//   {"turn":5 , "location":["C4","B7"]}
//   ]

console.log("added")


   placeSalvos();


   })





   function placeSalvos(){

   fetch('/api/games/players/'+id+'/salvos' , {
          credentials: 'include',
          method: 'POST',
          headers: {

              'Content-Type': 'application/json'
          },
          body: JSON.stringify(salvosForEachTurn)
      }).then(function(response) {
          return response.json();

      }).then(function(json) {

       location.reload();
          console.log('parsed json', json)
      }).catch(function(ex) {
          console.log('parsing failed', ex)
      });
   }




   function showHits(){

 var allTheHits = {};

        for(var i = 0; i < test.MyHitsOnOpponent.length; i ++){

            for(var key in test.MyHitsOnOpponent[i]){


              // console.log(test.MyHitsOnOpponent[i][key].length);
              console.log(key);

                var theHits = [];

                for(var j = 0; j < test.MyHitsOnOpponent[i][key].length; j ++){

                var shipName = "";

                     for(var keys in test.MyHitsOnOpponent[i][key][j]){

                        console.log("Hit Pos: " + keys + " Ship: "+test.MyHitsOnOpponent[i][key][j][keys]);

                        shipName = test.MyHitsOnOpponent[i][key][j][keys];
                        theHits.push(keys);



                                              console.log(allTheHits);

}
                            allTheHits[key] = {"turn": key,
                                               "shipType":shipName,
                                               "hitPositions" : theHits.toString()}

                }
            }

        }

app3.allTheHits = allTheHits;

   }