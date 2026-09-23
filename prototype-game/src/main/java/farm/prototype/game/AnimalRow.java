package farm.prototype.game;

/** PROTOTYPE — one row of real data pulled from the `farm` Postgres db. */
public record AnimalRow(
    int id,
    String name,
    String species,
    String birthDate,
    double weightKg,
    String fleeceColor,
    String penLabel
) {
}
