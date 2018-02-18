<?php

include '../json_helper/playerL.php';
/**
 * Created by PhpStorm.
 * User: JeremyC
 * Date: 11/4/2017
 * Time: 12:41 PM
 *
 * This php file retrieve the list of players' friend in-game information that has a record of the win ratio.
 * The win ratio is obtained by dividing the numberWin column with the numberLoss column in the Player table.
 * The information for the friend of the player is sorted in descending order based on the win ratio.
 */

$username = $_GET['username'];
$password = $_GET['password'];
if(empty($username)||empty($password)){
    die("Please enter a valid username and password");
}

$servername = "mysql.cs.iastate.edu";
$usernameDb = "dbu309vcb4";
$passwordDb = "qCaVEG0Z";
$dbname = "db309vcb4";
$store = array(); //array to stored data.
$index = 0; // index of an array

//create connection
$conn = new mysqli($servername,$usernameDb,$passwordDb,$dbname);

//check connection
if($conn -> connect_error)
{
    die("Connection failed!".$conn->connect_error);
}
/**
 * Calculate the win ratio of a certain player
 * @param $a numberWin column in Player table
 * @param $b numberLoss column in Player table
 * @return float|int the win ratio
 */
function calc($a, $b)
{
    if($a == 0 || $b == 0)
    {
        return 0;
    }

    return $a/$b;
}

/**
* compare two win ratio value and see which one of the two is larger
* @param $a the first win ratio
* @param $b the second win ratio
* @return int  return the comparison value
*/
function cmp($a, $b)
{
    if(calc($a["numberWin"],$a["numberLoss"]) == calc($b["numberWin"],$b["numberLoss"]))
    {
        return 0;
    }

    return (calc($a["numberWin"],$a["numberLoss"]) < calc($b["numberWin"],$b["numberLoss"])) ? 1 : -1;
}

$sql = "select f.Friends
        from Player p join Friends f
        on p.PlayerID = f.PlayerID
        where p.username = \"$username\" and p.password = \"$password\";";

$result = $conn->query($sql);

if ($result->num_rows > 0) {
    //request for each friend his info.
    $row = $result->fetch_assoc();

    $array = preg_split("/[,]+/",$row["Friends"],NULL,PREG_SPLIT_NO_EMPTY);

    foreach ($array as $friendID){
        $sql= "SELECT p.PlayerID, p.username,p.message,p.numberWins, p.numberLoss ,p.imageID, p.fastestTime
                  FROM Player p WHERE p.PlayerID = $friendID && (p.numberWins > 0 && p.numberLoss > 0);";
        $result = $conn->query($sql);
        //compile each friend info into an array
        $row = $result->fetch_assoc();
        $store[$index] = $row;
        $index++;
    }

    //sort the array based on win ratio.
    usort($store, "cmp");

    //echo every element in the array.
    for($i = 0; $i < count($store); $i++)
    {
        if($store[$i] != null) {
            echo json_encode($store[$i]);
            echo "#";
        }
    }

} else {
    echo "Error : wrong username or password combination.\n";
}