-- Fix combination `Bomb Material` and `Smoke Bomb`
UPDATE item_combination SET item_a_id = 167 WHERE item_created_id = 63;
UPDATE item_combination SET item_b_id = 165 WHERE item_created_id = 70;
