<?php
if($_SERVER['REQUEST_METHOD']=='POST'){

include 'config.php';

 $con = mysqli_connect($HostName,$HostUser,$HostPass,$DatabaseName);
 date_default_timezone_set('Asia/Kolkata');

    $email = $_POST['email'];
    $name = $_POST['name'];
    $phone= $_POST['phone'];
    $password = $_POST['password'];
    $datetime = date("d-m-Y h:i:sa");
    $oauthid= md5($email);
    
   $CheckSQL = "SELECT * FROM `people_view_user_register` WHERE email='$email'";
 
 $check = mysqli_fetch_array(mysqli_query($con,$CheckSQL));
 
 if(isset($check)){

 echo 'Email Already Exist';

 }
else{ 
$Sql_Query = "INSERT INTO `register`(`email`, `name`, `phone`, `password`, `create_date`, `oauth_uid`) VALUES ('$email','$name','$phone', '$password', '$datetime', '$oauthid')";

 if(mysqli_query($con,$Sql_Query))
{
 echo 'Registration Successfully';
}
else
{
 echo 'Something went wrong';
 }
 }
}
 mysqli_close($con);

?>