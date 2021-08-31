<?php
$username = "s2088960";
$password = "Joker_366";
$database = "d2088960";
$link = mysqli_connect("127.0.0.1", $username, $password, $database);
$output=array();

$ID = $_REQUEST["id"];
$Items = $_REQUEST["items"];


$query="INSERT INTO REQUESTS(REQ_ITEMS,REQ_ID) VALUES(?,?) ";
$stat=mysqli_prepare($link,$query);
$stat->bind_param("ss",$Items,$ID);
$stat->execute();

mysqli_close($link);

?>

