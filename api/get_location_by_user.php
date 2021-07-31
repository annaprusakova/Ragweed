<?php 

$conn=mysqli_connect("localhost","root","091040tardis","users");

if (isset($_GET['key'])) {
        $key = $_GET["key"];
        $query = "SELECT * FROM location WHERE user_id_loc = $key ORDER BY location_id DESC";
        $result = mysqli_query($conn, $query);
        $response = array();
 

        while( $row = mysqli_fetch_assoc($result) ){

    array_push($response, 
    array(
        'location_id'   =>$row['location_id'], 
        'loc_name'      =>$row['loc_name'], 
        'loc_date'      =>date('d M Y', strtotime($row['loc_date'])),
        'loc_photo'     =>"http:/" . $row['loc_photo'],
        'loc_point'     =>$row['loc_point'],
        'loc_description'=>$row['loc_description'],
        'user_id_loc'    =>$row['user_id_loc'])
    );
}
        
echo json_encode($response); 
mysqli_close($conn); 
    
}
        




?>