<?php 

$conn=mysqli_connect("localhost","root","","ragweed");


        $query = "SELECT * FROM location";
        $result = mysqli_query($conn, $query);
        $response = array();
 

        while($row = mysqli_fetch_assoc($result) ){

    array_push($response, 
    array(
        'location_id'   =>$row['location_id'], 
        'loc_name'      =>$row['loc_name'], 
        'loc_date'      =>date($row['loc_date']),
        'loc_photo'     =>"http:/" . $row['loc_photo'],
        'loc_point'     =>$row['loc_point'],
        'loc_description'=>$row['loc_description'],
        'user_id_loc'    =>$row['user_id_loc'],
        'loc_latlng'      =>$row['loc_latlng'])
    );
}
        
echo json_encode($response); 
mysqli_close($conn); 
    

        




?>