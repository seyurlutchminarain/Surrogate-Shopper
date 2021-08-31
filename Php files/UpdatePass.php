<?php
$username = "s2088960";
$password = "Joker_366";
$database = "d2088960";
$link = mysqli_connect("127.0.0.1", $username, $password, $database);


$REQ_ID = $_REQUEST["id"];
$NEW_PWORD = $_REQUEST["pword"];

$update="UPDATE REQUESTER SET REQ_PASSWORD = ? WHERE REQ_ID = ?";
$Q=mysqli_prepare($link,$update);
$Q->bind_param("ss",$NEW_PWORD,$REQ_ID);
$Q->execute();

mysqli_close($link);

?>

