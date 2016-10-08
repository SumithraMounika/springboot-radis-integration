# springboot-radis-integration
Spring boot integration with redis data store with kyro serialization

1. It is developed by using the Java8 features for REST endpoint 

1. The application uses springboot java 8 implementation of a REST endpoint called '/availability/{id}?size=100kb'
2. Retrieve objects  from Redis, de-serializes into java objects , and creates a json response out of that ,before returning it as a response.
3. The implementation used KRYO serialization library for serialization and deserialization
4. Jackson for converting objects to json and return the response.
5. Redission redis client library.
6. Code organized cleanly into RedissonClient <- RedisAdapter <-  RedisFactory <- AvailabilityRestController.getAvailability();

