<?php
/**
 * Created by PhpStorm.
 * User: johan
 * Date: 05.11.2017
 * Time: 17:32
 */

class lobby
{
    var $leaderID;
    var $leaderUsername;
    var $gamemode;
    var $gameID;
    var $opponentID;
    var $opponentUsername;

    /**
     * Set the lobby
     * @param $leaderID
     * @param $leaderUsername
     * @param $gamemode
     * @param $gameID
     * @param $opponentID
     * @param $opponentUsername
     */
    function set_lobby($leaderID, $leaderUsername, $gamemode, $gameID, $opponentID, $opponentUsername){


        $this->leaderID = $leaderID;
        $this->leaderUsername = $leaderUsername;
        $this->gamemode = $gamemode;
        $this->gameID= $gameID;
        $this->opponentID = $opponentID;
        $this->opponentUsername = $opponentUsername;

    }

}