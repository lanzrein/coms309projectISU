<?php
/**
 * Created by PhpStorm.
 *
 * For now its really really basic.
 * In the future we will need to add much more variables to represent the gamestate.
 * User: johan
 * Date: 08.10.2017
 * Time: 10:20
 */

class Gamestate
{
    var $x;
    var $y;
    var $ready;

    /**
     * Set the gamestate
     * @param $x
     * @param $y
     * @param $ready
     */
    function set_gamestate($x,$y,$ready){
        $this->x = $x;
        $this->y = $y;
        $this->ready = $ready;
    }

}