CREATE OR REPLACE FUNCTION calculate_food_stats_totals()
RETURNS TRIGGER AS $$
DECLARE
    dish_record dishes%ROWTYPE;
BEGIN
    SELECT * INTO dish_record
    FROM dishes
    WHERE id = NEW.dish_id;

    IF NOT FOUND THEN
        RAISE EXCEPTION 'Dish with id % does not exist', NEW.dish_id;
    END IF;

    NEW.total_calories := ROUND(
        (dish_record.calories * NEW.grams) / 100,
        2
    );
    NEW.total_protein := ROUND(
        (dish_record.protein * NEW.grams) / 100,
        2
    );
    NEW.total_fat := ROUND(
        (dish_record.fat * NEW.grams) / 100,
        2
    );
    NEW.total_carbs := ROUND(
        (dish_record.carbs * NEW.grams) / 100,
        2
    );

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;



CREATE TRIGGER trg_calculate_food_stats
BEFORE INSERT ON food_stats
FOR EACH ROW
EXECUTE FUNCTION calculate_food_stats_totals();

CREATE TRIGGER trg_update_food_stats
BEFORE UPDATE ON food_stats
FOR EACH ROW
WHEN (OLD.grams IS DISTINCT FROM NEW.grams)
EXECUTE FUNCTION calculate_food_stats_totals();



