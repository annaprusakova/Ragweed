<?php 


$conn=mysqli_connect("localhost","root","091040tardis","users");

$key = $_POST['key'];

if(isset($_POST['tracker_date'], $_POST['itchy_nose'],$_POST['water_eyes'],$_POST['runny_nose'], $_POST['user_id'])){
$tracker_date  = $_POST['tracker_date'];
$itchy_nose    = $_POST['itchy_nose'];
$water_eyes    = $_POST['water_eyes'];
$runny_nose    = $_POST['runny_nose'];
$user_id = $_POST['user_id'];
$eye_redness = $_POST['eye_redness'];


if ( $key == "insert" ){
   
    $tracker_date_newformat = date('Y-m-d', strtotime($tracker_date));

    
    $query = "INSERT INTO tracker (tracker_date, itchy_nose,water_eyes,runny_nose,user_id,eye_redness)
    VALUES ('$tracker_date_newformat',  '$itchy_nose','$water_eyes','$runny_nose','$user_id','$eye_redness')";

        if ( mysqli_query($conn, $query) ){
                $result["value"] = "1";
                $result["message"] = "Success";
    
                echo json_encode($result);
                mysqli_close($conn);

        } 
        else {
            $response["value"] = "0";
            $response["message"] = "Error! ".mysqli_error($conn);
            echo json_encode($response);

            mysqli_close($conn);
        }
}
}

?>