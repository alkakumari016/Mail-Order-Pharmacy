create table drugs(id int primary key,
		   name varchar(20) not null,
           manufacturer varchar(20) not null,
		   manufactured_date date,
		   expiry_date date,
		   quantity int);

create table drug_location(id int primary key,location varchar(20),
			   quantity int,
			   drug_id int,
			   foreign key (drug_id) references drugs(id));
insert into drugs(id,name,manufacturer,manufactured_date,expiry_date,quantity) 
				values(1,'Paracetamol','WynBurg','2020-01-01','2025-01-01',10);
				
insert into drugs(id,name,manufacturer,manufactured_date,expiry_date,quantity) 
				values(2,'Dan','Unison','2020-01-01','2025-01-01',10);

insert into drug_location(id,location,quantity,drug_id)
				values(1,'HYD',5,1);
insert into drug_location(id,location,quantity,drug_id)
				values(2,'GUJ',5,2);