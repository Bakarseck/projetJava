<?php
// Vérifie si les données du formulaire sont présentes
if (isset($_POST['a']) && isset($_POST['b']) && isset($_POST['c'])) {
    $a = $_POST['a'];
    $b = $_POST['b'];
    $c = $_POST['c'];

    // Calcul du discriminant
    $delta = pow($b, 2) - (4 * $a * $c);

    // Résolution de l'équation en fonction du discriminant
    if ($delta > 0) {
        $x1 = (-$b - sqrt($delta)) / (2 * $a);
        $x2 = (-$b + sqrt($delta)) / (2 * $a);
        echo "L'équation a deux solutions réelles : x1 = $x1 et x2 = $x2";
    } elseif ($delta == 0) {
        $x = -$b / (2 * $a);
        echo "L'équation a une solution réelle double : x = $x";
    } else {
        echo "L'équation n'a pas de solutions réelles.";
    }
} else {
    echo "Veuillez saisir tous les coefficients.";
}
?>
