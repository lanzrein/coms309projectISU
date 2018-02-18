<?php
/**
 * A database helper that handles the connection
 * Created by PhpStorm.
 * User: johan
 * Date: 11.10.2017
 * Time: 10:28
 */
/**
 * connects to the database
 * @return mysqli an open connection to our database
 */
function connectToDB(){
    $servername = "mysql.cs.iastate.edu";
    $usernameDb = "dbu309vcb4";
    $passwordDb = "qCaVEG0Z";
    $dbname = "db309vcb4";

    //create connection
    $conn = new mysqli($servername,$usernameDb,$passwordDb,$dbname);
    //check connection
    if($conn -> connect_error){

        die("ERROR : Connection failed ! " .$conn->connect_error);
    }
    return $conn;

}