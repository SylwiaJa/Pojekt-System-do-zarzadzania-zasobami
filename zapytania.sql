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
				where taskID in(select taskID from taskStatus where stepName='in progress' and endStep ='0000-00-00 00:00:00')) 
    THEN SET s = 'in use';
    ELSE SET s = 'free';
    END IF;
    RETURN s;
END //
DELIMITER ;

select distinct eq.equipmentID, eq.name, eq.status, z.name 'zone', EquipmentUse(eq.equipmentID)	'availability'		
from equipment eq left join zone z on eq.zoneID=z.zoneID
				left join taskequipment te on te.equipmentID=eq.equipmentID;
				
				
-- Lider ma możliwość generowania raportów dotyczących czasu wykorzystania danego sprzętu i maszyn
select e.equipmentID, e.name, z.name 'zone', 
	sum(if(ts.endStep='0000-00-00 00:00:00', TIMESTAMPDIFF(HOUR, ts.startStep, CURRENT_TIMESTAMP), TIMESTAMPDIFF(HOUR, ts.startStep, ts.endStep))) 'hoursInUse'
from equipment e left join taskequipment te on e.equipmentID=te.equipmentID 
	left join task t on t.taskID=te.taskID
    left join taskstatus ts on ts.taskID=t.taskID
    left join zone z on z.zoneID=e.zoneID
where stepName='in progress'
group by e.equipmentID;


-- Lider ma możliwość generowania raportów dotyczących wydajności pracy z uwzględnieniem danego okresu czasu i pracownika
DELIMITER //
CREATE OR REPLACE PROCEDURE employeeEfficiency(startDate timestamp, endDate timestamp )
   BEGIN
		select e.employeeID, concat(e.name,' ',e.lastName) 'employee', sum(r.quantityOK)/sum(t.norm)'efficiency'
		from task t join taskstatus ts on t.taskID=ts.taskID
			join result r on t.resultID=r.resultID
			join employee e on ts.employeeID=e.employeeID
		where stepName='in progress' and ts.endStep<>'0000-00-00 00:00:00' and startStep>=startDate and startStep<=endDate
		group by e.employeeID;
   END//

DELIMITER ;
-- call employeeEfficiency('2024-01-02 10:17:36', '2024-01-07 10:17:36');

-- Lider ma możliwość generowania raportów dotyczących wydajności pracy z uwzględnieniem danego okresu czasu i obszaru
DELIMITER //
CREATE PROCEDURE zoneEfficiency(startDate timestamp, endDate timestamp )
   BEGIN
		select z.zoneID, z.name 'zone', sum(r.quantityOK)/sum(t.norm)'quantityOK/norm'
		from task t join taskstatus ts on t.taskID=ts.taskID
			join result r on t.resultID=r.resultID
			join employee e on ts.employeeID=e.employeeID
			join zone z on e.zoneID=z.zoneID
		where stepName='in progress' and ts.endStep<>'0000-00-00 00:00:00' and startStep>=startDate and startStep<=endDate
		group by z.zoneID;
   END//

DELIMITER ;
--call zoneEfficiency('2024-01-02 10:17:36', '2024-01-07 10:17:36');

-- Pracownik może przeglądać aktualne zlecenia dostępne dla niego, zgodne z jego uprawnieniami
DELIMITER //
CREATE PROCEDURE taskForEmployee(empID)
   BEGIN
		SELECT t.taskID, t.name, t.priority, t.description, p.name 'product', t.quantity, t.norm, 
			ts.stepName 'status' ,
    		GROUP_CONCAT(DISTINCT eq.name) 'equipment' , GROUP_CONCAT(DISTINCT c.name) 'component'
		FROM task t join product p on p.productID=t.productID 
			join taskstatus ts on ts.taskID=t.taskID 
    		join taskequipment te  on te.taskID=t.taskID
    		join equipment eq on eq.equipmentID=te.equipmentID
    		join taskcomponent tc  on tc.taskID=t.taskID
    		join component c on c.componentID=tc.componentID                    
		where ts.endStep ='0000-00-00 00:00:00'  and ts.stepName='available'
			and t.taskCategory in(select distinct taskcategoryid from taskcategorylicense  where licenseID in 
			(select licenseId from employeelicense where employeeId=empID))
		GROUP BY t.taskID;
   END//

DELIMITER ;
--call taskForEmployee(2);



























