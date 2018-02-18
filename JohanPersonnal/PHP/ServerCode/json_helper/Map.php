<?php
/**
 * Created by PhpStorm.
 * This is just a holder for the Map. It helps when we want to convert it into a JSON
 *
 *
 * User: johan
 * Date: 12.10.2017
 * Time: 15:58
 */

class Map
{
    var $mapID;
    var $mapName;
    var $mapDescription;
    var $author;

    /**
     * set the map
     * @param $mapID
     * @param $mapName
     * @param $mapDescription
     * @param $author
     */
    function set_map($mapID,$mapName,$mapDescription,$author){
        $this->mapID = $mapID;
        $this->mapName = $mapName;
        $this->mapDescription = $mapDescription;
        $this->author = $author;
    }
}