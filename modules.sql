use Library;
-- add_Books:
DROP procedure IF EXISTS `add_Book`;

DELIMITER $$
CREATE DEFINER=`msandbox`@`localhost` PROCEDURE `add_Book`(in Book_ID bigint(20), in Author_Name varchar(50), in Title_ varchar(100), in Name_ varchar(50) )
BEGIN
INSERT INTO Books(BookID, AuthorName, Title, Name_)
VALUES ( Book_ID , Author_Name ,Title_ , Name_);
END
$$

DELIMITER ;

-- add_BookCopies:

DROP procedure IF EXISTS `add_BookCopies`;

DELIMITER $$

CREATE PROCEDURE `add_BookCopies`(in BookCopyID_ bigint(20), in NoOfCopies_ smallint(6), in BookID_ bigint(20), in BranchID bigint(20))
BEGIN
INSERT INTO Book_Copies(BookCopyId, NoOfCopies, BookId, BranchId)
VALUES (  BookCopyID_ ,  NoOfCopies_ ,  BookID_ , BranchID  );
END$$

DELIMITER ;


-- Add_bookloans:

DROP procedure IF EXISTS `add_BookLoans`;

DELIMITER $$

CREATE DEFINER=current_user PROCEDURE `add_BookLoans`(in BookLoanID_ bigint(20),in date_out date, in dueDate_ date, in BranchID_ bigint(20), in BookID_ bigint(20), in CardNo_ bigint(20))
BEGIN

insert into Book_Loans (BookLoanId, DateOut, DueDate, BranchId, BookId, CardNo)
values(BookLoanID_, now(), dueDate_, BranchID_, BookID_, CardNo_);

END$$

DELIMITER ;


-- return book

DROP procedure IF EXISTS `return_Book`;

DELIMITER $$
USE `Library`$$
CREATE  PROCEDURE `return_Book`(BranchID_ bigint(20),  BookID_ bigint(20),  CardNo_ bigint(20))
BEGIN
 delete from Book_Loans
    where ((Book_Loans.BranchId = BranchID_) and (Book_Loans.BookId = BookID_) and (Book_Loans.CardNo = CardNo_));
END$$

DELIMITER ;


-- book at library branch

CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `msandbox`@`localhost` 
    SQL SECURITY DEFINER
VIEW `Library`.`displaybooks_atlibbranches` AS
    SELECT 
        `lb`.`BranchName` AS `BranchName`,
        `b`.`Title` AS  'Title',
        `b`.`BookId` AS `BookID`
    FROM
        ((`Library`.`Books` `b`
        JOIN `Library`.`Book_Copies` `bc` ON ((`b`.`BookId` = `bc`.`BookId`)))
        JOIN `Library`.`Library_Branches` `lb` ON ((`lb`.`BranchId` = `bc`.`BranchId`)));


-- Borrower for each book

CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = current_user 
    SQL SECURITY DEFINER
VIEW `Library`.`Borrower_BookCheckOut` AS
    SELECT 
        `b`.`BookId` AS `BookID`,
        `b`.`Title` AS `BookTitle`,
        `br`.`Name_` AS `Borrower`
    FROM
        ((`Library`.`Books` `b`
        JOIN `Library`.`Book_Loans` `bl` ON ((`b`.`BookId` = `bl`.`BookId`)))
        JOIN `Library`.`Borrowers` `br` ON ((`br`.`CardNo` = `bl`.`CardNo`)));


-- num_copies:

DELIMITER $$
CREATE DEFINER=current_user FUNCTION `return_copiesNumber`(bookTitle varchar(100),branchName varchar(100)) RETURNS smallint(6)
    READS SQL DATA
BEGIN
DECLARE x smallint;

select sum(NoOfCopies)
from Books b
join Book_Copies bc on (b.BookID = bc.BookID)
join Library_Branches lb on (lb.BranchId = bc.BranchID)
where (lb.BranchName=branchName and b.Title=bookTitle)
into x;
 RETURN x;
END$$

DELIMITER ;

-- Trigger on book table 

Delimiter //
CREATE DEFINER = CURRENT_USER TRIGGER Books_AFTER_INSERT AFTER INSERT ON Books FOR EACH ROW
BEGIN
	insert into Audit(username, action_,changedate) values(USER() ,'Adding Books',NOW());
END//
delimiter ;
 Delimiter //
CREATE DEFINER = CURRENT_USER TRIGGER Books_AFTER_UPDATE AFTER update ON Books FOR EACH ROW
BEGIN
	insert into Audit(username, action_,changedate) values(USER() ,'Update Books',NOW());
END//
delimiter ;

