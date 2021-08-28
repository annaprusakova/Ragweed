<?php 

$conn=mysqli_connect("localhost","root","091040tardis","users");

$type = $_GET['item_type'];

if (isset($_GET['key'])) {
    $key = $_GET["key"];
    if ($type == 'medicines') {
        $query = "SELECT * FROM medicines WHERE med_name LIKE '%$key%'";
        $result = mysqli_query($conn, $query);
        $data = array();
        while( $row = mysqli_fetch_assoc($result) ){
            $data[]=$row;
        }
        echo json_encode( $data);   
    }
} else {
    if ($type == 'medicines') {
        $query = "SELECT * FROM medicines";
        $result = mysqli_query($conn, $query);
        $data = array();
        while( $row = mysqli_fetch_assoc($result) ){
            $data[]=$row;
        }
        echo json_encode($data);   
    }
}


?>