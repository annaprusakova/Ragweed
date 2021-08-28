<?php 

$conn=mysqli_connect("localhost","root","091040tardis","users");

if(isset($_GET['item_type'])){
$type = $_GET['item_type'];

    if ($type == 'min') {
        $query = "SELECT * FROM medicines ORDER BY cost";
        $result = mysqli_query($conn, $query);
        $data = array();
        while( $row = mysqli_fetch_assoc($result) ){
            $data[]=$row;
        }
        echo json_encode( $data);   
    }
else {
    if ($type == 'max') {
        $query = "SELECT * FROM medicines ORDER BY cost DESC";
        $result = mysqli_query($conn, $query);
        $data = array();
        while( $row = mysqli_fetch_assoc($result) ){
            $data[]=$row;
        }
        echo json_encode($data);   
    }

}
}

?>