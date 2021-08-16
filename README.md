# SolrHashCracker

Hi, welcome to my super sketchy but kinda good enough Solr hash cracker.

I'll have to come back and make this more efficient, but for now it works.

Code heavily borrowed from:
https://github.com/ansgarwiechers/solrpasswordhash/blob/master/src/SolrPasswordHash.java

### Usage
Simply run:
```
javac SolrHashCracker.java
java SolrHashCracker <hash list> <word list>
```

### Hash list format
The hash list must be in the following format (same as in config file):
```<base64 salted hash 1> <base64 salt 1>
<base64 salted hash 2> <base64 salt 2>
<base64 salted hash 3> <base64 salt 3>
...
```

### ToDo
- Make more efficient
- Add usernames to the hash list format (for better output)
- Think of more ToDo items
