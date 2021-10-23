<?php 


$conn=mysqli_connect("localhost","root","","ragweed");

$key = $_POST['key'];

if(isset($_POST['tracker_date'], $_POST['itchy_eyes'],$_POST['sore_throat'],$_POST['water_eyes'], 
$_POST['runny_nose'], $_POST['cough'], $_POST['pressure_sinuses'], $_POST['blue_under_eyes'], $_POST['bad_sleep'], $_POST['allergy_eczema'], $_POST['user_id'])){

    $tracker_date  = $_POST['tracker_date'];
    $itchy_eyes    = $_POST['itchy_eyes'];
    $sore_throat    = $_POST['sore_throat'];
    $water_eyes    = $_POST['water_eyes'];
    $runny_nose    = $_POST['runny_nose'];
    $cough   = $_POST['cough'];
    $pressure_sinuses   = $_POST['pressure_sinuses'];
    $blue_under_eyes   = $_POST['blue_under_eyes'];
    $bad_sleep   = $_POST['bad_sleep'];
    $allergy_eczema   = $_POST['allergy_eczema'];
    $user_id = $_POST['user_id'];
    $eye_redness = $_POST['eye_redness'];



if ( $key == "insert" ){
   
    $tracker_date_newformat = date('Y-m-d', strtotime($tracker_date));

    
    $query = "INSERT INTO tracker (tracker_date, itchy_eyes,sore_throat,water_eyes,runny_nose,cough,pressure_sinuses,blue_under_eyes,bad_sleep,allergy_eczema,user_id,eye_redness)
    VALUES ('$tracker_date_newformat',  '$itchy_eyes','$sore_throat','$water_eyes', '$runny_nose', '$cough', '$pressure_sinuses', '$blue_under_eyes', '$bad_sleep', '$allergy_eczema', '$user_id','$eye_redness')";

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