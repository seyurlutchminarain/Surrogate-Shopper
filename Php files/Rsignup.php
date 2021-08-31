<?php
$username = "s2088960";
$password = "Joker_366";
$database = "d2088960";

$link = mysqli_connect("127.0.0.1", $username, $password, $database);


$Username = $_REQUEST["name"];
$Fname = $_REQUEST["fname"];
$Lname = $_REQUEST["lname"];
$Location = $_REQUEST["location"];
$Password = $_REQUEST["pword"];
$DOB = $_REQUEST["dob"];
 
//checking if username already exists.
$query="SELECT COUNT(*) FROM REQUESTER where REQ_USERNAME=? ";
$Qu=mysqli_prepare($link,$query);
$Qu->bind_param("s",$Username);
$Qu->execute();
$result=$Qu->get_result();
$row=mysqli_fetch_assoc($result);
$count=$row["COUNT(*)"];

if($count==0){
     	$insert="INSERT INTO REQUESTER(REQ_FNAME,REQ_LNAME,REQ_LOCATION,REQ_PASSWORD,REQ_DOB,REQ_USERNAME) VALUES(?,?,?,?,?,?)";
	$Q=mysqli_prepare($link,$insert);
	$Q->bind_param("ssssss",$Fname,$Lname,$Location,$Password,$DOB,$Username);
	$Q->execute();
}
else{
	echo "false";
}

mysqli_close($link);


?>
