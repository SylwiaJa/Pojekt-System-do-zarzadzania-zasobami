-- Lider może przeglądać wszystkie zlecenia dostępne, wykonywane, zakończone wraz z informacją kto jest przypisany do danego 
-- zlecenia, jakie zasoby są wykorzystywane i jaki jest czas wykonywania
SELECT t.taskID, t.name, t.priority, t.description, p.name 'product', t.quantity, t.norm, 
		ts.stepName 'status' , ts.startStep, TIMESTAMPDIFF(HOUR, ts.startStep, CURRENT_TIMESTAMP) 'hours',
        concat(e.name,' ',e.lastName) 'employee',
        GROUP_CONCAT(DISTINCT eq.name) 'equipment' , GROUP_CONCAT(DISTINCT c.name) 'component'
FROM task t join product p on p.productID=t.productID 
			join taskstatus ts on ts.taskID=t.taskID 
            join employee e on ts.employeeID=e.employeeID
            join taskequipment te  on te.taskID=t.taskID
            join equipment eq on eq.equipmentID=te.equipmentID
            join taskcomponent tc  on tc.taskID=t.taskID
            join component c on c.componentID=tc.componentID                    
where ts.endStep ='0000-00-00 00:00:00'
GROUP BY t.taskID;

-- Lider może przeglądać listę pracowników wraz z informacją o ich aktualnych zadaniach
select concat(e.name,' ',e.lastName) 'employee', z.name 'zone', 
		t.name 'task', t.description,
        ts.stepName 'status', ts.startStep, TIMESTAMPDIFF(HOUR, ts.startStep, CURRENT_TIMESTAMP) 'hours'
from employee e left join zone z on e.zoneID=z.zoneID
				left join taskstatus ts on ts.employeeID=e.employeeID
                left join task t on t.taskID=ts.taskID
where ts.endStep ='0000-00-00 00:00:00' or ts.endStep is null;


-- Lider może przeglądać listy wszystkich dostępnych maszyn i sprzętów wraz z informacją o ich aktualnym wykorzystaniu

DELIMITER //
CREATE FUNCTION EquipmentUse(n INT)
  RETURNS VARCHAR(20)
  BEGIN
    DECLARE s VARCHAR(20);
    IF n in (select distinct e.equipmentID
				from equipment e left join taskequipment te on e.equipmentID=te.equipmentID
				where taskID in(select taskID from taskStatus where stepName='in progress')) 
    THEN SET s = 'in use';
    ELSE SET s = 'free';
    END IF;
    RETURN s;
END //
DELIMITER ;

select distinct eq.equipmentID, eq.name, eq.status, z.name 'zone', EquipmentUse(eq.equipmentID)	'availability'		
from equipment eq left join zone z on eq.zoneID=z.zoneID
				left join taskequipment te on te.equipmentID=eq.equipmentID;