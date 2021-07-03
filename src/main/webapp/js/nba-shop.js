/**
 * view-controller for nba-shop.html
 *
 *
 * @author  Luigi Spina
 */

/**
 * register listeners and load all spieler
 */
$(document).ready(function () {
    loadSpieler();

    /**
     * listener for buttons within spielerliste
     */
    $("#spieleranzeige").on("click", "button", function () {
        if (confirm("Wollen Sie diesen Spieler wirklich löschen?")) {
            deleteSpieler(this.value);
        }
    });
});

function loadSpieler() {

    $
        .ajax({
            url: "./resource/spieler/list",
            dataType: "json",
            type: "GET"
        })
        .done(showSpieler)
        .fail(function (xhr, status, errorThrown) {
            if (xhr.status == 403) {
                window.location.href = "./login.html";
            } else if (xhr.status == 404) {
                $("#message").text("keine Spieler vorhanden");
            }else {
                $("#message").text("Fehler beim Lesen der Spieler, Melden sie sich zuerst an.");
            }
        })
}

/**
 * shows all players as a table
 *
 * @param spielerData all players as an array
 */
function showSpieler(spielerData) {

    let table = document.getElementById("nba-shop");

    clearTable(table);

    $.each(spielerData, function (uuid, spieler) {
        if (spieler.vorname) {
            let row = table.insertRow(-1);
            let cell = row.insertCell(-1);
            cell.innerHTML = spieler.vorname;

            cell = row.insertCell(-1);
            cell.innerHTML = spieler.nachname;

            cell = row.insertCell(-1);
            cell.innerHTML = spieler.alter;

            cell = row.insertCell(-1);
            cell.innerHTML = spieler.schuh.schuhName;

            cell = row.insertCell(-1);
            cell.innerHTML = spieler.jersey.spielerName;

            cell = row.insertCell(-1);
            cell.innerHTML = spieler.schuh.preis;

            cell = row.insertCell(-1);
            cell.innerHTML = spieler.jersey.preis;


            cell = row.insertCell(-1);
            cell.innerHTML = "<a href='./spieleredit.html?uuid=" + uuid + "'>Bearbeiten</a>";

            cell = row.insertCell(-1);
            cell.innerHTML = "<button type='button' id='delete_" + uuid + "' value='" + uuid + "'>Löschen</button>";
        }
    });
}


function clearTable(table) {
    while (table.hasChildNodes()) {
        table.removeChild(table.firstChild);
    }
}

/**
 * send delete request for a player
 * @param spielerUUID
 */
function deleteSpieler(spielerUUID) {
    $
        .ajax({
            url: "./resource/spieler/delete?uuid=" + spielerUUID,
            dataType: "text",
            type: "DELETE",
        })
        .done(function (data) {
            loadSpieler();
            $("#message").text("Spieler gelöscht");

        })
        .fail(function (xhr, status, errorThrown) {
            $("#message").text("Fehler beim Löschen des Spielers");
        })
}