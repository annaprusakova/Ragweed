<?php 

$conn=mysqli_connect("localhost","root","091040tardis","users");

if (isset($_GET['key'])) {
        $key = $_GET["key"];
        $query = "SELECT u.name, u.user_photo FROM location as l, users_table as u WHERE u.id = l.user_id_loc and l.location_id= $key";
        $result = mysqli_query($conn, $query);
        $response = array();
 

        while( $row = mysqli_fetch_assoc($result) ){

    array_push($response, 
    array(
        'name'      =>$row['name'], 
        'user_photo'     =>"http:/" . $row['user_photo'])
    );
}
        
echo json_encode($response); 
mysqli_close($conn); 
    
}
        




?>