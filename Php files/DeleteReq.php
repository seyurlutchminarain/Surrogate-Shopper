<?php
$username = "s2088960";
$password = "Joker_366";
$database = "d2088960";
$link = mysqli_connect("127.0.0.1", $username, $password, $database);
$output=array();

$R_ID = $_REQUEST["rid"];

$query="DELETE FROM REQUESTS WHERE R_ID = ? ";
$stat=mysqli_prepare($link,$query);
$stat->bind_param("s",$R_ID);
$stat->execute();

mysqli_close($link);

?>

