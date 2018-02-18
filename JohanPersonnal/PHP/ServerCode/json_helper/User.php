<?php
include 'Player.php';
/**
 * Created by PhpStorm.
 * User: johan
 * Date: 23.10.2017
 * Time: 12:15
 */

class User extends Player
{
    //we already have username, message, and PlayerID..
    var $numberWins;
    var $numberLoss;
    var $imageID;

    /**
     * Set the user.
     * @param $playerID
     * @param $username
     * @param $message
     * @param $numberWins
     * @param $numberLoss
     * @param $imageID
     */
    function set_user($playerID,$username,$message,$numberWins,$numberLoss,$imageID){
        $this->set_player($playerID,$username,$message);
        $this->numberLoss = $numberLoss;
        $this->numberWins = $numberWins;
        $this->imageID = $imageID;
    }
}