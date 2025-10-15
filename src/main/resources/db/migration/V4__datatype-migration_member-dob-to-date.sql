begin;
alter table member
    add column dob_tmp date;

update member
set dob_tmp = member.date_of_birth;

alter table member drop column date_of_birth;
alter table member rename column dob_tmp to date_of_birth;

commit;