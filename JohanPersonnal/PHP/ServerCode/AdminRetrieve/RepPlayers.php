<?php
/**
 * Created by PhpStorm.
 * User: JeremyC
 * Date: 11/18/2017
 * Time: 10:14 PM
 * Set the reported players.
 */

class RepPlayers
{
    var $PlayerID;
    var $username;
    var $numberWins;
    var $numberLoss;
    var $numberReports;
    var $imageID;
    var $message;


    /**
     * set the reported player and its information
     * @param $PlayerID The ID of the player
     * @param $username The username of the player
     * @param $numberWins The number of wins the player have
     * @param $numberLoss The number of losses the player have
     * @param $numberReports The number of reports the player have
     * @param $imageID The image id of the player
     * @param $message The message the player set
     */
    function set_PlayerR($PlayerID, $username, $numberWins, $numberLoss, $numberReports, $imageID, $message)
    {
        $this->PlayerID = $PlayerID;
        $this->username = $username;
        $this->numberWins = $numberWins;
        $this->numberLoss = $numberLoss;
        $this->numberReports = $numberReports;
        $this->imageID = $imageID;
        $this->message = $message;

    }
}