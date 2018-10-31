<?
switch($_SERVER['REQUEST_METHOD'])
{
    case 'GET':
        echo
            '{"id": '.rand(1, 100).',"name":"example_'.rand(1, 100).'"}';
        break;
    case 'POST':
        $data = file_get_contents('php://input');
        $json = json_decode($data);
        $name = $json->{'name'};

        if(isset($name) && $name!=''){
            http_response_code(200);
            echo
                '{"id": '.rand(1, 100).',"name":"'.$name.'"}';
        }else
            // bad request
            http_response_code(400);
        break;
}

?>