<?php
/**
 * Created by PhpStorm.
 * User: JeremyC
 * Date: 11/28/2017
 * Time: 11:31 PM
 *
 * The player that shares similar ID with the provided ID will have its numberReports column's value increase by one in the Player table.
 */

include '../database_helper.php';

$PlayerID = $_GET['PlayerID'];

if(empty($PlayerID))
{
    die('ERR : ID does not exist.');
}

//create connection
$conn = connectToDB();


$sql = "UPDATE Player 
        SET numberReports = numberReports + 1
        where PlayerID = $PlayerID";

if($conn->query($sql) == TRUE)
{
    echo "Successfully update account";
}

else{
    echo "Error in updating account".$conn->error;
}

$conn->close();
?>