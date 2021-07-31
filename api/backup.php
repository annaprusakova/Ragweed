<?php
   $dbhost = 'localhost';
   $dbuser = 'root';
   $dbpass = '091040tardis';
   
   $conn = mysql_connect($dbhost, $dbuser, $dbpass);
   
   if(! $conn ) {
      die('Could not connect: ' . mysql_error());
   }
	
   $table_name = "users";
   $backup_file  = "/tmp/users.sql";
   $sql = "SELECT * INTO OUTFILE '$backup_file' FROM $table_name";
   
   mysql_select_db('test_db');
   $retval = mysql_query( $sql, $conn );
   
   if(! $retval ) {
      die('Could not take data backup: ' . mysql_error());
   }
   
   echo "Backedup  data successfully\n";
   
   mysql_close($conn);
?>