CREATE PROCEDURE `add_rental_history`(
	prams_rent_id int,
    prams_equipment_id int,
    prams_employee_id int,
    prams_start_date Date,
    prams_end_date Date,
    prams_status varchar(10)
)
BEGIN
 INSERT INTO rental_history (rent_id, equipment_id, employee_id, start_date, end_date, status)
 VALUES(prams_rent_id, prams_equipment_id, prams_employee_id, prams_start_date, prams_end_date, prams_status);
END