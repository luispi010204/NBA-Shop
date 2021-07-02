/**
 * view-controller for spieleredit.html
 *
 * @author  Luigi Spina
 */

/**
 * register listeners and load the book data
 */
$(document).ready(function () {
    loadSchuhe();
    loadJerseys();
    loadSpieler();

    /**
     * listener for submitting the form
     */
    $("#spielereditForm").submit(saveSpieler);

    /**
     * listener for button [abbrechen], redirects to bookshelf
     */
    $("#cancel").click(function () {
        window.location.href = "./nba-shop.html";
    });
});

/**
 *  loads the data of this spieler
 *
 */
function loadSpieler() {
    let spielerUUID = $.urlParam("uuid");
    if (spielerUUID) {
        $
            .ajax({
                url: "./resource/spieler/read?uuid=" + spielerUUID,
                dataType: "json",
                type: "GET"
            })
            .done(showSpieler)
            .fail(function (xhr, status, errorThrown) {
                if (xhr.status == 403) {
                    window.location.href = "./login.html";
                } else if (xhr.status == 404) {
                    $("#message").text("Kein Spieler gefunden");
                } else {
                    window.location.href = "./nba-shop.html";
                }
            })
    }
}

/**
 * shows the data of this book
 * @param  spieler  the book data to be shown
 */
function showSpieler(spieler) {
    $("#spielerUUID").val(spieler.spielerUUID);
    $("#vorname").val(spieler.vorname);
    $("#nachname").val(spieler.nachname);
    $("#alter").val(spieler.alter);
    $("#schuh").val(spieler.schuh.schuhUUID);
    $("#jersey").val(spieler.jersey.jerseyUUID);
}

/**
 * sends the player data to the webservice
 * @param form the form being submitted
 */
function saveSpieler(form) {
    form.preventDefault();

    let url = "./resource/spieler/";
    let type;

    let spielerUUID = $("#spielerUUID").val();
    if (spielerUUID) {
        type= "PUT";
        url += "update"
    } else {
        type = "POST";
        url += "create";
    }

    $
        .ajax({
            url: url,
            dataType: "text",
            type: type,
            data: $("#spielereditForm").serialize()
        })
        .done(function (jsonData) {
            window.location.href = "./nba-shop.html"
        })
        .fail(function (xhr, status, errorThrown) {
            if (xhr.status == 404) {
                $("#message").text("Dieser Spieler existiert nicht");
            } else {
                $("#message").text("Fehler beim Speichern des Spielers");
            }
        })
}

function loadSchuhe() {
    $
        .ajax({
            url: "./resource/schuh/list",
            dataType: "json",
            type: "GET"
        })
        .done(showSchuh)
        .fail(function (xhr, status, errorThrown) {
            if (xhr.status == 404) {
                $("#message").text("Kein Schuhmodel gefunden");
            } else {
                window.location.href = "./nba-shop.html";
            }
        })
}

function showSchuh(schuhe) {

    $.each(schuhe, function (uuid, schuh) {
        $('#schuh').append($('<option>', {
            value: schuh.schuhUUID,
            text: schuh.schuh
        }));
    });
}

function loadJerseys() {
    $
        .ajax({
            url: "./resource/jersey/list",
            dataType: "json",
            type: "GET"
        })
        .done(showJersey)
        .fail(function (xhr, status, errorThrown) {
            if (xhr.status == 404) {
                $("#message").text("Kein Jersey gefunden");
            } else {
                window.location.href = "./nba-shop.html";
            }
        })
}

function showJersey(jersey) {

    $.each(jersey, function (uuid, jersey) {
        $('#jersey').append($('<option>', {
            value: jersey.jerseyUUID,
            text: jersey.jersey
        }));
    });
}
