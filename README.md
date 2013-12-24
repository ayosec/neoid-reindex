Download a precompiled version

    $ wget https://github.com/ayosec/neoid-reindex/releases/download/0.1/neoid-reindexer-assembly-0.1-SNAPSHOT.jar

Delete/rename your old `index` directory, under `data/graph.db`

Type

    $ java -jar neoid-reindexer-assembly-0.1-SNAPSHOT.jar /path/to/graph.db/

A dot is printed every 1000 records (nodes and relationships)
