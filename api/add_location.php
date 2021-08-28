<?php 
$conn=mysqli_connect("localhost","root","091040tardis","users");


$key = $_POST['key'];

$loc_name      = $_POST['loc_name'];
$loc_date    = $_POST['loc_date'];
$loc_point     = $_POST['loc_point'];
$loc_description      = $_POST['loc_description'];
$user_id_loc    = $_POST['user_id_loc'];
$loc_latlng = $_POST['loc_latlng'];
$loc_photo = $_POST['loc_photo'];



if ( $key == "insert" ){

    $date_newformat = date('Y-m-d', strtotime($loc_date));

    $query = "INSERT INTO location (loc_name,loc_date,loc_point,loc_description,user_id_loc,loc_latlng)
    VALUES ('$loc_name', '$date_newformat', '$loc_point', '$loc_description', $user_id_loc, '$loc_latlng') ";

        if ( mysqli_query($conn, $query) ){
            if ($loc_photo == null) {

                $finalPath = "/Uploads/loc_photo.png"; 
                $result["value"] = "1";
                $result["message"] = "Success";
    
                echo json_encode($result);
                mysqli_close($conn);

            } 
          
            else {

                $id = mysqli_insert_id($conn);
                $path = "Uploads/$id.jpg";
                $finalPath = "/192.168.1.6/api/".$path;

                $insert_picture = "UPDATE location SET loc_photo='$finalPath' WHERE location_id='$id' ";
            
                if (mysqli_query($conn, $insert_picture)) {
            
                    if ( file_put_contents( $path, base64_decode($loc_photo) ) ) {
                        
                        $result["value"] = "1";
                        $result["message"] = "Success!";
            
                        echo json_encode($result);
                        mysqli_close($conn);
            
                    } else {
                        
                        $response["value"] = "0";
                        $response["message"] = "Error! ".mysqli_error($conn);
                        echo json_encode($response);

                        mysqli_close($conn);
                    }

                }
            }
            
                

                
            

        } 
        else {
            $response["value"] = "0";
            $response["message"] = "Error! ".mysqli_error($conn);
            echo json_encode($response);

            mysqli_close($conn);
        }
}


?>