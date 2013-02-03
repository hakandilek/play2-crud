play2-cache
===========

cache utilities for the [play framework](http://github.com/playframework/play).

Sample
------
   
   [Here](https://github.com/hakandilek/play2-cache/tree/master/samples/play2-cache-sample) is a full featured sample.

HOW-TO
------

Follow these steps to use play2-cache in your projects

# CachedFinder

## Define a CachedFinder 

Define a CachedFinder like any Finder in your project:

```java
public static CachedFinder<Long, Task> find = new CachedFinder<Long, Task>(Long.class, Task.class);
```

## Use with CRUD operations

TODO

## Use page() methods

TODO

# InterimCache

InterimCache stores key/value pairs for a limited time. Items will be cleared and reloaded upon recall when timeout is reached.

## Create an InterimCache with timeout

TODO

## store/retrieve values

TODO
## store/retrieve a whole Page object

TODO
