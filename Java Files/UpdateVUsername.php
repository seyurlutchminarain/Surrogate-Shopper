<?php
$username = "s2088960";
$password = "Joker_366";
$database = "d2088960";
$link = mysqli_connect("127.0.0.1", $username, $password, $database);
$output=array();

$VOL_ID = $_REQUEST["id"];
$NEW = $_REQUEST["new_username"];

$query="SELECT COUNT(*) FROM VOLUNTEER where VOL_USERNAME=? ";
$Qu=mysqli_prepare($link,$query);
$Qu->bind_param("s",$NEW);
$Qu->execute();
$result=$Qu->get_result();
$row=mysqli_fetch_assoc($result);
$count=$row["COUNT(*)"];


if($count==0){
     	$update="UPDATE VOLUNTEER SET VOL_USERNAME = ? WHERE VOL_ID = ?";
	$Q=mysqli_prepare($link,$update);
	$Q->bind_param("ss",$NEW,$VOL_ID);
	$Q->execute();
	echo "UserName Updated!";
}
else{
	echo "UserName Already exists try Another!";
}
mysqli_close($link);

?>

