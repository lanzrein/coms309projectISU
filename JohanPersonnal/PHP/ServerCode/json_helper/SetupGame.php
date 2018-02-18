<?php
/**
 * Created by PhpStorm.
 * User: johan
 * Date: 05.11.2017
 * Time: 19:05
 */

class SetupGame
{

    var $leaderID;
    var $race1;
    var $race2;
    var $gameID;
    var $mapID;
    var $opponentID;
    var $gamemode;


    /**
     * Set the setup
     * @param $race1
     * @param $race2
     * @param $mapID
     * @param $leaderID
     * @param $opponentID
     * @param $gameID
     * @param $gamemode
     */
    function set_setup($race1, $race2, $mapID, $leaderID, $opponentID, $gameID, $gamemode){


        $this->leaderID = $leaderID;
        $this->race1 = $race1;
        $this->gamemode = $gamemode;
        $this->gameID= $gameID;
        $this->opponentID = $opponentID;
        $this->race2 = $race2;
        $this->mapID = $mapID;

    }

    public function set_fromlobby($lobby)
    {
        $this->set_setup(-1,-1,-1,$lobby->leaderID,$lobby->opponentID,$lobby->gameID,$lobby->gamemode);
    }
}