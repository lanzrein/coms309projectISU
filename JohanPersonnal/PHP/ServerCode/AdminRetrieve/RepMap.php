<?php
/**
 * Created by PhpStorm.
 * User: JeremyC
 * Date: 11/13/2017
 * Time: 6:47 PM
 *
 * Set the Reported map.
 */

class RepMap
{
    var $mapID;
    var $mapName;
    var $mapDescription;
    var $NumOffensiveRep;
    var $NumUnfairRep;

    /**
     * Set the reported map and its information.
     * @param $mapID The ID of the map
     * @param $mapName The name of the map
     * @param $mapDescription The description of the map
     * @param $NumOffensiveRep The number of offensive report that the map have
     * @param $NumUnfairRep The number of unfair report that the map have.
     */
    function set_PlayerR($mapID, $mapName, $mapDescription, $NumOffensiveRep, $NumUnfairRep)
    {
        $this->mapID = $mapID;
        $this->mapName = $mapName;
        $this->mapDescription = $mapDescription;
        $this->NumOffensiveRep = $NumOffensiveRep;
        $this->NumUnfairRep = $NumUnfairRep;
    }
}