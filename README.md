# msnnts
This is my solution for the coding task from N26.

Let's review the requirements at first, the API should contain two endpoints:
 - `POST /transactions` - register transaction (for statistics purposes) if it was registered within last 60 seconds</li>
 - `GET /statistics` - get the statistics for the last 60 seconds</li>

The service should monitor and collect the following statistics for the last 60 seconds:
 - Total amount of transactions
 - Total number of transactions
 - Average amount of transactions
 - Minimum amount of transactions
 - Maximum amount of transactions

Additional requirement: endpoints should run in constant time and memory (O(1)).

The provided solution endpoints work in constant O(1) time. The whole service requires constant memory (required memory amount is fixed and does not increase as the transactions increase). 

I used the circular buffer data structure for this task (own implementation) for the following advantages:<br/>
 - Getting element by index requires constant time - data is stored in simple java array of fixed length.
 - Appending an element in the front requires constant time, no objects moved, no iterations. Just the zero index marker is moved to the right one step.
 - Removing an element from the end requires constant time, again also no iterations. Instead of actual deletion of object, the deleted object is just replaced with the newly added object.
 - Requires constant memory. For this coding task we need to account for last 60 seconds only, so we need fixed length array of about 60 elements.
 - The implementation is thread safe.
 
So what we store in this data structure of fixed length?
We store statistics objects for every second in this data structure. So we have 60 Statistics objects for the last 60 seconds.

Every statistics object of every seconds contains the following:
 - local minimum for this second
 - local maximum for this second
 - total amount of transactions for this second
 - total number of transactions for this second 
 
Please, check the sources out for more details.

Because of the limited time, I added only two unit test methods.
Ideally there would be more test cases for all of the endpoints and edge cases of the problem.