**Redis-Messaging Sandbox**

This is a sandbox for testing redis messaging. 
It is based on gs-messaging-redis: https://spring.io/guides/gs/messaging-redis/

Here:

- You have a seperated sender and two receivers
- Receiver One waits for an additional System.in, so you can test some things while the application is waiting.
- Receiver Two (and also One) only exit execution if at least one message was received by the sender application.

**Redis-Caching Sandbox**

The redis-templates sandbox shows an example of how to cache a JSON serialized object into redis and load it back.


** Maven **

This project is using Maven. If you haven't maven installed, you can use the included maven wrapper.