
-- update cos_assignment_group_item title when update assignment, discussion, quiz's title
drop trigger if exists assignment_title_update_assg_group_item;
delimiter $$
create trigger assignment_title_update_assg_group_item after update
    on cos_assignment for each row
begin
    update cos_assignment_group_item set title = NEW.title, score = NEW.score, status = NEW.status
        where origin_type = 1 and origin_id = NEW.id;
end $$

drop trigger if exists discussion_title_update_assg_group_item;
delimiter $$
create trigger discussion_title_update_assg_group_item after update
    on cos_discussion for each row
begin
    update cos_assignment_group_item set title = NEW.title, score = NEW.score, status = NEW.status
        where origin_type = 2 and origin_id = NEW.id;
end $$

drop trigger if exists quiz_title_update_assg_group_item;
delimiter $$
create trigger quiz_title_update_assg_group_item after update
    on cos_quiz for each row
begin
    update cos_assignment_group_item set title = NEW.title, score = NEW.score, status = NEW.status
        where origin_type = 3 and origin_id = NEW.id;
end $$
-- end


-- update cos_module_item's title when update assignment, discussion, quiz, file and page's title
drop trigger if exists assignment_title_update_module_item;
delimiter $$
create trigger assignment_title_update_module_item after update
    on cos_assignment for each row
begin
    update cos_module_item set title = NEW.title, score = NEW.score, status = NEW.status
        where origin_type = 1 and origin_id = NEW.id;
end $$

drop trigger if exists discussion_title_update_module_item;
delimiter $$
create trigger discussion_title_update_module_item after update
    on cos_discussion for each row
begin
    update cos_module_item set title = NEW.title, score = NEW.score, status = NEW.status
        where origin_type = 2 and origin_id = NEW.id;
end $$

drop trigger if exists quiz_title_update_module_item;
delimiter $$
create trigger quiz_title_update_module_item after update
    on cos_quiz for each row
begin
    update cos_module_item set title = NEW.title, score = NEW.score, status = NEW.status
        where origin_type = 3 and origin_id = NEW.id;
end $$


drop trigger if exists file_title_update_module_item;
delimiter $$
create trigger file_title_update_module_item after update
    on sys_user_file for each row
begin
    update cos_module_item set title = NEW.file_name, status = NEW.status
        where origin_type = 4 and origin_id = NEW.id;
end $$


drop trigger if exists page_title_update_module_item;
delimiter $$
create trigger page_title_update_module_item after update
    on cos_page for each row
begin
    update cos_module_item set title = NEW.title, status = NEW.status
        where origin_type = 5 and origin_id = NEW.id;
end $$

DROP TRIGGER
IF EXISTS text_header_title_update_module_item;
delimiter $$


CREATE TRIGGER text_header_title_update_module_item AFTER UPDATE ON cos_module_text_header FOR EACH ROW
BEGIN
UPDATE cos_module_item
SET title = NEW.text,
    STATUS = NEW. STATUS
WHERE
        origin_type = 13
  AND origin_id = NEW.id ; END$$

drop trigger if exists external_url_title_update_module_item;
delimiter $$


CREATE TRIGGER external_url_title_update_module_item AFTER UPDATE ON cos_module_external_url FOR EACH ROW
BEGIN
UPDATE cos_module_item
SET title = NEW.page_name,
    STATUS = NEW. STATUS
WHERE
        origin_type = 14
  AND origin_id = NEW.id ; END$$
-- end


-- update cos_grade's title when update cos_assignment_group_item's title
-- drop trigger if exists assg_group_item_title_update_grade;
-- delimiter $$
-- create trigger assg_group_item_title_update_grade after update
--     on cos_assignment_group_item for each row
-- begin
--     update cos_grade set title = NEW.title
--         where assignment_group_item_id = NEW.id;
-- end $$
-- --

-- drop trigger if exists assignment_title_update_assg_group_item;
-- delimiter $$
-- create trigger assignment_title_update_assg_group_item after update
--     on cos_assignment for each row
-- begin
--     update cos_assignment_group_item set title = NEW.title
--         where origin_type = 1 and origin_id = NEW.id;
-- end $$
--
-- drop trigger if exists discussion_title_update_assg_group_item;
-- delimiter $$
-- create trigger discussion_title_update_assg_group_item after update
--     on cos_discussion for each row
-- begin
--     update cos_assignment_group_item set title = NEW.title
--         where origin_type = 2 and origin_id = NEW.id;
-- end $$
--
-- drop trigger if exists quiz_title_update_assg_group_item;
-- delimiter $$
-- create trigger quiz_title_update_assg_group_item after update
--     on cos_quiz for each row
-- begin
--     update cos_assignment_group_item set title = NEW.title
--         where origin_type = 3 and origin_id = NEW.id;
-- end $$
-- end



