# Spring-JPA

## Hibernate V/s JDBC:

1. One of the new problem with JDBC is JDBC Exceptions are checked exceptions.
2. JDBC suffering from more problems to persist the data. If we want to perform any operation we have to know about the whole details about the particular table. About table name, datatypes, column name, whole schema about the table etc.We’ll get data in relational format only.
3. Most of the time we want data in the object format but JDBC will give data in Relational format, as programmer we have to write for conversation logic object or relational format and relational format to Object format.
4. JDBC hard to perform transaction Management
5. Local Transaction is performed by JDBC but global transaction is performed by JTA. So we cannot handle both with JDBC.
6. JPA we can use JPQL Queries which is independent from DB queries
7. Boiler plate logic for Keygenerator and timesatmp in jDBC.
8. Hibernate Mapped the object directly with Object , but in jDBC we have to write the resultset.


## Hibernate CLass:
[Class Daigram](./src/main/resources/image/Class.png)
### Session: 
Object which contain mapping and configuration information and used to have the connection from Database. We cannot create the session object by own because for every transaction we need session object mean every time we will create a new connection. So to get session object we can use sessionFactory

### SessionFactory:
To give the session object we pass the datasource and configuration information and get the session object.
[Configuration.java](./src/main/java/com/example/springjpa/config/Configuration.java)

###  Dialect
It is used to convert HQL to underlying BD queries and vice versa. It also contain all the syntax and structure of DB queries. And also it contain the datatype. So in dialect we have one datatype which later on convert to underline database datatype.

### @PersistenceContext
Inside the persistence context there is a uniquely entity instance. So for any model mapped with @entity it will create the instance in it and it will managed the lifecycle.  It act as a first level cache where all the entity are fetched from db or persist to DB.</br>
	The EntityManager API is used to create and remove persistent entity instances, to find entities by their primary key, and to query over entities.</br>
	Whenever in  persistence context entity are changed it will marked the entity as dirty and when transaction complete this changes are flush to persistent storage. So persistence storage will kepp track of each entities.</br>
	There are 4 state the entity will go through to perfom the persistency
1. Transient state: When the entity is created. Any operation done on this state will not go to DB.
2. Persistent state: Where entity of attribute is attached to database or session to perform some operation. JPA operation : persist(), merge()
3. Detached state: Once object has been removed the relationship with session or database. JPA Method: detach(), clear(),refresh()
4. Removed state: When we need to perform update, delete from db.: JPA method : merge(),remove()
[Diagram for Lifecycle](./src/main/resources/image/entity-lifecycle.png)

</br>We have 2 type of persistence Context:
1. Transaction-scoped : By default in JPA. There will be only one persistence context object.
2. Extended-Scoped : There will be multiple persistence object each type when is autowired in multiple bean.And one persistence context is unawre of another persistence object.</br>
[Extended-Scoped Class](./src/main/java/com/example/springjpa/dao/IssueCardDao.java)
[Transaction-scoped class](./src/main/java/com/example/springjpa/dao/PersonRep.java)

## @DirtiesContext
When we dont want the transaction to be saved on to DB  after the operation is complete.
[PersonRepTest.java](./src/test/java/com/example/springjpa/dao/PersonRepTest.java)
	
## Lazy Fetch and Eager Fetch
When we mapped the object there may be another object which need to mapped. For example for Book class we also have Person Object which need to get Value. So in lazy fetch when we call the IssueCard class it will create a proxy object of person and instead of going to db to get value it will populate default value. But when we access the person object i.e. issueCaed.getPerson() then it will again go to db and fetch the value . So this is called lazy Fetch .
But in Eager fetch it will prepolute the value.

[Example of lazy fetch ](./src/main/java/com/example/springjpa/model/IssueCard.java)

### EntityManager 
flush(): To write the data to object at this point
detach(Object): No longer to track the change for the object
clear(): No longer to track the change in entity manager for all object.
refresh(object): Whatever the change done to object is not required rollback it.
[Example Entity Manager Java Class](./src/main/java/com/example/springjpa/dao/PersonRep.java)



# RelationShip

### Relationship using inheritance
1. **Generalization**
>It is process of keeping duplicate method in one class and extend the class to another class for using the method.

Example: m1() functionality is the duplicate method require in Class B & C for their method
Class A{
	public void m1(){}
}

Class B extend A{
	public void m2(){
	super.m1();
	}
}

Class C extend A{
	public void m2(){
	super.m1();
	}
}

2. **Realization**
>Here we will be using same method but the implemantation of method is different according to class.

Example:
Interface A{
	void m1();
}	

Class B implements A{
	@Override
	public void m1(){//Implementation 1}
}

Class C implements A{
	@Override
	public void m1(){//Implementation 2}
}

3. **Specialization**

> A class which have its own functionlity remain to that class only and will not be sharable to other class.

Example:
Class A{
	private  static final m1(){}
}

Class B extends A{
	//m1 cant be share to this class.
}

### Relationship using Composition
1. **Association**
> when we want to communicate with other class there must be a link and that can be represented by assocaition (connector). There can be one-to-one assocaition, one-to-n asociation, n-to-one association

Example:  Class B want to talk to Class A
Class A(){}
CLass B(){
	A a = new A()// now class B can talk to A
}

2.  **Aggregration**
> one class "owns" object of another class. But in aggregration it implies   relationship where the child can exist independently of the parent


3. **Composition**:
> It same like aggregration only difference is that child class cannot exist if parent class is not there.

>[Daigram]('./src/main/resources/image/association.png')

### Relationship Mapping model
There are several problems while persistency the data into the database because one entity class may contains relational data it is every hard to predict the relationship with underlying database.
ORM Technology has founded five most common problems while dealing with the relationship with object.
• Granularity
• Inheritance
• Navigation
• Identity
• Association

For that ORM has come with
**Inheritance Mapping Model** 
To solve the problem with inheritance probelm there are 3 strategy way:
1. SingleTable 
2. Table per class
3. Joined

[Inheritance Explanation Diagram](./src/main/resources/image/Inheritance.png)
Example : [Membership class](./src/main/java/com/example/springjpa/model/Membership.java) , [BasicMembershipClass](./src/main/java/com/example/springjpa/model/BasicMemberShip.java) , [PremiumMembership Class](./src/main/java/com/example/springjpa/model/PreminumMembership.java)


## Association
1. One-to-one 
	When we want to map the 2 table as one-to-one relationship we use this mapping. Example: IssueCard need to map person and there can be only one issuecard to one person. This is one-to-one unidirectional. As in person we don't need to map issue card. But if we need person to have issuecard into that then as we done with issuecard we have to do same in person we have to create one more foreign key in person as issue card so we can map issue card too. This is Bidirectional. In This we need to make the change in table. But if we don't need to change the table then JPA also provide mapped attribute in @oneToOne.
	[Person.class](./src/main/java/com/example/springjpa/model/Person.java)
	[IssueCard.class](./src/main/java/com/example/springjpa/model/IssueCard.java)
2. One-to-many:
When we want to map two table and the table can map the multiple entity of another table. Always Consider as Class and attribute, if class can have multiple attribute then it is one-to-many.
[IssueCard.java](./src/main/java/com/example/springjpa/model/IssueCard.java)
>One issue card may have multiple rating, so it is one-to-many maping
3. many-to-one
When we want to map two table and the entity can map the multiple table. Always Consider as Class and attribute, if we have multiple class that map to single attribute then it is many-to-one.
[Rating.java](./src/main/java/com/example/springjpa/model/Rating.java)
>There can be multiple rating for one issue card.
4. Many-to-many
If multiple class can be mapped to another multiple class.



# Transaction Management

ACID Properties

### A: Atomicity:
In the operation all the transaction made be operation should be completely rollback or should be completely successful. There should not be any partial transaction.

### C: Concurrent:
If there are n transaction happening in parallel and it should give same result to each transacrtion.

### I: Isolation:
How the transaction should affect the other transaction. Ex if transaction A happen and transaction B also happen so transaction B should see the affected value of transaction A or not. There are 4 level of isolation.

### D: Durability:
If transaction happen successfull and the server restart then also transaction value should be there. 

## Isolation Level:

### Transaction Isolation level is determined by following schema:
### Dirty Read:
A Dirty read is the situation when a transaction reads a data that has not yet been committed.
Example: Tansaction 1 update the value . And it didnt commit while transaction 2 read the updated value . At later of time transaction1 rollback , so transaction2 has the value which is never existed.

### Non Repetable Read:
Non Repeatable read occurs when a transaction reads same row twice, and get a different value each time
For Example: Transation 1 Read the row with attribute
Transaction 2 update the data
Transation 1 read the same rowdata.
So transation 1 read the same row with different attribute value..

### Phantom Read:
Phantom Read occurs when two same queries are executed, but the rows retrieved by the two, are different.
For example:
Transaction 1 read the data and get 1 row. with age=10
Transaction 2 insert one more row with age=10
Transaction 1 again read the data with 2 raow with age=10

### Isolation Level:
|Level|Dirty Read	| Non Repeatable Read |PhantomRead 
|--|--|--|--
| Read Uncommited | Allow |Allow|Allow
| Read Commited| Not Allowed|Allow|Allow
| Repeatale Read|Not Allowed |Not Allowed|Allow
| Serializable | Not Allowed|Not Allowed|Not Allowed


### Read Uncommited:
Read Uncommitted is the lowest isolation level. In this level, one transaction may read the uncommited change made by another transaction. So transaction are not isolated by each other. Here Dirty Read, non Repetable Read, Phantom Read are allowed.

### Read Commited
We allow transaction to read only the commited data. Uncommited data is not read. Using this we can only solve Dirty Read.

### Repeatable Read:
The transaction wait until the write lock is unlocked by other transaction which is writing, updating, modifying the data.
So in this we are save from Repetable read. 
And the transaction read only commited data, so we are save with dirty read.

### Serializable:
The transaction wait until the write lock is unlocked by other transaction which is writing, updating, modifying the data.
So in this we are save from Repetable read. 
And the transaction read only commited data, so we are save with dirty read.
When on transaction is doing the read , it will put the read lock so that other transaction cannot insert the row, so this way we can save from phantom read.
This is the highest isolation level.


> When we go from highest isolation level the performance decreases and performance goes on increasing as we reach to lowest isolation level.

###  Propogation
Spring use the propogation to configure the transaction boundary across business layer,

1. **REQUIRED** — support a current transaction, create a new one if none exist
2. **REQUIRES_NEW** — create a new transaction and suspend the current transaction if none exist
3. **MANDATORY** — support a current transaction, throw an exception if none exists
4.  **NESTED** — executes within a nested transaction if a current transaction exists
5.  **SUPPORTS** — supports currents transaction but execute non-transactionally if none exists

### Implementation of transaction management:
@transactional from javax.transaction.Transactional is good to manage transation for single db.

But in an operation when we have 2 DB and we have to manage transaction it good to use @Transactional of org.springframework.transaction.annotation.Transactional.

Attribute in @Transactional spring :
-   **readOnly —**  whether the transaction is read-only or read/write
-   **timeout —**  transaction timeout
-   **rollbackFor —**  arrays of exception class objects that must cause a rollback of the transaction
-   **rollbackForClassName —** arrays of exception class names that must cause a rollback of the transaction
-   **noRollbackFor —** arrays of exception class objects that must not cause a rollback of the transaction
-   **noRollbackForClassName —**  arrays of exception class names that must not cause a rollback of the transaction
**- Propogation**
**Isolation**

[Example](./src/main/java/com/example/springjpa/config/Configuration.java)

### Pessimistic Locking:
When transaction at the first time tries to access the data it will acquire a lock to that so that other transaction will be in waiting state for reading the data.
The main Disadvantage is that a resource is locked from the time it is first accessed in a transaction until the transaction is finished, making it inaccessible to other transactions during that time.So if there are n transaction happening in parallel all transaction has to wait.

### Optimistic Locking
In this scenario when transaction tries to commit the data in database it will check the state of the resource is read from storage again and compared to the state that was saved when the resource was first accessed in the transaction. If the state is changed then it will not commit the changes and transaction will be rollback. The main advantage is the transaction will not keep a lock at first time it accessed so that other transaction can be run concurrent.

### JPA provide type of lock:
1. OPTIMISTIC – it obtains an optimistic read lock for all entities containing a version attribute
2. OPTIMISTIC_FORCE_INCREMENT – it obtains an optimistic lock the same as OPTIMISTIC and additionally increments the version attribute value
3. READ – it's a synonym for OPTIMISTIC
4. WRITE – it's a synonym for OPTIMISTIC_FORCE_INCREMENT
5. PESSIMISTIC_READ - allows us to obtain a shared lock and prevent the data from being updated or deleted
6. PESSIMISTIC_FORCE_INCREMENT - works like PESSIMISTIC_WRITE and it additionally increments a version attribute of a versioned entity
7. PESSIMISTIC_WRITE - allows us to obtain an exclusive lock and prevent the data from being read, updated or deleted

Example : [Optimistic Locking on entity manager and find method()](./src/main/java/com/example/springjpa/dao/PersonRep.java) , [Locking on Native Query, Named Query](./src/main/java/com/example/springjpa/model/Person.java)


### Atomic Locking:
In the table for row we will have a attribute lock. So when a transaction1 read a row it will increment the lock value 1, which mean no other transaction can do write/ update , when another transaction2  acquire a lock it will again increment the lock value 2. And when transaction1 is finished it will decremnt the lock value to 1. For other transaction to update the value they have to wait till lock value become to 0


## Caching
[Cache Diagram](./src/main/resources/image/Caching.png)
### First Level Cache (L1):
Persistence context will act as a first level cache and the cache will  work only in tansaction boundary.</br>
For each transaction level there will be different cache. Example in class [bookdao.class](./src/main/java/com/example/springjpa/dao/BookDao.java) , [bookdaoTest.class](./src/main/test/java/com/example/springjpa/dao/BookDaoCachingTest.java)
So we put find book method as @Transaction and when we call find book method 2 time from the method then it will hit the db 2 time but when we add transaction to method who is calling 2 time then both operation will come under one transaction. So therefore at first time it will go to db and second time it will not go to DB.

### Second Level Cache(L2):
If we want to have cache across multiple transaction layer then we can use second level cache.
For L2 cache we have to provide Cache to JPA.
So we have to make configuration to Hibernate:
1. Enable second level cache
2. Provide cache provider(Here we use EHCache)
    ```
    Property:
   //enable second  level cahce
   hibernateProperties.put("hibernate.cache.use_second_level_cache","true");
   //Which Cache provider to use
   hibernateProperties.put("hibernate.cache.region.factory_class","org.hibernate.cache.ehcache.EhCacheRegionFactory");
    ```
   [Configuration.class](./src/main/java/com/example/springjpa/config/Configuration.java)
3. Say to cache only data we want, so whatever entity we want to cache annotate with @Cache(startegy)
</br> [Book.java](./src/main/java/com/example/springjpa/model/Book.java)
>Strategy type:
1. READ_ONLY: Used only for entities that never change (exception is thrown if an attempt to update such an entity is made). It is very simple and performant. Very suitable for some static reference data that don't change
2. NONSTRICT_READ_WRITE: Cache is updated after a transaction that changed the affected data has been committed. Thus, strong consistency is not guaranteed and there is a small time window in which stale data may be obtained from cache. This kind of strategy is suitable for use cases that can tolerate eventual consistency
3. READ_WRITE: This strategy guarantees strong consistency which it achieves by using ‘soft' locks: When a cached entity is updated, a soft lock is stored in the cache for that entity as well, which is released after the transaction is committed. All concurrent transactions that access soft-locked entries will fetch the corresponding data directly from database
4. TRANSACTIONAL: Cache changes are done in distributed XA transactions. A change in a cached entity is either committed or rolled back in both database and cache in the same XA transaction

