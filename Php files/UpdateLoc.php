<?php
$username = "s2088960";
$password = "Joker_366";
$database = "d2088960";
$link = mysqli_connect("127.0.0.1", $username, $password, $database);
$output=array();

$REQ_ID = $_REQUEST["id"];
$NEW_LOC = $_REQUEST["loc"];

$update="UPDATE REQUESTER SET REQ_LOCATION = ? WHERE REQ_ID = ?";
$Q=mysqli_prepare($link,$update);
$Q->bind_param("ss",$NEW_LOC,$REQ_ID);
$Q->execute();

mysqli_close($link);

?>

