<?php
$username = "s2088960";
$password = "Joker_366";
$database = "d2088960";
$link = mysqli_connect("127.0.0.1", $username, $password, $database);


$VOL_ID = $_REQUEST["id"];
$NEW_PWORD = $_REQUEST["pword"];

$update="UPDATE VOLUNTEER SET VOL_PASSWORD = ? WHERE VOL_ID = ?";
$Q=mysqli_prepare($link,$update);
$Q->bind_param("ss",$NEW_PWORD,$VOL_ID);
$Q->execute();

mysqli_close($link);

?>

