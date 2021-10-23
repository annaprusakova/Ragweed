<?php
 
try{ 
$host="mysql:host=localhost;dbname=ragweed";
$user_name="root";
$user_password="";
$dbh=new PDO($host,$user_name,$user_password);
}
 
catch(Exception $e){
exit("Помилка з'єднання з сервером: ".$e->getMessage());
}
 
 ?>
  