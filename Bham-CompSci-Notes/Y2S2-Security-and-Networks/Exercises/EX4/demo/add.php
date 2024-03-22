<!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML//EN">
<html> <head>
<title>Adding numbers</title>
</head>

<body>
<h1>Adding numbers</h1>
<?php
  $noOne = $_GET["noOne"];
  $noTwo = $_GET["noTwo"];
  echo "Number 1 + Number 2 equals ".(intval($noOne)+intval($noTwo));
?>

</body>
</html>
