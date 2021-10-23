<?php 

$conn=mysqli_connect("localhost","root","091040tardi","ragweed");

$type = $_GET['item_type'];

if (isset($_GET['key'])) {
    $key = $_GET["key"];
    if ($type == 'active') {
        $query = "SELECT * FROM medicines WHERE active_substance LIKE '%$key%'";
        $result = mysqli_query($conn, $query);
        $data = array();
        while( $row = mysqli_fetch_assoc($result) ){
            $data[]=$row;
        }
        echo json_encode( $data);   
    }

}


?>