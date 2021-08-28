<?php 

$conn=mysqli_connect("localhost","root","091040tardis","users");

if (isset($_GET['key'])) {
    $key = $_GET["key"];
    $query = "SELECT tracker_date, degree_tracker(itchy_nose, water_eyes,runny_nose,eye_redness) as degree FROM tracker WHERE user_id = $key ORDER BY tracker_date";
    $result = mysqli_query($conn, $query);
    $response = array();

  
    while( $row = mysqli_fetch_assoc($result) ){
      
        array_push($response, 
        array(
            'tracker_date'      => date("d.m.Y",strtotime($row['tracker_date'])), 
            'degree'     =>$row['degree'])
        );
    }
            
    echo json_encode($response); 
    mysqli_close($conn); 

}


?>