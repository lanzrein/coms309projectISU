<?php
/**
 * A class for setting player's information.
 * Created by PhpStorm.
 * User: JeremyC
 * Date: 10/7/2017
 * Time: 3:30 PM
 */

class playerL
{
    var $PlayerID;
    var $username;
    var $message;
    var $numberWins;
    var $numberLoss;
    var $imageID;
    var $fastestTime;

    /**
     * Set the player's information
     * @param $PlayerID Player's ID
     * @param $username username of the player
     * @param $message the message that the player set
     * @param $numberWins the number of wins that the player have
     * @param $numberLoss the number of losses that the player have
     * @param $imageID the image ID that the player chose
     * @param $fastestTime the fastest time in a single game that the player experienced.
     */
    function set_player($PlayerID,$username,$message,$numberWins,$numberLoss,$imageID,$fastestTime){
        $this->PlayerID = $PlayerID;
        $this->username = $username;
        $this->message = $message;
        $this->numberWins = $numberWins;
        $this->numberLoss = $numberLoss;
        $this->imageID = $imageID;
        $this->fastestTime = $fastestTime;

    }
}