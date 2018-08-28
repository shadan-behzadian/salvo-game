fetch("http://localhost:8080/api/games", {
        method: "GET"
    }).then(function (response) {
        if (response.ok) {
            return response.json();
        }

    }).then(function (json) {

        app.gamesInfo = json.Games;

    })


var app = new Vue({
    el:'#games',
    data: {
            gamesInfo:[]

       }});


