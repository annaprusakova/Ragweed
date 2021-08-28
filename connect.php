<?php
 
try{ 
$host="mysql:host=localhost;dbname=users";
$user_name="root";
$user_password="091040tardis";
$dbh=new PDO($host,$user_name,$user_password);
}
 
catch(Exception $e){
exit("Помилка з'єднання з сервером: ".$e->getMessage());
}
 
 ?>
  