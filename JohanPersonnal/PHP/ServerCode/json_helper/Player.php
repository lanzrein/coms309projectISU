<?php
class Player{
    var $username;
    var $PlayerID;
    var $message;

    /**
     * set the player
     * @param $PlayerID
     * @param $username
     * @param $message
     */
    function set_player($PlayerID,$username,$message){
        $this->PlayerID = $PlayerID;
        $this->username = $username;
        $this->message = $message;
    }
}
