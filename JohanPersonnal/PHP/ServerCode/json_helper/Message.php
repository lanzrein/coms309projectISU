<?php
/**
 * Created by PhpStorm.
 * User: johan
 * Date: 02.11.2017
 * Time: 18:14
 */

class Message
{
    var $messageID;
    var $fromID;
    var $fromUsername;
    var $message;

    /**
     * sett the message
     * @param $fromID
     * @param $fromUsername
     * @param $message
     */
    function set_message($fromID,$fromUsername,$message){
        $this->fromID = $fromID;
        $this->fromUsername = $fromUsername;
        $this->message = $message;
    }
}