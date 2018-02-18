<?php
/**
 * Created by PhpStorm.
 * User: JeremyC
 * Date: 11/28/2017
 * Time: 9:20 PM
 *
 * Delete the entire row of the player ID that is similar to the provided ID.
 */

$servername = "mysql.cs.iastate.edu";
$usernameDb = "dbu309vcb4";
$passwordDb = "qCaVEG0Z";
$dbname = "db309vcb4";

$PlayerID = $_GET['PlayerID'];

if(empty($PlayerID))
{
    die('ERR : ID does not exist.');
}

//create connection
$conn = new mysqli($servername,$usernameDb,$passwordDb,$dbname);

if($conn -> connect_error)
{
    die("Connection failed!".$conn->connect_error);
}

$sql = "DELETE FROM Player WHERE PlayerID = $PlayerID";

if($conn->query($sql) == TRUE)
{
    echo "Successfully deleted account";
}

else{
    echo "Error in deleting account".$conn->error;
}

$conn->close();
?>