INSERT INTO users (name, email, age, weight, height, status)
VALUES
    ('Test1', 'Test1@example.com', 28, 75.50, 180.00, 'WEIGHT_GAIN'),
    ('Test2', 'Test2@example.com', 32, 58.20, 165.00, 'MAINTAINING'),
    ('Test3', 'Test3@example.com', 41, 92.00, 190.00, 'WEIGHT_LOSS'),
    ('Test4', 'Test4@example.com', 25, 63.00, 170.00, 'RECOMPOSITION'),
    ('Test5', 'Test5@example.com', 38, 85.00, 185.00, 'MAINTAINING');


INSERT INTO user_norms (user_id, calorie_norm, protein_norm, fat_norm, carbs_norm)
VALUES
    (1, 2991.50, 186.00, 66.00, 412.00),
    (2, 1920.60, 105.00, 56.00, 249.00),
    (3, 2736.00, 239.00, 76.00, 273.00),
    (4, 2079.00, 156.00, 58.00, 240.00),
    (5, 2805.00, 154.00, 85.00, 363.00);


-- INSERT INTO dishes (name, calories, protein, fat, carbs)
-- VALUES
--     ('Гречка отварная'),
--     ('Картофель отварной'),
--     ('Картофель жареный'),
--     ('Макароны отварные'),
--     ('Рис отварной'),
--     ('Курицы(варёная)'),
--     ('Курица(жареная)'),
--     ('Курица(запечёная)'),
--     ('Говядина отварная'),
--     (''),
--     ('Котлеты домашние'),
--     ('Сосиски охотничьи'),
--     ('Наггетсы'),
--     (),
--     (),
--     (),
--     (),
--     (),
--     (),
--     (),
--     (),
--     (),
--     (),
--     (),
--     (),
--     (),
--     ();
--
--
-- INSERT INTO food_stats (user_id, dish_id, status, grams, total_calories,
--     total_protein, total_fat, total_carbs, created_at)
-- VALUES
--     (1, 9, 'BREAKFAST', 400, ),
--     (1, ),
--     (1, ),
--     (1, ),
--     (1, ),