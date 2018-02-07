<?php
if($_SERVER['REQUEST_METHOD']=='POST'){

include 'config.php';

 $con = mysqli_connect($HostName,$HostUser,$HostPass,$DatabaseName);
 date_default_timezone_set('Asia/Kolkata');

    $email = $_POST['email'];
    $name = $_POST['name'];
    $time= $_POST['time'];
    $type = $_POST['type'];
    $oauthid= $_POST['id'];
    
  
$Sql_Query = "INSERT INTO `social_login`(`email`, `name`, `time`, `oauth_provider`, `oauth_id`) VALUES ('$email','$name','$time', '$type', '$oauthid')";

 if(mysqli_query($con,$Sql_Query))
{
 echo 'History updated';
}
else
{
 echo 'Something went wrong';
 }
 }

 mysqli_close($con);

?>