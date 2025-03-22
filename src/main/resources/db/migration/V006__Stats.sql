INSERT INTO food_stats (user_id, dish_id, status, grams, total_calories,
    total_protein, total_fat, total_carbs, created_at)
VALUES
    -- Test1 (user_id=1) День 1
    (1, 22, 'BREAKFAST', 120, 444, 12.6, 2.52, 93.6, '2025-03-22 09:00:00.000000+03'),
    (1, 23, 'BREAKFAST', 200, 100, 6.6, 4.0, 9.4, '2025-03-22 09:00:00.000000+03'),
    (1, 25, 'BREAKFAST', 150, 133.5, 1.65, 0.53, 34.28, '2025-03-22 09:00:00.000000+03'),
    (1, 26, 'SNACK', 50, 159, 0.9, 0.1, 40.65, '2025-03-22 11:00:00.000000+03'),
    (1, 24, 'SNACK', 30, 105, 1.35, 0.9, 22.8, '2025-03-22 11:00:00.000000+03'),
    (1, 1, 'LUNCH', 200, 240, 6.4, 8.3, 38.1, '2025-03-22 15:00:00.000000+03'),
    (1, 7, 'LUNCH', 180, 480.6, 45.0, 11.88, 0.0, '2025-03-22 15:00:00.000000+03'),
    (1, 29, 'LUNCH', 100, 15, 0.65, 0.1, 3.65, '2025-03-22 15:00:00.000000+03'),
    (1, 21, 'SNACK', 100, 259, 26.5, 17.0, 0.0, '2025-03-22 18:00:00.000000+03'),
    (1, 20, 'SNACK', 50, 133, 3.83, 1.65, 25.3, '2025-03-22 18:00:00.000000+03'),
    (1, 17, 'DINNER', 250, 550, 30.0, 17.5, 62.5, '2025-03-22 20:00:00.000000+03'),
    (1, 30, 'DINNER', 100, 24, 1.1, 0.2, 3.7, '2025-03-22 20:00:00.000000+03'),
    (1, 27, 'SNACK', 40, 214, 3.06, 11.86, 23.76, '2025-03-22 23:00:00.000000+03'),

    -- Test1 День 2
    (1, 18, 'BREAKFAST', 150, 220.5, 18.9, 14.93, 1.13, '2025-03-23 09:30:00.000000+03'),
    (1, 20, 'BREAKFAST', 100, 266, 7.65, 3.3, 50.6, '2025-03-23 09:30:00.000000+03'),
    (1, 25, 'SNACK', 200, 178, 2.2, 0.7, 45.7, '2025-03-23 12:00:00.000000+03'),
    (1, 23, 'SNACK', 300, 150, 9.9, 6.0, 14.1, '2025-03-23 12:00:00.000000+03'),
    (1, 5, 'LUNCH', 200, 246, 5.8, 0.7, 52.1, '2025-03-23 14:00:00.000000+03'),
    (1, 10, 'LUNCH', 200, 540, 37.5, 40.2, 5.7, '2025-03-23 14:00:00.000000+03'),
    (1, 29, 'LUNCH', 100, 15, 0.65, 0.1, 3.65, '2025-03-23 14:00:00.000000+03'),
    (1, 14, 'SNACK', 150, 445.5, 23.4, 28.2, 24.53, '2025-03-23 17:30:00.000000+03'),
    (1, 3, 'DINNER', 250, 372.5, 5.75, 17.5, 50.13, '2025-03-23 19:30:00.000000+03'),
    (1, 16, 'DINNER', 180, 358.2, 46.44, 5.4, 28.26, '2025-03-23 19:30:00.000000+03'),
    (1, 21, 'SNACK', 150, 388.5, 39.75, 25.5, 0.0, '2025-03-23 22:00:00.000000+03'),
    (1, 26, 'SNACK', 30, 95.4, 1.08, 0.06, 24.39, '2025-03-23 22:00:00.000000+03'),

    -- Test2 (user_id=2) День 1
    (2, 18, 'BREAKFAST', 100, 147, 12.6, 9.95, 0.75, '2025-03-22 07:00:00.000000+03'),
    (2, 20, 'BREAKFAST', 50, 133, 3.83, 1.65, 25.3, '2025-03-22 07:00:00.000000+03'),
    (2, 30, 'BREAKFAST', 100, 24, 1.1, 0.2, 3.7, '2025-03-22 07:00:00.000000+03'),
    (2, 25, 'SNACK', 100, 89, 1.1, 0.35, 22.85, '2025-03-22 11:00:00.000000+03'),
    (2, 2, 'LUNCH', 150, 129, 2.78, 0.15, 30.0, '2025-03-22 14:00:00.000000+03'),
    (2, 6, 'LUNCH', 120, 205.2, 30.3, 8.64, 0.0, '2025-03-22 14:00:00.000000+03'),
    (2, 29, 'LUNCH', 100, 15, 0.65, 0.1, 3.65, '2025-03-22 14:00:00.000000+03'),
    (2, 19, 'SNACK', 70, 53.2, 7.0, 2.8, 0.0, '2025-03-22 16:00:00.000000+03'),
    (2, 4, 'DINNER', 150, 154.5, 5.63, 0.9, 30.08, '2025-03-22 18:00:00.000000+03'),
    (2, 11, 'DINNER', 100, 129, 12.0, 9.0, 0.0, '2025-03-22 18:00:00.000000+03'),
    (2, 26, 'SNACK', 20, 63.6, 0.72, 0.04, 16.26, '2025-03-22 21:00:00.000000+03'),

    -- Test2 День 2
    (2, 22, 'BREAKFAST', 50, 185, 5.25, 0.88, 39.0, '2025-03-23 07:30:00.000000+03'),
    (2, 23, 'BREAKFAST', 150, 75, 4.95, 3.0, 7.05, '2025-03-23 07:30:00.000000+03'),
    (2, 21, 'SNACK', 50, 129.5, 13.25, 8.5, 0.0, '2025-03-23 12:00:00.000000+03'),
    (2, 18, 'SNACK', 50, 73.5, 6.3, 4.98, 0.38, '2025-03-23 12:00:00.000000+03'),
    (2, 20, 'SNACK', 30, 79.8, 2.3, 0.99, 15.18, '2025-03-23 12:00:00.000000+03'),
    (2, 1, 'LUNCH', 150, 180, 4.8, 6.23, 28.58, '2025-03-23 16:00:00.000000+03'),
    (2, 16, 'LUNCH', 130, 258.7, 33.54, 3.9, 20.41, '2025-03-23 16:00:00.000000+03'),
    (2, 30, 'LUNCH', 100, 24, 1.1, 0.2, 3.7, '2025-03-23 16:00:00.000000+03'),
    (2, 5, 'DINNER', 100, 123, 2.9, 0.35, 26.05, '2025-03-23 20:00:00.000000+03'),
    (2, 8, 'DINNER', 150, 282, 43.05, 11.03, 0.0, '2025-03-23 20:00:00.000000+03'),
    (2, 26, 'SNACK', 25, 79.5, 0.9, 0.05, 20.33, '2025-03-23 21:00:00.000000+03'),

    -- Test3 (user_id=3) День 1
    (3, 21, 'BREAKFAST', 200, 518, 53.0, 34.0, 0.0, '2025-03-22 11:00:00.000000+03'),
    (3, 25, 'BREAKFAST', 100, 89, 1.1, 0.35, 22.85, '2025-03-22 11:00:00.000000+03'),
    (3, 19, 'SNACK', 100, 76, 10.0, 4.0, 0.0, '2025-03-22 13:00:00.000000+03'),
    (3, 1, 'LUNCH', 150, 180, 4.8, 6.23, 28.58, '2025-03-22 15:30:00.000000+03'),
    (3, 9, 'LUNCH', 200, 512, 51.5, 32.9, 2.0, '2025-03-22 15:30:00.000000+03'),
    (3, 29, 'LUNCH', 150, 22.5, 0.98, 0.15, 5.48, '2025-03-22 15:30:00.000000+03'),
    (3, 18, 'SNACK', 100, 147, 12.6, 9.95, 0.75, '2025-03-22 18:00:00.000000+03'),
    (3, 8, 'DINNER', 250, 470, 71.75, 18.38, 0.0, '2025-03-22 21:00:00.000000+03'),
    (3, 30, 'DINNER', 150, 36, 1.65, 0.3, 5.55, '2025-03-22 21:00:00.000000+03'),
    (3, 23, 'SNACK', 200, 100, 6.6, 4.0, 9.4, '2025-03-22 23:30:00.000000+03'),

    -- Test3 День 2
    (3, 18, 'BREAKFAST', 150, 220.5, 18.9, 14.93, 1.13, '2025-03-23 10:45:00.000000+03'),
    (3, 21, 'BREAKFAST', 50, 129.5, 13.25, 8.5, 0.0, '2025-03-23 10:45:00.000000+03'),
    (3, 14, 'SNACK', 80, 237.6, 12.48, 15.04, 13.08, '2025-03-23 12:00:00.000000+03'),
    (3, 2, 'LUNCH', 200, 172, 3.7, 0.2, 40.0, '2025-03-23 15:00:00.000000+03'),
    (3, 10, 'LUNCH', 150, 405, 28.13, 30.15, 4.28, '2025-03-23 15:00:00.000000+03'),
    (3, 16, 'SNACK', 150, 298.5, 38.7, 4.5, 23.55, '2025-03-23 18:00:00.000000+03'),
    (3, 29, 'DINNER', 100, 15, 0.65, 0.1, 3.65, '2025-03-23 22:00:00.000000+03'),
    (3, 23, 'SNACK', 200, 100, 6.6, 4.0, 9.4, '2025-03-23 23:00:00.000000+03'),

    -- Test4 (user_id=4) День 1
    (4, 22, 'BREAKFAST', 80, 296, 8.4, 1.68, 62.4, '2025-03-22 08:00:00.000000+03'),
    (4, 23, 'BREAKFAST', 200, 100, 6.6, 4.0, 9.4, '2025-03-22 08:00:00.000000+03'),
    (4, 18, 'SNACK', 100, 147, 12.6, 9.95, 0.75, '2025-03-22 12:00:00.000000+03'),
    (4, 4, 'LUNCH', 150, 154.5, 5.63, 0.9, 30.08, '2025-03-22 13:00:00.000000+03'),
    (4, 9, 'LUNCH', 150, 384, 38.63, 24.68, 1.5, '2025-03-22 13:00:00.000000+03'),
    (4, 29, 'LUNCH', 100, 15, 0.65, 0.1, 3.65, '2025-03-22 13:00:00.000000+03'),
    (4, 21, 'SNACK', 70, 181.3, 18.55, 11.9, 0.0, '2025-03-22 18:00:00.000000+03'),
    (4, 6, 'DINNER', 200, 342, 50.5, 14.4, 0.0, '2025-03-22 19:00:00.000000+03'),
    (4, 5, 'DINNER', 100, 123, 2.9, 0.35, 26.05, '2025-03-22 19:00:00.000000+03'),
    (4, 26, 'SNACK', 25, 79.5, 0.9, 0.05, 20.33, '2025-03-22 22:00:00.000000+03'),

    -- Test4 День 2
    (4, 21, 'BREAKFAST', 150, 388.5, 39.75, 25.5, 0.0, '2025-03-23 08:00:00.000000+03'),
    (4, 25, 'BREAKFAST', 100, 89, 1.1, 0.35, 22.85, '2025-03-23 08:00:00.000000+03'),
    (4, 19, 'SNACK', 50, 38, 5.0, 2.0, 0.0, '2025-03-23 09:00:00.000000+03'),
    (4, 20, 'SNACK', 30, 79.8, 2.3, 0.99, 15.18, '2025-03-23 09:00:00.000000+03'),
    (4, 3, 'LUNCH', 150, 223.5, 3.45, 10.5, 30.08, '2025-03-23 15:00:00.000000+03'),
    (4, 12, 'LUNCH', 120, 240, 32.88, 7.14, 8.88, '2025-03-23 15:00:00.000000+03'),
    (4, 14, 'SNACK', 60, 178.2, 9.36, 11.28, 9.81, '2025-03-23 17:30:00.000000+03'),
    (4, 16, 'DINNER', 150, 298.5, 38.7, 4.5, 23.55, '2025-03-23 20:30:00.000000+03'),
    (4, 30, 'DINNER', 100, 24, 1.1, 0.2, 3.7, '2025-03-23 20:30:00.000000+03'),
    (4, 23, 'SNACK', 150, 75, 4.95, 3.0, 7.05, '2025-03-23 22:00:00.000000+03'),

    -- Test5 (user_id=5) День 1
    (5, 18, 'BREAKFAST', 150, 220.5, 18.9, 14.93, 1.13, '2025-03-22 10:00:00.000000+03'),
    (5, 20, 'BREAKFAST', 100, 266, 7.65, 3.3, 50.6, '2025-03-22 10:00:00.000000+03'),
    (5, 21, 'BREAKFAST', 70, 181.3, 18.55, 11.9, 0.0, '2025-03-22 10:00:00.000000+03'),
    (5, 24, 'SNACK', 50, 175, 2.25, 1.5, 38.0, '2025-03-22 12:30:00.000000+03'),
    (5, 3, 'LUNCH', 200, 298, 4.6, 14.0, 40.1, '2025-03-22 14:30:00.000000+03'),
    (5, 10, 'LUNCH', 200, 540, 37.5, 40.2, 5.7, '2025-03-22 14:30:00.000000+03'),
    (5, 14, 'SNACK', 120, 356.4, 18.72, 22.56, 19.62, '2025-03-22 18:00:00.000000+03'),
    (5, 17, 'DINNER', 300, 660, 36.0, 21.0, 75.0, '2025-03-22 21:00:00.000000+03'),
    (5, 29, 'DINNER', 100, 15, 0.65, 0.1, 3.65, '2025-03-22 21:00:00.000000+03'),
    (5, 27, 'SNACK', 30, 160.5, 2.3, 8.9, 17.82, '2025-03-22 22:00:00.000000+03'),

    -- Test5 День 2
    (5, 22, 'BREAKFAST', 120, 444, 12.6, 2.52, 93.6, '2025-03-23 09:40:00.000000+03'),
    (5, 23, 'BREAKFAST', 250, 125, 8.25, 5.0, 11.75, '2025-03-23 09:40:00.000000+03'),
    (5, 25, 'BREAKFAST', 150, 133.5, 1.65, 0.53, 34.28, '2025-03-23 09:40:00.000000+03'),
    (5, 13, 'SNACK', 100, 262, 10.0, 22.0, 0.0, '2025-03-23 12:00:00.000000+03'),
    (5, 5, 'LUNCH', 200, 246, 5.8, 0.7, 52.1, '2025-03-23 14:00:00.000000+03'),
    (5, 8, 'LUNCH', 250, 470, 71.75, 18.38, 0.0, '2025-03-23 14:00:00.000000+03'),
    (5, 1, 'DINNER', 150, 180, 4.8, 6.23, 28.58, '2025-03-23 19:00:00.000000+03'),
    (5, 15, 'DINNER', 180, 324, 47.88, 6.21, 16.74, '2025-03-23 19:00:00.000000+03'),
    (5, 21, 'SNACK', 100, 259, 26.5, 17.0, 0.0, '2025-03-23 21:00:00.000000+03'),
    (5, 26, 'SNACK', 30, 95.4, 1.08, 0.06, 24.39, '2025-03-23 21:00:00.000000+03');